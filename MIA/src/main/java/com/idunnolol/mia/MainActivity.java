package com.idunnolol.mia;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

public class MainActivity extends Activity implements DataFragment.QueryListener, SearchFragment.SearchFragmentListener {

    private DataFragment mDataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        if (savedInstanceState == null) {
            mDataFragment = new DataFragment();

            fm.beginTransaction()
                    .add(R.id.container, new SearchFragment())
                    .add(mDataFragment, DataFragment.TAG)
                    .commit();
        }
        else {
            mDataFragment = (DataFragment) fm.findFragmentByTag(DataFragment.TAG);
        }
    }

    //////////////////////////////////////////////////////////////////////////
    // QueryListener

    @Override
    public void onAudioStopLoaded(AudioStop audioStop) {
        if (audioStop != null) {
            Toast.makeText(this, "Loaded audio stop: " + audioStop.mTitle, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, getString(R.string.error_no_stop), Toast.LENGTH_SHORT).show();
        }
    }

    //////////////////////////////////////////////////////////////////////////
    // SearchFragmentListener

    @Override
    public void onQuery(String query) {
        if (TextUtils.isEmpty(query)) {
            Toast.makeText(this, getString(R.string.error_no_query), Toast.LENGTH_SHORT).show();
        }
        else {
            mDataFragment.query(query);
        }
    }
}
