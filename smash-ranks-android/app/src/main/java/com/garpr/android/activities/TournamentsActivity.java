package com.garpr.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.garpr.android.R;
import com.garpr.android.misc.Constants;
import com.garpr.android.misc.Networking;
import com.garpr.android.models.Tournament;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Turok on 9/20/2014.
 */
public class TournamentsActivity extends BaseActivity{

    private static final String TAG = TournamentsActivity.class.getSimpleName();

    private ListView mList;

    private ArrayList<Tournament> mTournaments;
    private ListView mListView;
    private ProgressBar mProgress;
    private TournamentAdapter mAdapter;
    private TextView mError;


    @Override
    protected int getContentView() {
        return R.layout.activity_tournaments;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        downloadTournaments();
    }

    private void findViews() {
        mListView = (ListView) findViewById(R.id.activity_tournaments_list);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mError = (TextView) findViewById(R.id.activity_tournaments_error);
    }


    private void showList(){
        mAdapter = new TournamentAdapter();
        mListView.setAdapter(mAdapter);
        mProgress.setVisibility(View.GONE);
        invalidateOptionsMenu();
    }

    private void downloadTournaments(){
        Networking.Callback callback = new Networking.Callback(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Network exception when downloading tournaments!", error);
                showError();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    ArrayList<Tournament> tournamentsList = new ArrayList<Tournament>();
                    JSONArray tournaments = response.getJSONArray(Constants.TOURNAMENTS);
                    for(int i = 0; i < tournaments.length() ; ++i ){
                        JSONObject tournamentJSON = tournaments.getJSONObject(i);
                        try {
                            Tournament tournament = new Tournament(tournamentJSON);
                            tournamentsList.add(tournament);
                        } catch (JSONException e) {
                            Log.e(TAG, "Exception when building tournament at index " + i, e);
                        }
                    }
                    tournamentsList.trimToSize();
                    mTournaments = tournamentsList;
                    showList();
                } catch(JSONException e){
                    showError();
                }
            }
        };

        Networking.getTournaments(this, callback);
    }

    private void showError() {
        mProgress.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
    }

    public static void start(final Activity activity) {
        final Intent intent = new Intent(activity, TournamentsActivity.class);
        activity.startActivity(intent);
    }


    private class TournamentAdapter extends BaseAdapter{
        private final LayoutInflater mInflater;
        private TournamentAdapter() {
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mTournaments.size();
        }

        @Override
        public Tournament getItem(int i) {
            return mTournaments.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = mInflater.inflate(R.layout.model_tournament, viewGroup, false);
            }

            ViewHolder holder = (ViewHolder) view.getTag();

            if (holder == null) {
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            final Tournament tournament = getItem(i);
            holder.mDate.setText(tournament.getDate());
            holder.mName.setText(tournament.getName());

            return view;
        }
    }


    private final static class ViewHolder {

        private final TextView mName;
        private final TextView mDate;

        private ViewHolder(final View view) {
            mDate = (TextView) view.findViewById(R.id.model_tournament_date);
            mName = (TextView) view.findViewById(R.id.model_tournament_name);
        }
    }



}
