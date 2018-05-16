package com.raki.okostian;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Class for home page.
 *
 * @author Kostian Sasha
 * @version %I%, %G%
 */

public class Home extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View view = inflater.inflate(R.layout.content_home, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse( "tel:" + getResources().getString(R.string.telephone_number) ));
                //intent.setData(Uri.parse( new String("tel:"+R.string.telephone_number) ));
                startActivity(intent);
                //If you want to initiate the call directly without user's interaction ,
                // You can use action Intent.ACTION_CALL. In this case, you must add the
                // following permission in your AndroidManifest.xml:
                //<uses-permission android:name="android.permission.CALL_PHONE" />
            }
        });

        final CardView myCardView = view.findViewById(R.id.home_card_view);
        myCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCard("com.raki.okostian.BuyCards");
            }
        });

        final CardView mCardView = view.findViewById(R.id.home_card_view_contacts);
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCard("com.raki.okostian.Contacts");
            }
        });

        final CardView m3CardView = view.findViewById(R.id.home_card_view_3);
        m3CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCard("com.raki.okostian.AboutUs");
            }
        });

        return view;
    }

    private void clickCard(String name) {
        Fragment fragment = null;                                              //производительность
        try {
            fragment = (Fragment) Class.forName(name).newInstance();
        } catch (ClassNotFoundException | java.lang.InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null); //   запоминается обратная работ
        fragmentTransaction.commit();
    }
}