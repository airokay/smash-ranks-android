package com.garpr.android.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuItem;

import com.garpr.android.R;
import com.garpr.android.data.User;
import com.garpr.android.fragments.PlayersFragment;
import com.garpr.android.fragments.RegionsFragment;
import com.garpr.android.misc.NonSwipeableViewPager;
import com.garpr.android.misc.OnItemSelectedListener;
import com.garpr.android.models.Player;
import com.garpr.android.models.Region;


public class OnboardingActivity extends BaseActivity implements
        OnItemSelectedListener {


    private static final int ONBOARDING_FRAGMENT_COUNT = 2;
    private static final int ONBOARDING_FRAGMENT_PLAYERS = 1;
    private static final int ONBOARDING_FRAGMENT_REGIONS = 0;

    private MenuItem mGo;
    private MenuItem mNext;
    private MenuItem mSkip;
    private NonSwipeableViewPager mViewPager;
    private PlayersFragment mPlayersFragment;
    private RegionsFragment mRegionsFragment;




    private void createFragments() {
        mPlayersFragment = PlayersFragment.create();
        mRegionsFragment = RegionsFragment.create(false);
    }


    private void findViews() {
        mViewPager = (NonSwipeableViewPager) findViewById(R.id.activity_onboarding_pager);
    }


    private void finishOnboarding() {
        // note that at this point, it's possible for the user to have hit the skip button, and
        // therefore will not have selected a specific Player (so it'll be null here)
        final Player player = mPlayersFragment.getSelectedPlayer();
        final Region region = mRegionsFragment.getSelectedRegion();
        User.setData(player, region);

        RankingsActivity.start(this);
        finish();
    }


    @Override
    protected boolean isNavigationDrawerEnabled() {
        return false;
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_onboarding;
    }


    @Override
    protected int getOptionsMenu() {
        return R.menu.activity_onboarding;
    }


    private void nextOnboardingStep() {
        switch (mViewPager.getCurrentItem()) {
            case ONBOARDING_FRAGMENT_REGIONS:
                mViewPager.setCurrentItem(ONBOARDING_FRAGMENT_PLAYERS, true);
                mNext.setVisible(false);
                mSkip.setVisible(true);
                mGo.setVisible(true);
                break;

            default:
                // this should never happen
                throw new RuntimeException();
        }
    }


    @Override
    public void onBackPressed() {
        if (mViewPager == null || mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            switch (mViewPager.getCurrentItem()) {
                case ONBOARDING_FRAGMENT_PLAYERS:
                    mViewPager.setCurrentItem(ONBOARDING_FRAGMENT_REGIONS, true);
                    mPlayersFragment.clearSelectedPlayer();
                    mGo.setVisible(false);
                    mGo.setEnabled(false);
                    mSkip.setVisible(false);
                    mNext.setVisible(true);
                    break;

                default:
                    // this should never happen
                    throw new RuntimeException();
            }
        }
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        findViews();
        createFragments();
        prepareViews();
    }


    @Override
    public void onItemSelected() {
        switch (mViewPager.getCurrentItem()) {
            case ONBOARDING_FRAGMENT_REGIONS:
                mNext.setEnabled(true);
                break;

            case ONBOARDING_FRAGMENT_PLAYERS:
                mGo.setEnabled(true);
                break;

            default:
                // this should never happen
                throw new RuntimeException();
        }
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_onboarding_menu_go:
            case R.id.activity_onboarding_menu_skip:
                finishOnboarding();
                break;

            case R.id.activity_onboarding_menu_next:
                nextOnboardingStep();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        mGo = menu.findItem(R.id.activity_onboarding_menu_go);
        mNext = menu.findItem(R.id.activity_onboarding_menu_next);
        mSkip = menu.findItem(R.id.activity_onboarding_menu_skip);
        return super.onPrepareOptionsMenu(menu);
    }


    private void prepareViews() {
        mViewPager.setAdapter(new OnboardingFragmentAdapter());
    }




    private final class OnboardingFragmentAdapter extends FragmentPagerAdapter {


        private OnboardingFragmentAdapter() {
            super(getSupportFragmentManager());
        }


        @Override
        public int getCount() {
            return ONBOARDING_FRAGMENT_COUNT;
        }


        @Override
        public Fragment getItem(final int position) {
            final Fragment fragment;

            switch (position) {
                case ONBOARDING_FRAGMENT_REGIONS:
                    fragment = mRegionsFragment;
                    break;

                case ONBOARDING_FRAGMENT_PLAYERS:
                    fragment = mPlayersFragment;
                    break;

                default:
                    // this should never happen
                    throw new RuntimeException();
            }

            return fragment;
        }


    }


}
