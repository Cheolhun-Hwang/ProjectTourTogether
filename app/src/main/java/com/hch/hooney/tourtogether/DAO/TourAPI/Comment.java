package com.hch.hooney.tourtogether.DAO.TourAPI;

/**
 * Created by hch on 2018-02-10.
 */

public class Comment {
    private String C_Key;
    private String Uid;
    private String UName;
    private String UProFileImage;
    private String WriteDate;
    private String Content;

    public Comment() {
        this.C_Key = "";
        this.Uid = "";
        this.UName = "";
        this.UProFileImage = "";
        this.WriteDate = "";
        this.Content = "";
    }

    public Comment(String c_Key, String uid, String UName, String UProFileImage, String writeDate, String content) {
        this.C_Key = c_Key;
        this.Uid = uid;
        this.UName = UName;
        this.UProFileImage = UProFileImage;
        this.WriteDate = writeDate;
        this.Content = content;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getC_Key() {
        return C_Key;
    }

    public void setC_Key(String c_Key) {
        C_Key = c_Key;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getUProFileImage() {
        return UProFileImage;
    }

    public void setUProFileImage(String UProFileImage) {
        this.UProFileImage = UProFileImage;
    }

    public String getWriteDate() {
        return WriteDate;
    }

    public void setWriteDate(String writeDate) {
        WriteDate = writeDate;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
