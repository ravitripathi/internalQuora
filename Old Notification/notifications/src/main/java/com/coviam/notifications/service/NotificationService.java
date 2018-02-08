package com.coviam.notifications.service;

import com.coviam.notifications.entity.Notification;

import java.util.HashMap;
import java.util.List;

public interface NotificationService {
    public List<Notification> findAll();

    public Notification insert(Notification notification);

    public Notification save(Notification notification);

    public void markAllAsRead(String user_id);

    public List<Notification> findAllByUser(String user_id);

    public Notification findOneById(String notification_id);

    public void addNotification(String user_id, HashMap<String,String> details, int type);

}
