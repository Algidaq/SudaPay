package com.example.sudapay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class SudaPayTransaction extends Fragment implements Interfaces.SendResutlsAction {

    TextView customerName,receiverAccountNumber,transferedAmount,businessName;
    CircularImageView receiverProfileImage;
    TextView personalOrBusiness;
    TextView bankName;
    TextView youPay,fee;
    TextView textToCustomer;
    Button send;
    String finalPrice;
    String json ="";
    String accountNumber;
    int api=-1;
    CoordinatorLayout coorLayout;
    int type = -1;//means customer
    Toolbar toolbar;
    private static String pass;

    public static SudaPayTransaction getInstance(String pass1)
    {
        pass = pass1;
        SudaPayTransaction sudaPayTransaction = new SudaPayTransaction();

        return sudaPayTransaction;
    }
    public SudaPayTransaction()

    {}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_and_send_layout,container,false);

        coorLayout = view.findViewById(R.id.coorLayout);
        textToCustomer = (TextView) view.findViewById(R.id.textView3);
        transferedAmount = (TextView) view.findViewById(R.id.textView20);
        customerName = (TextView) view.findViewById(R.id.textView23);
        businessName =  view.findViewById(R.id.textView24);
        receiverProfileImage = (CircularImageView) view.findViewById(R.id.circularImageView4);
        personalOrBusiness = (TextView) view.findViewById(R.id.textView26);
        bankName = (TextView) view.findViewById(R.id.textView27);
        fee = (TextView) view.findViewById(R.id.textView30);
        youPay = (TextView) view.findViewById(R.id.textView32);
        send = (Button) view.findViewById(R.id.SendPayment);
        receiverAccountNumber = view.findViewById(R.id.textView39);
        toolbar = view.findViewById(R.id.toolbar4);



        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent in1 = getActivity().getIntent();
        accountNumber = in1.getStringExtra("accountNumber");
        api=1;
        finalPrice = String.valueOf(in1.getFloatExtra("amount",0f));
        networkCall();
        textToCustomer.setText(Constants.apiModel.appName+" would Like to Receive "+finalPrice+" for "+Constants.apiModel.service);

        send.setOnClickListener((e)->{
           networkCall2(this.pass);
        });

        toolbar.setNavigationOnClickListener((e)->{
            getActivity().finish();
        });
    }


    public void networkCall()
    {

        send.setEnabled(false);
        transferedAmount.setText((Float.parseFloat(finalPrice)+0.00f)+"");
        fee.setText("2.00");
        youPay.setText((Float.parseFloat(finalPrice)+2.00f)+"");
        String numbers = "";
        char varChar[] = accountNumber.toCharArray();

        for(int i=0;i<accountNumber.length();i++)
        {
            if(i<5)
            {
                numbers+="*";
            }
            else
            {
                numbers+=varChar[i];
            }
        }
        receiverAccountNumber.setText(numbers);
        ArrayList<NameValuePair> posts  = new ArrayList<>();
        System.out.println("accountNumber "+ accountNumber);
        posts.add(new BasicNameValuePair("accountNumber",accountNumber));
        SyncTask task = new SyncTask(Constants.getReceiverInfo,posts, Constants.Task.getReceiverInfo,getContext());
        task.sendResutlsAction = this;
        task.execute();
    }


    public void networkCall2(String pass)
    {
        ArrayList<NameValuePair> posts = new ArrayList<>();
        posts.add(new BasicNameValuePair("sender",Constants.accountNumber));
        posts.add(new BasicNameValuePair("receiver",accountNumber));
        posts.add(new BasicNameValuePair("pass",pass));
        posts.add(new BasicNameValuePair("amount",youPay.getText().toString()));
        posts.add(new BasicNameValuePair("transfereType","-1"));
        posts.add(new BasicNameValuePair("api","1"));
        posts.add(new BasicNameValuePair("apiNumber",getString(R.string.SPID)));
        SyncTask task = new SyncTask(Constants.makeTransaction,posts, Constants.Task.makeTransaction,getContext());
        task.sendResutlsAction = this;
        task.execute();
    }

    @Override
    public void getCustomerInfo(Models.CustomerModel customerModel) {

        if(customerModel.responseModel.Query==1)
        {

            receiverProfileImage.setImageBitmap(customerModel.image);

            customerName.setText(customerModel.firstName+" "+customerModel.lastName);
            bankName.setText(customerModel.bankName);

                personalOrBusiness.setText("Business");
                businessName.setText(Constants.apiModel.appName);


            send.setEnabled(true);

        }
    }

    @Override
    public void getPass(String pass) {
        networkCall2(pass);
    }

    @Override
    public void getTransactionInfo(Models.ResponseModel model) {
        if(model.Query==1)
        {
            SudaPayResult.amount = getActivity().getIntent().getFloatExtra("amount",-1);
            SudaPayResult result = new SudaPayResult();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
            transaction.hide(this);
            transaction.commit();

            transaction = getFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
            transaction.replace(R.id.frame,result);
            transaction.commit();
        }
        else
        {
            Snackbar.make(coorLayout,model.Cause,Snackbar.LENGTH_SHORT).show();
            new Thread(()->{
                try {
                    Thread.sleep(2000);
                    getActivity().finish();
                    SudaPayActivity.callBack.onOperationCompleted(-1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
