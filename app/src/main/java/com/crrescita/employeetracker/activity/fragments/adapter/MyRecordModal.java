package com.crrescita.employeetracker.activity.fragments.adapter;

public class MyRecordModal {
    private String  title;
    private String  icon;
    private String  details;


    public MyRecordModal(String title, String icon, String details) {
        this.title = title;
        this.icon = icon;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
