package raki.com.raki_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter class for lobsters list.
 *
 * @author Kostian Sasha
 * @version %I%, %G%
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.LobsterViewHolder> {

    private List<Lobster> lobsters;
    RecyclerViewAdapter(List<Lobster> lobsters) {
        this.lobsters = lobsters;
    }

    @Override
    public LobsterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //View itemView = inflater.inflate(R.layout.content_buy_cards, parent, false);  //??????????
        View itemView = inflater.inflate(R.layout.item_buy_cards, parent, false);
        LobsterViewHolder lvh = new LobsterViewHolder(itemView);

        return lvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.LobsterViewHolder holder, int position) {
        holder.name.setText(lobsters.get(position).getName());
        holder.price.setText(lobsters.get(position).getPrice());
        holder.lobsterPhoto.setImageResource(lobsters.get(position).getPictureResource());
    }

    @Override
    public int getItemCount() {
        return lobsters.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class LobsterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected CardView cardView;
        protected TextView name;
        protected ImageView lobsterPhoto;
        protected TextView price;

        public LobsterViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.buy_card_item);
            name = (TextView) view.findViewById(R.id.buy_cards_name_for_lobster);
            price = (TextView) view.findViewById(R.id.buy_cards_price_for_lobster);
            lobsterPhoto = (ImageView) view.findViewById(R.id.picture_of_lobster);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new Buy();
            MainActivity myActivity = (MainActivity) v.getContext();

            FragmentManager fragmentManager = myActivity.getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null); //   запоминается обратная работ
            fragmentTransaction.commit();
        }
    }
}
