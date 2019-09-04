package com.example.sudapay;

import java.util.ArrayList;

public class Interfaces {

    public interface ResponseAction {
        void getResponse(Models.ResponseModel responseModel);
    }

    public interface LoginActions
    {
        void loginResult(Models.ResponseModel response);
        void getApiInfo(Models.ApiModel apiModel);
    }


    public interface SendResutlsAction{
        void getCustomerInfo(Models.CustomerModel customerModel);
        void getPass(String pass);
        void getTransactionInfo(Models.ResponseModel model);
    }


    public interface callBack{

        void onOperationCompleted(int Query);
    }


}
