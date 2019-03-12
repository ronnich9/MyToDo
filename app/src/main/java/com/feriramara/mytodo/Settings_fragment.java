package com.feriramara.mytodo;


import android.os.Bundle;

import android.util.Log;

import androidx.preference.PreferenceFragmentCompat;

public class Settings_fragment extends PreferenceFragmentCompat {

    public static final String PREF_VIBRATION_MODE = "vibration_pref_key";
    public static final String PREF_US_MODE = "us_format_pref_key";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_layout, rootKey);

        Log.d("ONCREATE", PREF_VIBRATION_MODE);
    }


}