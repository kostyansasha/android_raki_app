package com.raki.okostian;

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
    private static View parentView;
    RecyclerViewAdapter(List<Lobster> lobsters) {
        this.lobsters = lobsters;
    }

    private static List<Order> orderList;
    public static void setOrderList(List<Order> orderList) {
        RecyclerViewAdapter.orderList = orderList;
    }


    public static void setParentView(View v) {
        parentView = v;
    }
    public static View getParentView() {
        return parentView;
    }

    @Override
    public LobsterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //View itemView = inflater.inflate(R.layout.content_buy_cards, parent, false);  //??????????
        View itemView = inflater.inflate(R.layout.item_buy_cards, parent, false);

        return new LobsterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.LobsterViewHolder holder, int position) {
        holder.name.setText(lobsters.get(position).getName());
        holder.price.setText(lobsters.get(position).getPrice() + "грн.");
        holder.lobsterPhoto.setImageResource(lobsters.get(position).getPictureResource());

        float weight = 0;
        if (orderList != null && orderList.size() > 0) {
            for (Order o : orderList) {
                if (o.getLobster().getName().equals(lobsters.get(position).getName())) {
                    weight = o.getWeights();
                }
            }
        }

        if (weight != 0){
            holder.descriptionWeight.setText(weight+"");
            holder.lWeight.setVisibility(View.VISIBLE);
        } else {
            holder.lWeight.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return lobsters.size();
    }

    public static class LobsterViewHolder extends RecyclerView.ViewHolder {

        protected CardView cardView;
        protected TextView name;
        protected ImageView lobsterPhoto;
        protected TextView price;

        //для показа заказаного веса
        protected TextView descriptionWeight;
        protected CardView lWeight;

        private boolean selected = false;

        public LobsterViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.buy_card_item);
            name = (TextView) view.findViewById(R.id.buy_cards_name_for_lobster);
            price = (TextView) view.findViewById(R.id.buy_cards_price_for_lobster);
            lobsterPhoto = (ImageView) view.findViewById(R.id.picture_of_lobster);

            cardView.setOnClickListener(new BuyCardClick(this, getParentView()));

            descriptionWeight = (TextView) itemView.findViewById(R.id.buy_cards_weights_of_order);
            lWeight = (CardView) view.findViewById(R.id.buy_cards_layout_of_weights);
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
