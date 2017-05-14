package com.example.rishucuber.armywelfare.model;

import java.io.Serializable;

/**
 * Created by rishucuber on 7/4/17.
 */

public class MartyrInformation implements Serializable {
    private String mName, mVillage, mDate, mDonation, mInfo;


    public MartyrInformation(String mName, String mVillage, String mDate, String mDonation, String mInfo) {
        this.mName = mName;
        this.mVillage = mVillage;
        this.mDate = mDate;
        this.mDonation = mDonation;
        this.mInfo = mInfo;


    }

    public String getmName() {
        return mName;
    }

    public String getmVillage() {
        return mVillage;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmDonation() {
        return mDonation;
    }

    public String getmInfo() {return mInfo; }
}
