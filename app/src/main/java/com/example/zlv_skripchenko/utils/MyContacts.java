package com.example.zlv_skripchenko.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Yevgen on 18.06.2017.
 */

public class MyContacts {
    @SerializedName("contacts")
    @Expose
    private List<MyContact> contacts = null;

    public List<MyContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<MyContact> contacts) {
        this.contacts = contacts;
    }

}
