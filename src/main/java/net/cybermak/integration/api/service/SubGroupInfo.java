package net.cybermak.integration.api.service;

public class SubGroupInfo {

    private String accountCode;
    private String corporateGroupID;
    private String groupName;
    private String groupMemberLimit;

    public SubGroupInfo() {
    }

    public SubGroupInfo(String accountCode, String corporateGroupID, String groupName, String groupMemberLimit) {
        this.accountCode = accountCode;
        this.corporateGroupID = corporateGroupID;
        this.groupName = groupName;
        this.groupMemberLimit = groupMemberLimit;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getCorporateGroupID() {
        return corporateGroupID;
    }

    public void setCorporateGroupID(String corporateGroupID) {
        this.corporateGroupID = corporateGroupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupMemberLimit() {
        return groupMemberLimit;
    }

    public void setGroupMemberLimit(String groupMemberLimit) {
        this.groupMemberLimit = groupMemberLimit;
    }

    @Override
    public String toString() {
        return "SubGroupInfo{"
                + "accountCode='" + accountCode + '\''
                + ", corporateGroupID='" + corporateGroupID + '\''
                + ", groupName='" + groupName + '\''
                + ", groupMemberLimit='" + groupMemberLimit + '\''
                + '}';
    }
}

