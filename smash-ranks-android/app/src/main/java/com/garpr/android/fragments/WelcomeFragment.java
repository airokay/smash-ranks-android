package com.garpr.android.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.garpr.android.R;


public class WelcomeFragment extends BaseFragment {


    private static final String TAG = "WelcomeFragment";

    private ImageButton mNext;
    private Listener mListener;
    private TextView mWelcomeText;




    public static WelcomeFragment create() {
        return new WelcomeFragment();
    }


    private void findViews() {
        final View view = getView();
        mNext = (ImageButton) view.findViewById(R.id.fragment_welcome_next);
        mWelcomeText = (TextView) view.findViewById(R.id.fragment_welcome_text);
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_welcome;
    }


    @Override
    protected String getFragmentName() {
        return TAG;
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        prepareViews();
    }


    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mListener = (Listener) activity;
    }


    private void prepareViews() {
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mListener.onWelcomeNextClick();
            }
        });

        mWelcomeText.setText(Html.fromHtml(getString(R.string.gar_pr_welcome_text)));
    }




    public interface Listener {


        void onWelcomeNextClick();


    }


}
