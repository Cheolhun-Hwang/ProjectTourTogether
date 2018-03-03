package com.hch.hooney.tourtogether.Recycler.Translate;

/**
 * Created by hch on 2018-03-01.
 */

public class TranslateItem {
    private String t_id;
    private int t_from;
    private int t_to;
    private String t_origin;
    private String t_translate;

    public TranslateItem() {
        this.t_id = "";
        this.t_from = -1;
        this.t_to = -1;
        this.t_origin = "";
        this.t_translate = "";
    }

    public TranslateItem(String t_id, int t_from, int t_to,
                         String t_origin, String t_translate) {
        this.t_id = t_id;
        this.t_from = t_from;
        this.t_to = t_to;
        this.t_origin = t_origin;
        this.t_translate = t_translate;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public int getT_from() {
        return t_from;
    }

    public void setT_from(int t_from) {
        this.t_from = t_from;
    }

    public int getT_to() {
        return t_to;
    }

    public void setT_to(int t_to) {
        this.t_to = t_to;
    }

    public String getT_origin() {
        return t_origin;
    }

    public void setT_origin(String t_origin) {
        this.t_origin = t_origin;
    }

    public String getT_translate() {
        return t_translate;
    }

    public void setT_translate(String t_translate) {
        this.t_translate = t_translate;
    }
}
