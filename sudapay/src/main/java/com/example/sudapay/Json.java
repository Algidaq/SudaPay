package com.example.sudapay;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Json {


    public static Models.ResponseModel responseModelJson(String Response)
    {
        Models.ResponseModel handler = new Models.ResponseModel();

        try{

            JSONObject obj = new JSONObject(Response);
            handler.Query = obj.getInt("Query");
            if(handler.Query==1)
            {
            }
            else
            {
                handler.Cause = obj.getString("Cause");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            System.err.println(e);
        }


        return handler;
    }

    public static Models.ResponseModel loginJson(String Response)
    {
        Models.ResponseModel handler = new Models.ResponseModel();

        try{

            JSONObject obj = new JSONObject(Response);
            handler.Query = obj.getInt("Query");
            if(handler.Query==1)
            {
                handler.currentBalance = obj.getString("currentBalance");
            }
            else
            {
                handler.Cause = obj.getString("Cause");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            System.err.println(e);
        }


        return handler;
    }


    public static Models.ResponseModel makeTransactionMode(String response)
    {
        Models.ResponseModel model = new Models.ResponseModel();

        try{
            JSONObject obj = new JSONObject(response);
            model.Query = obj.getInt("Query");

            if(model.Query==1)
            {
                model.Cause = String.valueOf(obj.getDouble("currentBalance"));

            }
            else
            {
                model.Cause = obj.getString("Cause");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return model;
    }


    public static Models.CustomerModel customerJsonHelper(JSONObject obj2)
    {
        Models.CustomerModel model = new Models.CustomerModel();

        try{
            model.accountNumber = obj2.getString("accountNumber");
            model.firstName = obj2.getString("firstName");
            model.lastName = obj2.getString("lastName");
            model.businessName = obj2.getString("businessName");
            model.accountType = Integer.parseInt(obj2.getString("accountType"));
            model.profileImage = (obj2.getString("profile"));
            model.bankName = obj2.getString("bankName");
            model.phone = obj2.getString("Phone");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e);
        }

        return model;
    }


    public static Models.CustomerModel customerJson(String response)
    {
        Models.CustomerModel receiverModel = null;

        try{
            JSONObject obj = new JSONObject(response);
            int Query = obj.getInt("Query");

            if(Query==1)
            {
                receiverModel = customerJsonHelper(obj.getJSONObject("accountInfo"));
                String url = Constants.url+""+receiverModel.profileImage;
                receiverModel.image = Utils.normalConnection(url);
                receiverModel.responseModel = new Models.ResponseModel();
                receiverModel.responseModel.Query=1;
            }
            else
            {
                receiverModel = new Models.CustomerModel();
                receiverModel.responseModel = new Models.ResponseModel();
                receiverModel.responseModel.Query = -1;
                receiverModel.responseModel.Cause = obj.getString("Cause");
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println(e);
        }

        return receiverModel;
    }


    public static Models.ApiModel apiJson(String response)
    {
        Models.ApiModel model = new Models.ApiModel();
        model.responseModel = new Models.ResponseModel();
        model.responseModel.Query=-1;
        model.responseModel.Cause=" Nothing ";
        try{

            JSONObject obj = new JSONObject(response);
            int Query = obj.getInt("Query");

            if(Query==1)
            {
                model.responseModel.Query=1;
                obj = obj.getJSONObject("apiModel");
                model.appName = obj.getString("AppName");
                model.ownerAccountNumber = obj.getString("accountNumber");
                model.service = obj.getString("service");
                model.apiType = Integer.parseInt(obj.getString("appType"));
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return model;
    }

}
