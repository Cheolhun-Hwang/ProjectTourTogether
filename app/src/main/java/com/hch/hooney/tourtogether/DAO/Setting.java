package com.hch.hooney.tourtogether.DAO;

/**
 * Created by qewqs on 2018-01-23.
 */

public class Setting {
    private boolean isPush;

    public Setting() {
        this.isPush = true;
    }

    public Setting(boolean isPush) {
        this.isPush = isPush;
    }

    public boolean isPush() {
        return isPush;
    }

    public void setPush(boolean push) {
        isPush = push;
    }
}
