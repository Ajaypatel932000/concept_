package com.example.concept_git.notification_list;

public class NotiListItems {

    String date,Notification;

    public NotiListItems(String date, String notification) {
        this.date = date;
        Notification = notification;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotification() {
        return Notification;
    }

    public void setNotification(String notification) {
        Notification = notification;
    }
}
