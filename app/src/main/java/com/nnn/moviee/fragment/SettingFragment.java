package com.nnn.moviee.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.nnn.moviee.R;
import com.nnn.moviee.utils.S;
import com.nnn.moviee.utils.db.Pref;

public class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting);

        PreferenceManager.setDefaultValues(getContext(), R.xml.setting, false);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.key_language))) {
            S.log("shared  pref berubah");
            getActivity().recreate();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Pref.get(getContext()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Pref.get(getContext()).unregisterOnSharedPreferenceChangeListener(this);
    }
}
