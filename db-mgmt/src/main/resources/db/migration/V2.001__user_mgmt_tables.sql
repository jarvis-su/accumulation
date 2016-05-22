CREATE TABLE PRIVILEGES (
  PRIVILEGE_ID INT(11) NOT NULL,
  OPERATION VARCHAR(45) DEFAULT NULL COMMENT '				',
  COMPONENT VARCHAR(45) DEFAULT NULL,
  DESCRIPTION VARCHAR(200) DEFAULT NULL COMMENT '			',
  EFFECTIVE_DATE DATETIME DEFAULT NULL COMMENT '		',
  EXPIRED_DATE DATETIME DEFAULT NULL,
  STATUS_ID INT(11) NOT NULL,
  INSERT_DATE DATETIME DEFAULT NULL,
  MOD_DATE VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (PRIVILEGE_ID),
  KEY FK_PRI_STATUS_ID_IDX (STATUS_ID),
  CONSTRAINT FK_PRI_STATUS_ID FOREIGN KEY (STATUS_ID) REFERENCES STATUS (STATUS_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=INNODB DEFAULT CHARSET=UTF8;


CREATE TABLE USER_TYPES (
  USER_TYPE_ID INT(11) NOT NULL,
  TYPE_NAME VARCHAR(45) DEFAULT NULL,
  TYPE_DESCR VARCHAR(200) DEFAULT NULL,
  INSERT_DATE DATETIME DEFAULT NULL,
  PRIMARY KEY (USER_TYPE_ID)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE USER_TYPE_PRIVILEGES (
  USER_TYPE_ID INT(11) NOT NULL,
  PRILIVEGE_ID INT(11) NOT NULL,
  EFFECTIVE_DATE DATETIME DEFAULT NULL,
  END_DATE DATETIME DEFAULT NULL,
  INSERT_DATE DATETIME DEFAULT NULL,
  MOD_DATE DATETIME DEFAULT NULL,
  PRIMARY KEY (USER_TYPE_ID,PRILIVEGE_ID),
  KEY FK_USER_TYPE_PRIV_IDX (PRILIVEGE_ID),
  CONSTRAINT FK_USER_TYPE_PRIV FOREIGN KEY (PRILIVEGE_ID) REFERENCES PRIVILEGES (PRIVILEGE_ID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_USER_TYPE_TBL FOREIGN KEY (USER_TYPE_ID) REFERENCES USER_TYPES (USER_TYPE_ID) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=UTF8;


CREATE TABLE ROLE (
  ROLE_ID INT(11) NOT NULL,
  ROLE_NAME VARCHAR(45) NOT NULL,
  ROLE_DESCR VARCHAR(200) DEFAULT NULL,
  USER_TYPE_ID INT(11) DEFAULT NULL,
  STATUS_ID INT(11) DEFAULT NULL,
  INSERT_DATE DATETIME DEFAULT NULL COMMENT '		',
  MOD_DATE DATETIME DEFAULT NULL,
  PRIMARY KEY (ROLE_ID),
  KEY FK_ROLE_STATUS_ID_IDX (STATUS_ID),
  KEY FK_USER_TYPE_ROLE_IDX (USER_TYPE_ID),
  CONSTRAINT FK_ROLE_STATUS_ID FOREIGN KEY (STATUS_ID) REFERENCES STATUS (STATUS_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_USER_TYPE_ROLE FOREIGN KEY (USER_TYPE_ID) REFERENCES USER_TYPES (USER_TYPE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE ROLE_PRIVILEGES (
  ROLE_ID INT(11) NOT NULL,
  PRIVILEGE_ID INT(11) NOT NULL,
  EFFECTIVE_DATE DATETIME DEFAULT NULL,
  END_DATE DATETIME DEFAULT NULL,
  INSERT_DATE DATETIME DEFAULT NULL,
  MOD_DATE DATETIME DEFAULT NULL,
  PRIMARY KEY (ROLE_ID,PRIVILEGE_ID),
  KEY FK_ROLE_PRIV_ID_IDX (PRIVILEGE_ID),
  CONSTRAINT FK_ROLE_ID_PRIV FOREIGN KEY (ROLE_ID) REFERENCES ROLE (ROLE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_ROLE_PRIV_ID FOREIGN KEY (PRIVILEGE_ID) REFERENCES PRIVILEGES (PRIVILEGE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=INNODB DEFAULT CHARSET=UTF8;


CREATE TABLE USERS (
  USER_ID INT(11) NOT NULL,
  LOGIN_NAME VARCHAR(45) DEFAULT NULL,
  FIRST_NAME VARCHAR(45) DEFAULT NULL,
  LAST_NAME VARCHAR(45) DEFAULT NULL,
  EFFECTIVE_DATE DATETIME DEFAULT NULL,
  PASSWORD VARCHAR(200) DEFAULT NULL,
  PASSWORD1 VARCHAR(200) DEFAULT NULL,
  PASSWORD2 VARCHAR(200) DEFAULT NULL,
  BAD_PWD_CNT VARCHAR(45) DEFAULT NULL,
  USER_STATUS_ID INT(11) DEFAULT NULL,
  USER_TYPE_ID INT(11) DEFAULT NULL,
  INSERT_DATE DATETIME DEFAULT NULL,
  PRIMARY KEY (USER_ID),
  KEY FK_USER_STATUS_ID_IDX (USER_STATUS_ID),
  KEY FK_USER_TYPE_ID_IDX (USER_TYPE_ID),
  CONSTRAINT FK_USER_STATUS_ID FOREIGN KEY (USER_STATUS_ID) REFERENCES STATUS (STATUS_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_USER_TYPE_ID FOREIGN KEY (USER_TYPE_ID) REFERENCES USER_TYPES (USER_TYPE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=INNODB DEFAULT CHARSET=UTF8;


CREATE TABLE USER_TRX_HIS (
  TRX_ID INT(11) NOT NULL,
  SEQ_ID INT(11) NOT NULL,
  TRX_TYPE_ID INT(11) DEFAULT NULL,
  RESPONSE_TYPE_ID INT(11) DEFAULT NULL,
  TRX_DATE DATETIME DEFAULT NULL,
  USER_ID INT(11) DEFAULT NULL,
  ENTRY_DATE DATETIME DEFAULT NULL,
  PRIMARY KEY (TRX_ID,SEQ_ID),
  KEY FK_TRX_HIS_USER_ID_IDX (USER_ID),
  KEY FK_TRX_HIS_TYPE_ID_IDX (TRX_TYPE_ID),
  KEY FK_TRX_HIS_RES_ID_IDX (RESPONSE_TYPE_ID),
  CONSTRAINT FK_TRX_HIS_TYPE_ID FOREIGN KEY (TRX_TYPE_ID) REFERENCES TRANSACTION_TYPE (TRX_TYPE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_TRX_HIS_USER_ID FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_TRX_HIS_RES_ID FOREIGN KEY (RESPONSE_TYPE_ID) REFERENCES RESPONSE_TYPE (RESPONSE_TYPE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=INNODB DEFAULT CHARSET=UTF8;


CREATE TABLE TRANSACTION_USERS (
  TRX_ID INT(11) NOT NULL,
  SEQ_ID VARCHAR(45) NOT NULL,
  USER_ID INT(11) DEFAULT NULL,
  PRIMARY KEY (TRX_ID,SEQ_ID)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;
