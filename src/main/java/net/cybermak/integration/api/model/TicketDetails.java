package net.cybermak.integration.api.model;

public class TicketDetails {
    private String corporateID;
    private String stagingGUID;
    private String consumerUnderCorporateFlag;
    private String company;
    private int timeOut;

    public TicketDetails() {
    }


    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
    public String getCorporateID() {
        return corporateID;
    }

    public void setCorporateID(String corporateID) {
        this.corporateID = corporateID;
    }

    public String getStagingGUID() {
        return stagingGUID;
    }

    public void setStagingGUID(String stagingGUID) {
        this.stagingGUID = stagingGUID;
    }

    public String getConsumerUnderCorporateFlag() {
        return consumerUnderCorporateFlag;
    }

    public void setConsumerUnderCorporateFlag(String consumerUnderCorporateFlag) {
        this.consumerUnderCorporateFlag = consumerUnderCorporateFlag;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "TicketDetails{" +
                "corporateID='" + corporateID + '\'' +
                ", stagingGUID='" + stagingGUID + '\'' +
                ", consumerUnderCorporateFlag='" + consumerUnderCorporateFlag + '\'' +
                ", company='" + company + '\'' +
                ", timeOut=" + timeOut +
                '}';
    }
}
