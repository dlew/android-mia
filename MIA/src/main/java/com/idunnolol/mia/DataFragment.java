package com.idunnolol.mia;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataFragment extends Fragment {

    public static final String TAG = DataFragment.class.getName();

    private QueryListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (QueryListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    public void query(String text) {
        new QueryTask().execute(text);
    }

    //////////////////////////////////////////////////////////////////////////
    // AsyncTasks

    private class QueryTask extends AsyncTask<String, Void, AudioStop> {

        @Override
        protected AudioStop doInBackground(String... params) {
            try {
                URL url = new URL("http://www.artsmia.org/mobile/audio_tour_query.php?query=" + params[0]);
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String str = convertStreamToString(in);

                    // Strip callback
                    String jsonStr = str.substring("callback('".length(), str.length() - ";)".length());
                    JSONObject json = new JSONObject(jsonStr);

                    AudioStop stop = null;
                    if (json.has("media_url")) {
                        stop = new AudioStop();
                        stop.mTitle = json.optString("title");
                        stop.mMediaUrl = json.optString("media_url");
                    }
                    return stop;
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(AudioStop audioStop) {
            mListener.onAudioStopLoaded(audioStop);
        }
    }

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    //////////////////////////////////////////////////////////////////////////
    // Listener interfaces

    public interface QueryListener {
        public void onAudioStopLoaded(AudioStop audioStop);
    }
}
