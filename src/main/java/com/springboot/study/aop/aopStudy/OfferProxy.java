package com.springboot.study.aop.aopStudy;

/**
 * Created by ps on 2017/10/16.
 */
public class OfferProxy implements IOffer {

    private IOffer delegate;

    public OfferProxy(IOffer delegate) {
        this.delegate = delegate;
    }

    public void setDelegate(IOffer delegate) {
        this.delegate = delegate;
    }

    @Override
    public void postOffer() {
        System.out.println("start1");
        delegate.postOffer();
        System.out.println("end1");
    }

    @Override
    public void modifyOffer() {
        System.out.println("start2");
        delegate.modifyOffer();
        System.out.println("end2");
    }
}
