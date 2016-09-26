package jarvis.inner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by C5023792 on 8/26/2016.
 */
public class Test1 {

    public static void main(String[] args) {
        int loopCount = Integer.MAX_VALUE / 50;
        System.out.println(loopCount);
        long begin = System.currentTimeMillis();
        List<PrevInOutDetail> list = new ArrayList<PrevInOutDetail>();
        for(int i = 0 ;i < loopCount;i++){
            list.add(new PrevInOutDetail());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }


   public static class PrevInOutDetail extends Detail {
        private Date reportMonth;
        private String parish;
        private String providerNumber;
        private String taNumber;
        private String personNumber;
        private String childId;
        private String authNumber;
        private String trxType;
        private Date trxDatetime;
        private Date entryDate;
        public Date getReportMonth() {
            return reportMonth;
        }
        public void setReportMonth(Date reportMonth) {
            this.reportMonth = reportMonth;
        }
        public String getParish() {
            return parish;
        }
        public void setParish(String parish) {
            this.parish = parish;
        }
        public String getProviderNumber() {
            return providerNumber;
        }
        public void setProviderNumber(String providerNumber) {
            this.providerNumber = providerNumber;
        }
        public String getTaNumber() {
            return taNumber;
        }
        public void setTaNumber(String taNumber) {
            this.taNumber = taNumber;
        }
        public String getPersonNumber() {
            return personNumber;
        }
        public void setPersonNumber(String personNumber) {
            this.personNumber = personNumber;
        }
        public String getChildId() {
            return childId;
        }
        public void setChildId(String childId) {
            this.childId = childId;
        }
        public String getAuthNumber() {
            return authNumber;
        }
        public void setAuthNumber(String authNumber) {
            this.authNumber = authNumber;
        }
        public String getTrxType() {
            return trxType;
        }
        public void setTrxType(String trxType) {
            this.trxType = trxType;
        }
        public Date getTrxDatetime() {
            return trxDatetime;
        }
        public void setTrxDatetime(Date trxDatetime) {
            this.trxDatetime = trxDatetime;
        }
        public Date getEntryDate() {
            return entryDate;
        }
        public void setEntryDate(Date entryDate) {
            this.entryDate = entryDate;
        }
    }
}


