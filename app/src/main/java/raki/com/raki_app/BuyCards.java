package raki.com.raki_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for BuyCards page.
 *
 * @author Kostian Sasha
 * @version %I%, %G%
 */
public class BuyCards extends Fragment {

    private List<Lobster> lobsters;
    private void initialisationList(View v){
        lobsters = new ArrayList<>();
        lobsters.add(new Lobster("Семечки, 20-30г.",
                v.getResources().getInteger(R.integer.buy_gradation_price_1) +" грн.", R.drawable.green_1));
        lobsters.add(new Lobster("Мелкие 30-40г.",
                v.getResources().getInteger(R.integer.buy_gradation_price_2) +" грн.", R.drawable.green_1));
        lobsters.add(new Lobster("Средние 40-60г.",
                v.getResources().getInteger(R.integer.buy_gradation_price_3) +" грн.", R.drawable.green_1));
        lobsters.add(new Lobster("Крупные 60г.",
                v.getResources().getInteger(R.integer.buy_gradation_price_4) +" грн.", R.drawable.green_1));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.content_buy_cards, container, false);
        initialisationList(view);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        GridLayoutManager manager = new GridLayoutManager(this.getContext(), 2);
        rv.setLayoutManager(manager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(lobsters);
        rv.setAdapter(adapter);

        return view;
    }
}
