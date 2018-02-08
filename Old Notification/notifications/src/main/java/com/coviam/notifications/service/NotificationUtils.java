package com.coviam.notifications.service;

import com.coviam.notifications.entity.Notification;

import java.util.ArrayList;

public class NotificationUtils {

    public static ArrayList<Notification> findAllById(ArrayList<Notification> all, String user_id) {
        ArrayList<Notification> selected = new ArrayList<>();
        for(Notification notification: all){
            if(notification.getUser_id().equals(user_id))
                selected.add(notification);
        }
        return selected;
    }

    public static Notification createNotification(String user_id){
        Notification notification = new Notification();
        notification.setRead(0);
        notification.setTimestamp(String.valueOf(System.currentTimeMillis()));
        notification.setUser_id(user_id);
        return notification;
    }
}
