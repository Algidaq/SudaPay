package com.example.sudapay;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SudaPayResult extends Fragment {


    Button done;
    TextView doneText;
    MyView circle;
    public static float amount;




    public SudaPayResult(){}



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.suda_pay_result,container,false);

        done = view.findViewById(R.id.button);
        doneText = view.findViewById(R.id.textView4);
        circle = view.findViewById(R.id.myView2);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        doneText.setText(amount+" SDG transferred successfully To "+Constants.apiModel.appName);
        ObjectAnimator animator = ObjectAnimator.ofFloat(doneText,"alpha",0,1);
        animator.setDuration(4000);
        CircleAngleAnimation animation = new CircleAngleAnimation(circle, 70,70,313);
        animation.setDuration(6000);

        circle.startAnimation(animation);
        animator.start();

        done.setOnClickListener(e->{
            getActivity().finish();
            SudaPayActivity.callBack.onOperationCompleted(1);
        });


    }
}
