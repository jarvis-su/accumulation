create or replace procedure sp_GetNextSeqVal (
      SeqName IN VARCHAR2,
      Incr IN INTEGER,
      RetSeqVal OUT BINARY_DOUBLE,
      ExpireSpec OUT VARCHAR2,
      FetchTime OUT TIMESTAMP,
      MinUpdtMs OUT NUMBER,
      DfltIncr OUT NUMBER
      )
AS
PRAGMA AUTONOMOUS_TRANSACTION;
  NextSeqVal number(38,0);  -- allow to be bigger than MaxVal
  MaxVal BINARY_DOUBLE;  -- allow up to java long
  MinVal number;
  CanWrap char(1);

begin
  -- get next seq value
  update IDTABLE2
      set NEXTID = NEXTID + Incr, modtime = SYSTIMESTAMP
      where trim(SEQ_NAME) = SeqName
      returning NEXTID, MAX_VAL, EXPIRE_SPEC, SYSTIMESTAMP, MIN_UPDT_MS, CAN_WRAP, DFLT_INCR
        into NextSeqVal, MaxVal, ExpireSpec, FetchTime, MinUpdtMs, CanWrap, DfltIncr;
  dbms_output.put_line ('NextSeqVal=' || NextSeqVal ||
            ' MaxVal=' || MaxVal);
  -- check for bad seq name
  if SQL%ROWCOUNT = 0 then
      dbms_output.put_line (SeqName || ' is a bad sequence name');
      return;
  end if;
  -- check for seq overflow
  if NextSeqVal-1 > MaxVal then
      if CanWrap = 'N' then
        raise_application_error(-20101, 'Max sequence value has been reached for sequence ' || SeqName);
      end if;
      -- get lock to reset seq
      select min_val into MinVal
         from IDTABLE2
         where trim(SEQ_NAME) = SeqName and nextid = NextSeqVal
         for update of nextid;
      if SQL%ROWCOUNT = 0 then
        dbms_output.put_line (SeqName || ' already reset by someone else.');
      else
        -- reset the seqval
        dbms_output.put_line ('NextSeqVal ' || NextSeqVal ||
            ' is gt ' || MaxVal || ' so set to ' || MinVal);
        update IDTABLE2 set nextid = MinVal
          where trim(SEQ_NAME) = SeqName and nextid = NextSeqVal;
      end if;
      -- try again to get a seq block BUT DO NOT UPDATE MODTIME AGAIN so that min_updt_ms won't just go to 0.
      update IDTABLE2
          set NEXTID = NEXTID + Incr
          where trim(SEQ_NAME) = SeqName
      	  returning NEXTID, MAX_VAL, EXPIRE_SPEC, SYSTIMESTAMP, MIN_UPDT_MS, CAN_WRAP, DFLT_INCR
        	into NextSeqVal, MaxVal, ExpireSpec, FetchTime, MinUpdtMs, CanWrap, DfltIncr;
  end if;
  commit;
  RetSeqVal := NextSeqVal-Incr;
  dbms_output.put_line ('NextSeqVal=' || NextSeqVal || ', RetSeqVal=' || (RetSeqVal));
  
end;
