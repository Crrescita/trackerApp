package com.crrescita.employeetracker.activity.profile_menu.attendence_report.adapter;

public class AttendenceModal {
    private String  date;
    private String  attendence;
    private String  checkIn;
    private String  checkout;
    private String  totalHours;

    public AttendenceModal(String date, String attendence, String checkIn, String checkout, String totalHours) {
        this.date = date;
        this.attendence = attendence;
        this.checkIn = checkIn;
        this.checkout = checkout;
        this.totalHours = totalHours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttendence() {
        return attendence;
    }

    public void setAttendence(String attendence) {
        this.attendence = attendence;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }
}
