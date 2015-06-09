package com.garpr.android.misc;


import android.content.Context;
import android.text.TextUtils;
import android.view.MenuItem;

import com.garpr.android.App;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public final class Utils {


    public static final Random RANDOM = new Random();
    private static final String TAG = "Utils";




    public static boolean areAnyObjectsNull(final Object... objects) {
        if (objects == null || objects.length == 0) {
            return true;
        }

        for (final Object object : objects) {
            if (object == null) {
                return true;
            }
        }

        return false;
    }


    public static String getJSONString(final JSONObject json, final String first,
            final String second) throws JSONException {
        final String string;

        if (json.has(first)) {
            string = json.getString(first);
        } else {
            string = json.getString(second);
        }

        return string;
    }


    public static int googlePlayServicesConnectionStatus() {
        final Context context = App.getContext();
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
    }


    public static boolean googlePlayServicesAreAvailable() {
        final int connectionResult = googlePlayServicesConnectionStatus();
        final boolean googlePlayServicesAreAvailable;

        if (connectionResult == ConnectionResult.SUCCESS) {
            googlePlayServicesAreAvailable = true;
        } else {
            Console.w(TAG, "Google Play Services are unavailable (connectionResult " +
                    connectionResult + ')');
            googlePlayServicesAreAvailable = false;
        }

        return googlePlayServicesAreAvailable;
    }


    public static void hideMenuItems(final MenuItem... items) {
        setMenuItemsVisibility(false, items);
    }


    private static void setMenuItemsVisibility(final boolean visible, final MenuItem... items) {
        if (items != null && items.length >= 1) {
            for (final MenuItem item : items) {
                if (item != null) {
                    item.setVisible(visible);
                }
            }
        }
    }


    public static void showMenuItems(final MenuItem... items) {
        setMenuItemsVisibility(true, items);
    }


    /**
     * Checks a collection of {@link CharSequence} objects to see if any single one of them is
     * null, empty, or just whitespace.
     *
     * @param sequences
     * The collection of {@link CharSequence} objects to check.
     *
     * @return
     * Returns false if any single one of the given {@link CharSequence} objects is null, empty,
     * or just whitespace.
     */
    public static boolean validStrings(final CharSequence... sequences) {
        if (sequences == null || sequences.length == 0) {
            return false;
        }

        for (final CharSequence sequence : sequences) {
            if (TextUtils.isEmpty(sequence) || TextUtils.getTrimmedLength(sequence) == 0) {
                return false;
            }
        }

        return true;
    }


}
