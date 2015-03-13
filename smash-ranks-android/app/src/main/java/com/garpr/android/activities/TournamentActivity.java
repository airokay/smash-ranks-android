package com.garpr.android.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.garpr.android.R;
import com.garpr.android.data.ResponseOnUi;
import com.garpr.android.data.Tournaments;
import com.garpr.android.fragments.TournamentMatchesFragment;
import com.garpr.android.fragments.TournamentPlayersFragment;
import com.garpr.android.fragments.TournamentViewPagerFragment;
import com.garpr.android.misc.Analytics;
import com.garpr.android.misc.Console;
import com.garpr.android.misc.Constants;
import com.garpr.android.misc.FlexibleSwipeRefreshLayout;
import com.garpr.android.models.Tournament;
import com.garpr.android.models.TournamentBundle;


public class TournamentActivity extends BaseToolbarActivity implements
        TournamentViewPagerFragment.Listener, SwipeRefreshLayout.OnRefreshListener {


    private static final int TOURNAMENT_FRAGMENT_COUNT = 2;
    private static final int TOURNAMENT_FRAGMENT_MATCHES = 1;
    private static final int TOURNAMENT_FRAGMENT_PLAYERS = 0;
    private static final String CNAME = "com.garpr.android.activities.TournamentActivity";
    private static final String EXTRA_TOURNAMENT = CNAME + ".EXTRA_TOURNAMENT";
    private static final String KEY_BUNDLE = "KEY_BUNDLE";
    private static final String TAG = "TournamentActivity";

    private boolean mIsLoading;
    private FlexibleSwipeRefreshLayout mRefreshLayout;
    private LinearLayout mErrorView;
    private PagerSlidingTabStrip mTabStrip;
    private Tournament mTournament;
    private TournamentBundle mBundle;
    private ViewPager mViewPager;




    public static void start(final Activity activity, final Tournament tournament) {
        final Intent intent = new Intent(activity, TournamentActivity.class);
        intent.putExtra(EXTRA_TOURNAMENT, tournament);
        activity.startActivity(intent);
    }


    @Override
    public void attachToRefreshLayout(final RecyclerView recyclerView) {
        mRefreshLayout.setScrollingView(recyclerView);
    }


    private void fetchTournament() {
        setLoading(true);

        final ResponseOnUi<TournamentBundle> response = new ResponseOnUi<TournamentBundle>(TAG, this) {
            @Override
            public void errorOnUi(final Exception e) {
                Console.e(TAG, "Exception when fetching tournament", e);
                showError();

                Analytics.report(e, Constants.TOURNAMENT).send();
            }


            @Override
            public void successOnUi(final TournamentBundle object) {
                mBundle = object;
                prepareViewPager();
            }
        };

        Tournaments.getTournament(response, mTournament.getId());
    }


    private void findViews() {
        mErrorView = (LinearLayout) findViewById(R.id.activity_tournament_error);
        mRefreshLayout = (FlexibleSwipeRefreshLayout) findViewById(R.id.activity_tournament_refresh);
        mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.activity_tournament_tab_strip);
        mViewPager = (ViewPager) findViewById(R.id.activity_tournament_view_pager);
    }


    @Override
    protected String getActivityName() {
        return TAG;
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_tournament;
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntentData();
        setTitle(mTournament.getName());
        findViews();

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mBundle = savedInstanceState.getParcelable(KEY_BUNDLE);
        }

        if (mBundle == null) {
            fetchTournament();
        } else {
            prepareViewPager();
        }
    }


    @Override
    public void onRefresh() {
        if (!mIsLoading) {
            mErrorView.setVisibility(View.GONE);
            fetchTournament();
        }
    }


    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mBundle != null) {
            outState.putParcelable(KEY_BUNDLE, mBundle);
        }
    }


    private void prepareViewPager() {
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(new TournamentFragmentAdapter());
        mTabStrip.setShouldExpand(true);
        mTabStrip.setViewPager(mViewPager);
        setLoading(false);
    }


    private void readIntentData() {
        final Intent intent = getIntent();
        mTournament = intent.getParcelableExtra(EXTRA_TOURNAMENT);
    }


    private void setLoading(final boolean isLoading) {
        mIsLoading = isLoading;
        mRefreshLayout.setRefreshing(isLoading);
    }


    private void showError() {
        mViewPager.setAdapter(null);
        mViewPager.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        setLoading(false);
    }




    private final class TournamentFragmentAdapter extends FragmentPagerAdapter {


        private TournamentFragmentAdapter() {
            super(getSupportFragmentManager());
        }


        @Override
        public int getCount() {
            return TOURNAMENT_FRAGMENT_COUNT;
        }


        @Override
        public Fragment getItem(final int position) {
            final Fragment fragment;

            switch (position) {
                case TOURNAMENT_FRAGMENT_PLAYERS:
                    fragment = TournamentPlayersFragment.create(mBundle);
                    break;

                case TOURNAMENT_FRAGMENT_MATCHES:
                    fragment = TournamentMatchesFragment.create(mBundle);
                    break;

                default:
                    // this should never happen
                    throw new RuntimeException("invalid position: " + position);
            }

            return fragment;
        }


        @Override
        public CharSequence getPageTitle(final int position) {
            final CharSequence title;

            switch (position) {
                case TOURNAMENT_FRAGMENT_PLAYERS:
                    title = getString(R.string.players);
                    break;

                case TOURNAMENT_FRAGMENT_MATCHES:
                    title = getString(R.string.matches);
                    break;

                default:
                    // this should never happen
                    throw new RuntimeException("invalid position: " + position);
            }

            return title;
        }


    }


}