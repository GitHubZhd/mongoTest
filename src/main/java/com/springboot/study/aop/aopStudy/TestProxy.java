package com.springboot.study.aop.aopStudy;

/**
 * Created by ps on 2017/10/16.
 */
public class TestProxy {

//    public static void main(String[] args) {
//        IOffer offer=new OfferProxy(new OfferImpl());
//        offer.postOffer();
//        offer.modifyOffer();
//    }
    public static void main(String[] args) {
        IOffer offer=new OfferProxy((IOffer) new ProxyFactory().bind(new OfferImpl()));
        offer.postOffer();
        offer.modifyOffer();
    }
}
