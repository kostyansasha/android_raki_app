package com.raki.okostian;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.OrderViewHolder> {

    private List<Order> orderArray;

    OrderRecyclerViewAdapter(List<Order> orderArray) {
        this.orderArray = orderArray;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.order_item_view, parent, false);

        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        holder.name.setText(orderArray.get(position).getLobster().getName());

        float price = orderArray.get(position).getWeights()* orderArray.get(position).getLobster().getPrice();
        holder.totalCoast.setText(
                Math.round(orderArray.get(position).getWeights()) + " кг.\n" +
                Math.round(price) + " грн.");

        holder.lobsterPhoto.setImageResource(orderArray.get(position).getLobster().getPictureResource());
    }

    @Override
    public int getItemCount() {
        return orderArray.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        protected CardView cardView;
        protected TextView name;
        protected ImageView lobsterPhoto;
        protected TextView totalCoast;

        public OrderViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.order_card_item);
            name = (TextView) view.findViewById(R.id.order_card_name);
            totalCoast = (TextView) view.findViewById(R.id.order_card_coast);
            lobsterPhoto = (ImageView) view.findViewById(R.id.picture_of_lobster);
        }
    }
}
