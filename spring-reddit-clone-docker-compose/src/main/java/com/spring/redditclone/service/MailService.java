package com.spring.redditclone.service;

import com.spring.redditclone.model.NotificationEmail;

public interface MailService {
    void sendMail(NotificationEmail notificationEmail);
}
