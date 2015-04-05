package com.garpr.android.views;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.garpr.android.R;
import com.garpr.android.models.Tournament;


public class TournamentSeparatorView extends FrameLayout implements View.OnClickListener {


    private LinearLayout mContainer;
    private OnClickListener mClickListener;
    private TextView mDate;
    private TextView mName;
    private Tournament mTournament;
    private ViewHolder mViewHolder;




    public static TournamentSeparatorView inflate(final Context context, final ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        return (TournamentSeparatorView) inflater.inflate(R.layout.view_tournament_separator_item,
                parent, false);
    }


    public TournamentSeparatorView(final Context context) {
        super(context);
    }


    public TournamentSeparatorView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }


    public TournamentSeparatorView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TournamentSeparatorView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public LinearLayout getContainerView() {
        return mContainer;
    }


    public TextView getDateView() {
        return mDate;
    }


    public TextView getNameView() {
        return mName;
    }


    public Tournament getTournament() {
        return mTournament;
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
        mContainer = (LinearLayout) findViewById(R.id.view_tournament_separator_item_container);
        mDate = (TextView) findViewById(R.id.view_tournament_separator_item_date);
        mName = (TextView) findViewById(R.id.view_tournament_separator_item_name);

        mContainer.setOnClickListener(this);
    }


    public void setOnClickListener(final OnClickListener l) {
        mClickListener = l;
    }


    public void setTournament(final Tournament tournament) {
        mTournament = tournament;
        mDate.setText(mTournament.getDateWrapper().getDay());
        mName.setText(mTournament.getName());
    }




    public final class ViewHolder extends RecyclerView.ViewHolder {


        private ViewHolder() {
            super(TournamentSeparatorView.this);
        }


        public TournamentSeparatorView getView() {
            return TournamentSeparatorView.this;
        }


    }


    public interface OnClickListener {


        void onClick(final TournamentSeparatorView v);


    }


}
