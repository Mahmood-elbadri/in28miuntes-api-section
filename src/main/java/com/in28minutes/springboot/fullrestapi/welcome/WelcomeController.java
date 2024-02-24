package com.in28minutes.springboot.fullrestapi.welcome;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class WelcomeController {
    private MessageSource messageSource;

    public WelcomeController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/hello-internationalized")
    public String hello(){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message",null,"default message",locale);
    }
}
