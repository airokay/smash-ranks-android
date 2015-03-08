package com.garpr.android.models2;


import android.os.Parcel;
import android.os.Parcelable;

import com.garpr.android.misc.Constants;
import com.garpr.android.misc.ListUtils.AlphabeticallyComparable;
import com.garpr.android.misc.Utils;

import org.json.JSONException;
import org.json.JSONObject;


public class Player implements AlphabeticallyComparable, Parcelable {


    private final float mRating;
    private final int mRank;
    private final String mId;
    private final String mName;




    public Player(final JSONObject json) throws JSONException {
        mId = Utils.getJSONString(json, Constants.OPPONENT_ID, Constants.ID);
        mName = Utils.getJSONString(json, Constants.OPPONENT_NAME, Constants.NAME);
        mRating = (float) json.optDouble(Constants.RATING, Float.MIN_VALUE);
        mRank = json.optInt(Constants.RANK, Integer.MIN_VALUE);
    }


    public Player(final String id, final String name) {
        mId = id;
        mName = name;
        mRating = Float.MIN_VALUE;
        mRank = Integer.MIN_VALUE;
    }


    private Player(final Parcel source) {
        mRating = source.readFloat();
        mRank = source.readInt();
        mId = source.readString();
        mName = source.readString();
    }


    @Override
    public boolean equals(final Object o) {
        final boolean isEqual;

        if (this == o) {
            isEqual = true;
        } else if (o instanceof Player) {
            final Player p = (Player) o;

            if (mId.equals(p.getId()) && mName.equals(p.getName())) {
                if (hasCompetitionValues() && p.hasCompetitionValues()) {
                    isEqual = mRank == p.getRank() && mRating == p.getRating();
                } else if (!hasCompetitionValues() && !p.hasCompetitionValues()) {
                    isEqual = true;
                } else {
                    isEqual = false;
                }
            } else {
                isEqual = false;
            }
        } else {
            isEqual = false;
        }

        return isEqual;
    }


    @Override
    public char getFirstCharOfName() {
        return mName.charAt(0);
    }


    public String getId() {
        return mId;
    }


    public String getName() {
        return mName;
    }


    public int getRank() {
        return mRank;
    }


    public float getRating() {
        return mRating;
    }


    public boolean hasCompetitionValues() {
        return mRank != Integer.MIN_VALUE && mRating != Float.MIN_VALUE;
    }


    @Override
    public String toString() {
        return getName();
    }




    /*
     * Code necessary for the Android Parcelable interface is below. Read more here:
     * https://developer.android.com/intl/es/reference/android/os/Parcelable.html
     */


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeFloat(mRating);
        dest.writeInt(mRank);
        dest.writeString(mId);
        dest.writeString(mName);
    }


    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(final Parcel source) {
            return new Player(source);
        }


        @Override
        public Player[] newArray(final int size) {
            return new Player[size];
        }
    };


}
