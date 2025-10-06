package com.example.TTMS.service;

import com.example.TTMS.entity.RideTicket;

public interface MailTemplateService {

  String sendOtpMail(String otp, String name);

}
