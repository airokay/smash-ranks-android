package com.garpr.android.views;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.garpr.android.R;
import com.garpr.android.models.Match;
import com.garpr.android.models.Player;


public class MatchItemView extends FrameLayout {


    private LinearLayout mContainer;
    private Match mMatch;
    private TextView mLoser;
    private TextView mWinner;
    private ViewHolder mViewHolder;




    public static MatchItemView inflate(final Context context, final ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        return (MatchItemView) inflater.inflate(R.layout.view_match_item, parent, false);
    }


    public MatchItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }


    public LinearLayout getContainerView() {
        return mContainer;
    }


    public TextView getLoserView() {
        return mLoser;
    }


    public Match getMatch() {
        return mMatch;
    }


    public ViewHolder getViewHolder() {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder();
        }

        return mViewHolder;
    }


    public TextView getWinnerView() {
        return mWinner;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContainer = (LinearLayout) findViewById(R.id.view_match_item_container);
        mLoser = (TextView) findViewById(R.id.view_match_item_loser);
        mWinner = (TextView) findViewById(R.id.view_match_item_winner);
    }


    public void setMatch(final Match match) {
        mMatch = match;

        final Player loser = match.getLoser();
        mLoser.setText(loser.getName());

        final Player winner = mMatch.getWinner();
        mWinner.setText(winner.getName());
    }


    public void setOnClickListener(final OnClickListener l) {
        if (l == null) {
            mContainer.setClickable(false);
        } else {
            mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    l.onClick(MatchItemView.this);
                }
            });
        }
    }


    public void setOnLongClickListener(final OnLongClickListener l) {
        if (l == null) {
            mContainer.setLongClickable(false);
        } else {
            mContainer.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    return l.onLongClick(MatchItemView.this);
                }
            });
        }
    }




    public final class ViewHolder extends RecyclerView.ViewHolder {


        private ViewHolder() {
            super(MatchItemView.this);
        }


        public MatchItemView getView() {
            return MatchItemView.this;
        }


    }


    public interface OnClickListener {


        void onClick(final MatchItemView v);


    }


    public interface OnLongClickListener {


        boolean onLongClick(final MatchItemView v);


    }


}
