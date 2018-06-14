package com.raki.okostian;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        lobsters.add(new Lobster(getResources().getString(R.string.order_value_name_1),
                v.getResources().getInteger(R.integer.buy_gradation_price_1), R.drawable.green_1));
        lobsters.add(new Lobster(getResources().getString(R.string.order_value_name_2),
                v.getResources().getInteger(R.integer.buy_gradation_price_2), R.drawable.green_1));
        lobsters.add(new Lobster(getResources().getString(R.string.order_value_name_3),
                v.getResources().getInteger(R.integer.buy_gradation_price_3), R.drawable.green_1));
        lobsters.add(new Lobster(getResources().getString(R.string.order_value_name_4),
                v.getResources().getInteger(R.integer.buy_gradation_price_4), R.drawable.green_1));
    }


    private float totalWeights = 1;

    private static List<Order> orderArray = new ArrayList<>();
    private TextView nameOfOrder;

    //private LinearLayout cardsWeights;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.content_buy_cards, container, false);
        initialisationList(view);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        GridLayoutManager manager = new GridLayoutManager(this.getContext(), 2);
        rv.setLayoutManager(manager);

        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(lobsters);
        adapter.setParentView(view);
        rv.setAdapter(adapter);
        BuyCardClick.buyCardClass = this;

        final RelativeLayout butPlus = (RelativeLayout) view.findViewById(R.id.buy_cards_plus);
        butPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalWeights += 0.5;
                showWeight();
                showCoastOfItem();
            }
        });

        final RelativeLayout butMinus = (RelativeLayout) view.findViewById(R.id.buy_cards_minus);
        butMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalWeights > 1 ) {
                    totalWeights -= 0.5;
                }
                showWeight();
                showCoastOfItem();
            }
        });

        //показ начального веса для заказа
        showTotalWeight = (TextView) view.findViewById(R.id.buy_cards_total_weights);
        showWeight();
        showCoastOfOrders = (TextView) view.findViewById(R.id.buy_text_cast_all);
        //инициализация всплывающего слоя для подсдета веса
        //cardsWeights = (LinearLayout) view.findViewById(R.id.buy_cards_weights);

        //инициализация полей для показа
        nameOfOrder = (TextView) view.findViewById(R.id.buy_cards_name_of_selected);
        showCoast = (TextView) view.findViewById(R.id.buy_text_cost_of_order);

        //кнопка добавления пункта заказа
        final RelativeLayout butAddOrder = (RelativeLayout) view.findViewById(R.id.buy_button_add_to_order);

        butAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = (String) nameOfOrder.getText();
                Lobster lobster = null;
                int i = 0;
                for (Lobster l: lobsters) {
                    if(l.getName().equals(name)){
                        lobster = l;
                        break;
                    } else {
                        i++;
                    }
                }

                boolean flag = false;

                for (Order or: orderArray){
                    if (or.getLobster().getName().equals(name)) {
                        or.setWeights(totalWeights);
                        flag = true;
                    }
                }

                if (!flag) {
                    if (lobster != null)
                        orderArray.add(new Order(lobster, totalWeights));
                }

                refreshView(v, adapter, i);
                showMessage("Добавлено в корзину");
            }
        });

        //удаление пункта с заказа
        final RelativeLayout buttonDeleteOrder  = (RelativeLayout) view.findViewById(R.id.buy_button_delete_from_order);
        buttonDeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = (String) nameOfOrder.getText();

                if (orderArray.size() != 0) {
                    for (int i = 0; i < orderArray.size(); i++) {
                        if (orderArray.get(i).getLobster().getName().equals(name)) {
                            orderArray.remove(i);
                            break;
                        }
                    }
                }

                int j = 0;
                for (Lobster l: lobsters) {
                    if(l.getName().equals(name)){
                        break;
                    } else {
                        j++;
                    }
                }
                refreshView(v, adapter, j);
                showMessage("Удалено с корзины");
            }
        });

        //Оформление заказа
        final LinearLayout buttonConfirm = (LinearLayout) view.findViewById(R.id.buy_button_confirm_order);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderArray.size() == 0 ) {
                    showMessage("Сделайте заказ");
                    return;
                }

                Fragment fragment = new ConfirmOrder();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null); //   запоминается обратная работ
                fragmentTransaction.commit();
            }
        });



        return view;
    }

    private TextView showTotalWeight;

    private void showWeight(){
        showTotalWeight.setText(totalWeights + " кг.");
    }


    private TextView showCoast;
    private float totalCoast = 0;

    public void showCoastOfItem() {
        totalCoast = 0;
        int price = 0;
        for (Lobster l: lobsters) {
            if (l.getName().equals(nameOfOrder.getText())){
                price = l.getPrice();
                break;
            }
        }
        totalCoast += price * totalWeights;
        showCoast.setText(Math.round(totalCoast) +  " " + getResources().getString(R.string.currency));
    }

    private TextView showCoastOfOrders;
    private void showCoastOfOrders(View v) {
        totalCoast = 0;
        for (Order or: orderArray) {
            int price = 0;
            for (Lobster l: lobsters) {
                if (l.getName().equals(or.getLobster().getName())){
                    price = l.getPrice();
                    break;
                }
            }
            totalCoast += or.getWeights()* price;
        }

        showCoastOfOrders.setText(getResources().getString(R.string.order_name_basket)
                + " " + Math.round(totalCoast)
                + " " + getResources().getString(R.string.currency));
    }


    private void showMessage(String message) {
        Toast.makeText(this.getActivity(),
                message
                ,Toast.LENGTH_SHORT).show();
    }

    public static List<Order> getOrderArray() {
        return orderArray;
    }


    private void refreshView(View v, RecyclerViewAdapter adapter, int i) {
        nameOfOrder.setText("Выберите позицию");
        showCoastOfOrders(v);
        totalWeights = 1;
        showWeight();
        showCoastOfItem();

        //убираем следы выбора
        for (BuyCardClick item: BuyCardClick.getListCards()) {
            if (item.getLobHold().isSelected()) {
                //item.unSelectItem(v);
                item.onClick(v);
            }
        }

        //карточка показа веса заказаного рака
        RecyclerViewAdapter.setOrderList(orderArray);
        adapter.notifyItemChanged(i);
    }
}


