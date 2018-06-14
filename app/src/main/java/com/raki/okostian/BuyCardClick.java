package com.raki.okostian;

import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class BuyCardClick implements View.OnClickListener {
    public static BuyCards buyCardClass;

    private static List<BuyCardClick> listCards = new ArrayList<>();
    private RecyclerViewAdapter.LobsterViewHolder lobHold;
    private View parentView;


    static List<BuyCardClick> getListCards() {
        return listCards;
    }

    RecyclerViewAdapter.LobsterViewHolder getLobHold(){
        return lobHold;
    }

    public BuyCardClick(RecyclerViewAdapter.LobsterViewHolder lobHold, View v) {
        this.lobHold = lobHold;
        parentView = v;
        listCards.add(this);
    }

    @Override
    public void onClick(View v) {
        if (!lobHold.isSelected()) {
            //unselect another
            for (BuyCardClick b : getListCards()) {
                if (b.lobHold.isSelected()) {
                    b.unSelectItem(b.lobHold.cardView);
                }
            }
            selectItem(v);
        } else {
            unSelectItem(v);
        }
        buyCardClass.showCoastOfItem();
    }

    private LinearLayout weightLobster;
    private TextView textView;

    private void selectItem(View v) {
        lobHold.setSelected(true);
        lobHold.cardView.setCardBackgroundColor(v.getResources().getColor(R.color.colorItemClicked));

        weightLobster = (LinearLayout) parentView.findViewById(R.id.buy_cards_weights);
        weightLobster.setVisibility(View.VISIBLE);

        textView = (TextView) parentView.findViewById(R.id.buy_cards_name_of_selected);
        textView.setText(lobHold.name.getText());
    }

    private void unSelectItem(View v) {
        lobHold.setSelected(false);
        lobHold.cardView.setCardBackgroundColor(getColorBackground(v));

        weightLobster.setVisibility(View.INVISIBLE);
    }

    private static int getColorBackground (View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return
                    v.getResources().getColor(R.color.colorItemBuyCard, v.getContext().getTheme());
        } else {
            return
                    v.getResources().getColor(R.color.colorItemBuyCard) ;      //?
        }
    }
}
