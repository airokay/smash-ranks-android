package com.garpr.android.views;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.garpr.android.R;


public class SimpleSeparatorView extends FrameLayout implements View.OnClickListener {


    private FrameLayout mContainer;
    private OnClickListener mClickListener;
    private TextView mText;
    private ViewHolder mViewHolder;




    public static SimpleSeparatorView inflate(final Context context, final ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        return (SimpleSeparatorView) inflater.inflate(R.layout.view_simple_separator_item, parent,
                false);
    }


    public SimpleSeparatorView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }


    public FrameLayout getContainerView() {
        return mContainer;
    }


    public TextView getTextView() {
        return mText;
    }


    public ViewHolder getViewHolder() {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder();
        }

        return mViewHolder;
    }


    @Override
    public void onClick(final View v) {
        mClickListener.onClick(this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContainer = (FrameLayout) findViewById(R.id.view_simple_separator_item_container);
        mText = (TextView) findViewById(R.id.view_simple_separator_item_text);

        if (mClickListener != null) {
            mContainer.setOnClickListener(this);
        }
    }


    public void setOnClickListener(final OnClickListener l) {
        mClickListener = l;

        if (mContainer != null) {
            mContainer.setOnClickListener(this);
        }
    }


    public void setText(final CharSequence text) {
        mText.setText(text);
    }




    public final class ViewHolder extends RecyclerView.ViewHolder {


        private ViewHolder() {
            super(SimpleSeparatorView.this);
        }


        public SimpleSeparatorView getView() {
            return SimpleSeparatorView.this;
        }


    }


    public interface OnClickListener {


        void onClick(final SimpleSeparatorView v);


    }


}