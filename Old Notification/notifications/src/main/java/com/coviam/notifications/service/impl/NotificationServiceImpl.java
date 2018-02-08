package com.coviam.notifications.service.impl;


import com.coviam.notifications.entity.Notification;
import com.coviam.notifications.repository.NotificationRepository;
import com.coviam.notifications.service.NotificationService;
import com.coviam.notifications.service.NotificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;


    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification insert(Notification notification) {
        return notificationRepository.insert(notification);
    }



    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }


    @Override
    public void markAllAsRead(String user_id) {
        ArrayList<Notification> notifications = NotificationUtils.findAllById(new ArrayList<>(notificationRepository.findAll()), user_id);
        for(Notification notification: notifications){
            notification.setRead(1);
            save(notification);
        }
    }

    @Override
    public List<Notification> findAllByUser(String user_id) {
        return NotificationUtils.findAllById(new ArrayList<>(notificationRepository.findAll()), user_id);
    }

    @Override
    public Notification findOneById(String notification_id) {
        return notificationRepository.findOne(notification_id);
    }

    @Override
    public void addNotification(String user_id, HashMap<String,String> details, int type) {
        Notification notification = NotificationUtils.createNotification(user_id);
        notification.setDetails(details);
        notification.setType(type);
        notificationRepository.insert(notification);
    }
}
