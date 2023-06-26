package net.cybermak.integration.api.model;

public class Response {
    private String bssOutPutFlag;

    public Response(String bssOutPutFlag) {
        this.bssOutPutFlag = bssOutPutFlag;
    }

    public String getBssOutPutFlag() {
        return bssOutPutFlag;
    }

    public void setBssOutPutFlag(String bssOutPutFlag) {
        this.bssOutPutFlag = bssOutPutFlag;
    }
}
