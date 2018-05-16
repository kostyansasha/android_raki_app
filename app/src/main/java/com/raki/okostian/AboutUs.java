package com.raki.okostian;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 *
 * @author Kostian Sasha
 * @version %I%, %G%
 */

public class AboutUs extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.content_about_us, container, false);

        //final CardView m3CardView = view.findViewById(R.id.aboutUS_button_contacts);
        Button button =  view.findViewById(R.id.aboutUS_button_contacts);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).displayView(R.id.nav_contanct_us);
            }
        });

        return view;
    }
}
