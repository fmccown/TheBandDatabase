package com.zybooks.thebanddatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class ListFragment extends Fragment {

    // For the activity to implement
    public interface OnBandSelectedListener {
        void onBandSelected(int bandId);
    }

    // Reference to the activity
    private OnBandSelectedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        LinearLayout layout = (LinearLayout) view;

        // Create the buttons using the band names and ids from BandDatabase
        List<Band> bandList = BandDatabase.get(getContext()).getBands();
        for (int i = 0; i < bandList.size(); i++) {
            Button button = new Button(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 10);   // 10 px
            button.setLayoutParams(layoutParams);

            // Set the text to the band's name and tag to the band ID
            Band band = BandDatabase.get(getContext()).getBand(i + 1);
            button.setText(band.getName());
            button.setTag(Integer.toString(band.getId()));

            // All the buttons have the same click listener
            button.setOnClickListener(buttonClickListener);

            // Add the child to the LinearLayout
            layout.addView(button);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBandSelectedListener) {
            mListener = (OnBandSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBandSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Notify activity of band selection
            String bandId = (String) view.getTag();
            mListener.onBandSelected(Integer.parseInt(bandId));
        }
    };
}
