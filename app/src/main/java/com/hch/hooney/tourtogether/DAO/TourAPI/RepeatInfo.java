package com.hch.hooney.tourtogether.DAO.TourAPI;

/**
 * Created by hch on 2018-01-29.
 */

public class RepeatInfo {
    private String InfoName;
    private String InfoText;

    public RepeatInfo() {
        InfoName = "";
        InfoText = "";
    }

    public RepeatInfo(String infoName, String infoText) {
        InfoName = infoName;
        InfoText = infoText;
    }

    public String getInfoName() {
        return InfoName;
    }

    public void setInfoName(String infoName) {
        InfoName = infoName;
    }

    public String getInfoText() {
        return InfoText;
    }

    public void setInfoText(String infoText) {
        InfoText = infoText;
    }
}
