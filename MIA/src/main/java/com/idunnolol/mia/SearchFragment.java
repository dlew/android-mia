package com.idunnolol.mia;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SearchFragment extends Fragment {

    private SearchFragmentListener mListener;

    private EditText mEditText;
    private Button mButton;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (SearchFragmentListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mEditText = (EditText) rootView.findViewById(R.id.search_edit_text);
        mButton = (Button) rootView.findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(mEditText.getText().toString());
            }
        });

        return rootView;
    }

    private void search(String text) {
        mListener.onQuery(text);
    }

    //////////////////////////////////////////////////////////////////////////
    // Listener interfaces

    public interface SearchFragmentListener {
        public void onQuery(String query);
    }
}
