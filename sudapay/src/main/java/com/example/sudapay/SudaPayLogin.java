package com.example.sudapay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class SudaPayLogin extends Fragment implements Interfaces.LoginActions {

    TextInputLayout accountLayout , passwordLayout;
    EditText accountText , passwordText;
    Button login;
    Context context;
    String password = "";
    String accountNumber;
    NestedScrollView rootLayout;
    SudaPayTransaction transFragment ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout,container,false);
        accountLayout = (TextInputLayout) view.findViewById(R.id.textInputLayout);
        passwordLayout = (TextInputLayout) view.findViewById(R.id.textInputLayout2);
        accountText = (EditText) view.findViewById(R.id.edit1);
        passwordText = (EditText) view.findViewById(R.id.edit2);
        login = (Button) view.findViewById(R.id.button9);
        rootLayout =  view.findViewById(R.id.layout1);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();

        networkCall2();
        login.setOnClickListener((e)->{
            boolean bo = checkText();
            if(bo)
            {
                if(Utils.checkNetwork(context))
                {
                    netWorkCall();
                }
            }
        });
    }


    public boolean checkText()
    {
        boolean handler = true;

        password = passwordText.getText().toString();
        accountNumber = accountText.getText().toString();

        if(password.isEmpty())
        {
            System.out.println(" password is empty ");
            passwordLayout.setErrorEnabled(true);
            passwordLayout.setError(" Password Can Not be Empty");
            handler = false;
        }
        else
        {
            System.out.println("password Length "+ password.length());
            if(password.length()<4)
            {
                passwordLayout.setErrorEnabled(true);
                passwordLayout.setError(" short pass ");
                handler=false;
            }
            else
            {
                passwordLayout.setErrorEnabled(false);
            }
        }


        if(accountNumber.isEmpty())
        {
            accountLayout.setErrorEnabled(true);
            accountLayout.setError(" account Can Not be Empty");
            handler=false;
        }
        else
        {
            if(accountNumber.length()<10)
            {
                accountLayout.setErrorEnabled(true);
                accountLayout.setError(" short account Number  ");
                handler = false;
            }
            else
            {
                accountLayout.setErrorEnabled(false);
            }

        }


        return handler;
    }


    public void netWorkCall()
    {
        ArrayList<NameValuePair> posts = new ArrayList<>();
        posts.add(new BasicNameValuePair("accountNumber",accountNumber));
        posts.add(new BasicNameValuePair("pass",password));
        SyncTask task = new SyncTask(Constants.login,posts,Constants.Task.login,context);
        task.loginActions = this;
        task.execute();
    }


    public void networkCall2()
    {
        ArrayList<NameValuePair> posts = new ArrayList<>();
        posts.add(new BasicNameValuePair("spId",getString(R.string.SPID)));
        SyncTask task = new SyncTask(Constants.getApiInfo,posts,Constants.Task.getApiInfo,context);
        task.loginActions = this;
        task.execute();
    }

    @Override
    public void loginResult(Models.ResponseModel response) {
        if(response.Query==1)
        {
            transFragment = SudaPayTransaction.getInstance(password);
            Constants.accountNumber = accountNumber;
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.hide(this);
            transaction.commit();
            transaction = getFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.frame,transFragment);
            transaction.commit();

        }
        else
        {
            Snackbar.make(rootLayout,response.Cause,Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getApiInfo(Models.ApiModel apiModel) {
        if(apiModel.responseModel.Query==1)
        {
            Constants.apiModel = apiModel;

        }
        else
        {

        }
    }
}
