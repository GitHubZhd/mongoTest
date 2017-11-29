package com.springboot.study.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;
/**
 * Created by ps on 2017/11/14.
 */
@RestController
public class InternationTest {

    @RequestMapping("/locale")
    public void test(){

        Locale locale=LocaleContextHolder.getLocale();

        /**
         * http://localhost:8080/locale?lang=en_US
         */
        System.out.println(locale);
    }
}
