package com.garpr.android.views;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.garpr.android.R;
import com.garpr.android.models.Player;


public class RankingItemView extends FrameLayout implements View.OnClickListener {


    private LinearLayout mContainer;
    private OnClickListener mClickListener;
    private Player mPlayer;
    private TextView mName;
    private TextView mRank;
    private TextView mRating;
    private ViewHolder mViewHolder;




    public RankingItemView(final Context context) {
        super(context);
    }


    public RankingItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }


    public RankingItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RankingItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public LinearLayout getContainerView() {
        return mContainer;
    }


    public TextView getNameView() {
        return mName;
    }


    public Player getPlayer() {
        return mPlayer;
    }


    public TextView getRankView() {
        return mRank;
    }


    public TextView getRatingView() {
        return mRating;
    }


    public ViewHolder getViewHolder() {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder();
        }

        return mViewHolder;
    }


    @Override
    public void onClick(final View v) {
        if (mClickListener != null) {
            mClickListener.onClick(this);
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContainer = (LinearLayout) findViewById(R.id.view_ranking_item_container);
        mName = (TextView) findViewById(R.id.view_ranking_item_name);
        mRank = (TextView) findViewById(R.id.view_ranking_item_rank);
        mRating = (TextView) findViewById(R.id.view_ranking_item_rating);

        mContainer.setOnClickListener(this);
    }


    public void setOnClickListener(final OnClickListener l) {
        mClickListener = l;
    }


    public void setPlayer(final Player player) {
        mPlayer = player;
        mName.setText(mPlayer.getName());
        mRank.setText(String.valueOf(mPlayer.getRank()));
        mRating.setText(mPlayer.getRatingTruncated());
    }




    public final class ViewHolder extends RecyclerView.ViewHolder {


        private ViewHolder() {
            super(RankingItemView.this);
        }


        public RankingItemView getView() {
            return RankingItemView.this;
        }


    }


    public interface OnClickListener {


        void onClick(final RankingItemView v);


    }


}
