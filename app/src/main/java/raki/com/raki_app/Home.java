package raki.com.raki_app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.host;

/**
 *
 * @author Kostian Sasha
 * @version %I%, %G%
 */

public class Home  extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View view = inflater.inflate(R.layout.content_home, container, false);

       /* Button button = (Button) view.findViewById(R.id.buy_buttonBuy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCard();
                //NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
                //((MainActivity) getActivity()).onNavigationItemSelected(navigationView.getMenu().getItem(0));
            }
        });*/

       /* RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.wide_card_itself);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(myAdapter);*/


        final CardView myCardView = (CardView) view.findViewById(R.id.home_card_view);
        myCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCard();
            }
        });

        return view;
    }

    private void clickCard() {
        Fragment fragment = new Buy();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null); //   запоминается обратная работ
        fragmentTransaction.commit();
    }
}