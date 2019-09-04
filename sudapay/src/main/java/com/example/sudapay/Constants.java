package com.example.sudapay;

public class Constants  {

    public static String url = "https://gidaq.000webhostapp.com/SudaPay/";
    public static String checkApi = url+"checkApi.php";
    public static String login = url+"logIn.php";
    public static String getReceiverInfo = url+"getReceiverInfo.php";
    public static String makeTransaction = url+"makeTransaction.php";
    public static String getApiInfo = url+"getApiInfo.php";
    public static String accountNumber = "";
    public static Models.ApiModel apiModel= null;



    public enum Task
    {
        checkApi,login,getReceiverInfo,makeTransaction,getApiInfo
    }
}
