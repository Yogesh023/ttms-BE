package com.example.TTMS.service.impl;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.TTMS.service.MailTemplateService;

@Service
public class MailTemplateServiceImpl implements MailTemplateService {

  private final TemplateEngine templateEngine;

  public MailTemplateServiceImpl(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  @Override
  public String sendOtpMail(String otp, String name) {
    
    Context context = new Context();
    context.setVariable("userName", name);
    context.setVariable("otp", otp);
    return templateEngine.process("ride-otp", context);
  }

}
