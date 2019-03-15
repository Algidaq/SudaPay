package com.example.sudapay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class SudaPayActivity extends AppCompatActivity {

    ProgressBar bar;
    TextView erroText;
    SudaPayLogin loginFragment = new SudaPayLogin();
    SudaPayTransaction transactionFragment = new SudaPayTransaction();
    Fragment holder ;

    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction ;

    public static Interfaces.callBack callBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suda_pay_layout);
        Utils.ChanageBarColor(getWindow());
        init();
        networkCall();
    }

    public void init()
    {
        erroText = findViewById(R.id.textView2);
        bar = findViewById(R.id.progressBar);

    }

    public void networkCall()
    {
        ArrayList<NameValuePair> posts  = new ArrayList<>();
        posts.add(new BasicNameValuePair("spId",getApplicationContext().getString(R.string.SPID)));
        System.out.println("packageName "+ getPackageName());
        posts.add(new BasicNameValuePair("packageName",getPackageName()));
        posts.add(new BasicNameValuePair("sendFrom","2"));
        SyncTask syncTask = new SyncTask(Constants.checkApi,posts, Constants.Task.checkApi,this);
        syncTask.responseAction = responseModel -> {
            bar.setVisibility(View.GONE);

            if(responseModel.Query==1)
            {
                addFragment(loginFragment);
            }
            else
            {
                erroText.setVisibility(View.VISIBLE);
                erroText.setText(responseModel.Cause);
            }

        };
        syncTask.execute();
    }

    public void hideFragment()
    {
        transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.hide(holder);
        transaction.commit();
    }


    public void showFragment(Fragment fragment)
    {
        transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.show(fragment);
        transaction.commit();
    }


    public void addFragment(Fragment fragment)
    {
        transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.frame,fragment);
        transaction.commit();
    }
}
