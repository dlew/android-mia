package com.idunnolol.mia;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchFragment extends Fragment {

    private EditText mEditText;
    private Button mButton;

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
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(getActivity(), getString(R.string.error_no_query), Toast.LENGTH_SHORT).show();
        }
        else {
            // TODO: Query for stop #
        }
    }
}
