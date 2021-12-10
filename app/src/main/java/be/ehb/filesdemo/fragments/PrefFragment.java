package be.ehb.filesdemo.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import be.ehb.filesdemo.R;

public class PrefFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
