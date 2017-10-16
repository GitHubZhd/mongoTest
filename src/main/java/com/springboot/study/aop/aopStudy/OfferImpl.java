package com.springboot.study.aop.aopStudy;

/**
 * Created by ps on 2017/10/16.
 */
public class OfferImpl implements IOffer {
    @Override
    public void postOffer() {
        System.out.println("--------------");
    }

    @Override
    public void modifyOffer() {
        System.out.println("=================");
    }
}
