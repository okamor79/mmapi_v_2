package com.mm.beauty.api.service;

import com.mm.beauty.api.entity.EmailModel;


public interface EmailService {

    void sendSimpleMail(EmailModel details);
}
