package com.springboot.study.test;

/**
 * Created by ps on 2017/10/19.
 */
public class QrCode {

    private String qRcode;

    public String getqRcode() {
        return qRcode;
    }

    public void setqRcode(String qRcode) {
        this.qRcode = qRcode;
    }

    @Override
    public String toString() {
        return "QrCode{" +
                "qRcode='" + qRcode + '\'' +
                '}';
    }
}
