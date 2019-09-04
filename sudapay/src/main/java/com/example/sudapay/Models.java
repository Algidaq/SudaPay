package com.example.sudapay;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Models {

    public static class ResponseModel implements Serializable
    {
        public int Query =-1;
        public String Cause = "";
        public String currentBalance = "";

        public ResponseModel(){}
    }


    public static class CustomerModel implements Serializable
    {
        String firstName="" ,lastName="";
        String profileImage="";
        String bankName="";
        String accountNumber="";
        int accountType =-1;
        String businessName ="";
        String phone = "";
        ResponseModel responseModel;
        Bitmap image;
        ApiModel apiModel;

    }

    public static class ApiModel implements Serializable
    {
        public String appName="";
        int apiType=-1;
        String ownerAccountNumber ="";
        ResponseModel responseModel;
        String service="";
    }

}
