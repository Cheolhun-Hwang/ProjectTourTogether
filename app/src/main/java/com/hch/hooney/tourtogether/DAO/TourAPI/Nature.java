package com.hch.hooney.tourtogether.DAO.TourAPI;

import com.hch.hooney.tourtogether.DAO.TourApiItem;

import java.util.ArrayList;

/**
 * Created by hch on 2018-01-29.
 */

public class Nature extends TourApiItem {
    private String intro_infocenter;
    private String intro_restdate;
    private String intro_usetime;

    private String basic_homepage;

    private ArrayList<RepeatInfo> repeateList;

    private ArrayList<String> smallImageList;

    public Nature() {
        super();
        this.intro_infocenter = "";
        this.intro_restdate = "";
        this.intro_usetime = "";
        this.repeateList = null;
        this.smallImageList = null;
        this.basic_homepage = "";
    }

    public Nature(TourApiItem item) {
        super(item);
        this.intro_infocenter = "";
        this.intro_restdate = "";
        this.intro_usetime = "";
        this.repeateList = null;
        this.smallImageList = null;

        this.basic_homepage = "";
    }

    public String getIntro_infocenter() {
        return intro_infocenter;
    }

    public void setIntro_infocenter(String intro_infocenter) {
        this.intro_infocenter = intro_infocenter;
    }

    public String getIntro_restdate() {
        return intro_restdate;
    }

    public void setIntro_restdate(String intro_restdate) {
        this.intro_restdate = intro_restdate;
    }

    public String getIntro_usetime() {
        return intro_usetime;
    }

    public void setIntro_usetime(String intro_usetime) {
        this.intro_usetime = intro_usetime;
    }

    public ArrayList<RepeatInfo> getRepeateList() {
        return repeateList;
    }

    public void setRepeateList(ArrayList<RepeatInfo> repeateList) {
        this.repeateList = repeateList;
    }

    public ArrayList<String> getSmallImageList() {
        return smallImageList;
    }

    public void setSmallImageList(ArrayList<String> smallImageList) {
        this.smallImageList = smallImageList;
    }

    public String getBasic_homepage() {
        return basic_homepage;
    }

    public void setBasic_homepage(String basic_homepage) {
        this.basic_homepage = basic_homepage;
    }
}
