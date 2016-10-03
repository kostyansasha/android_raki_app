package raki.com.raki_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        Button button = (Button) view.findViewById(R.id.buy_buttonBuy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Buy();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(null);    запоминается обратная работ
                fragmentTransaction.commit();

                //NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
                //((MainActivity) getActivity()).onNavigationItemSelected(navigationView.getMenu().getItem(0));
            }
        });

        return view;
    }

}