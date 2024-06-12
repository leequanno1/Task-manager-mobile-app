package com.example.taskmanagerment.customlistview;

public class NotificationItem extends ItemModel {

    private String contentNotification;

    private String timeNotification;

    public NotificationItem(String contentNotification, String timeNotification) {
        this.contentNotification = contentNotification;
        this.timeNotification = timeNotification;
    }

    public String getContentNotification() {
        return contentNotification;
    }

    public void setContentNotification(String contentNotification) {
        this.contentNotification = contentNotification;
    }

    public String getTimeNotification() {
        return timeNotification;
    }

    public void setTimeNotification(String timeNotification) {
        this.timeNotification = timeNotification;
    }

    @Override
    public int getType() {
        return TYPE_NOTIFICATION;
    }

    @Override
    public String getTitle() {
        return contentNotification;
    }

    @Override
    public String getDate() {
        return timeNotification;
    }

}
