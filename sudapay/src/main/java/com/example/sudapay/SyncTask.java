package com.example.sudapay;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import cz.msebera.android.httpclient.NameValuePair;

public class SyncTask extends AsyncTask<Void,Void,Integer>{

    int Query =-1 ;
    Context context;
    ArrayList<NameValuePair> posts;
    String url;
    Constants.Task task;
    Models.ResponseModel responseModel;

    Interfaces.ResponseAction responseAction;
    Interfaces.LoginActions loginActions;
    Interfaces.SendResutlsAction sendResutlsAction;
    Models.CustomerModel customerModel;
    Models.ApiModel apiModel;

    public SyncTask(String url , ArrayList<NameValuePair> posts , Constants.Task task , Context context)
    {
        this.context = context;
        this.url = url;
        this.posts = posts;
        this.task =task;
    }


    @Override
    protected Integer doInBackground(Void... voids) {
        String response="";
        response = Utils.normalConnection(url,context,posts);
        if(response.isEmpty()==false)
        Query = deciedTask(response);

        return Query;
    }

    public int deciedTask(String response)
    {
        int query = -1;


        switch(task)
        {
            case checkApi:
            {
                responseModel = Json.responseModelJson(response);
                if(responseModel.Query==1)
                {
                    query=1;
                }
            }
            break;

            case login:
            {
                responseModel = Json.loginJson(response);
                if(responseModel.Query==1)
                {
                    query=1;
                }
            }
            break;


            case getReceiverInfo:
            {
                customerModel = Json.customerJson(response);
                if(customerModel.responseModel.Query != -1)
                    query=1;
            }
            break;

            case getApiInfo:
            {
                apiModel = Json.apiJson(response);
                if(apiModel.responseModel.Query != -1)
                    query=1;
            }
            break;

            case makeTransaction:
            {
                responseModel =  Json.makeTransactionMode(response);
                query = responseModel.Query;
            }


        }



        return query;
    }


    @Override
    protected void onPostExecute(Integer query) {

        Models.ResponseModel model2 = new Models.ResponseModel();
        if (query == 1)
        {
            switch (task) {
                case checkApi: {
                    responseAction.getResponse(responseModel);
                }
                break;
                case login:
                {
                    loginActions.loginResult(responseModel);
                }
                break;

                case getReceiverInfo:
                {
                    sendResutlsAction.getCustomerInfo(customerModel);
                }
                break;

                case getApiInfo:
                {
                    loginActions.getApiInfo(apiModel);
                }
                break;

                case makeTransaction:
                {
                   sendResutlsAction.getTransactionInfo(responseModel);
                }
                break;
            }
        }
        else
        {
            model2.Query = -1;
            model2.Cause = " No Internet Connection ";
            customerModel = new Models.CustomerModel();
            customerModel.responseModel=model2;


            switch (task) {
                case checkApi: {
                    responseAction.getResponse(model2);
                }
                break;

                case login:
                {
                    loginActions.loginResult(model2);
                }
                break;

                case getReceiverInfo:
                {
                    sendResutlsAction.getCustomerInfo(customerModel);
                }
                break;

                case getApiInfo:
                {
                    loginActions.getApiInfo(apiModel);
                }
                break;

                case makeTransaction:
                {
                    sendResutlsAction.getTransactionInfo(responseModel);
                }
                break;
            }
        }
    }

}

