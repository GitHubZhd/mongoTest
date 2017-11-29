package com.springboot.study.test;

/**
 * Created by ps on 2017/8/15.
 */
public class NewQRCodeModel {

    private String code;

    private NewQRCodeSub qRcode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public NewQRCodeSub getqRcode() {
        return qRcode;
    }

    public void setqRcode(NewQRCodeSub qRcode) {
        this.qRcode = qRcode;
    }
}
