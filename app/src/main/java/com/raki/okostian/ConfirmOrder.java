package com.raki.okostian;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ConfirmOrder extends Fragment {
    private List<Order> orderArray;

    private boolean is_fast_delivery;
    private String address_for_delivery;
    private EditText editText_address_for_delivery;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View view = inflater.inflate(R.layout.confirm_order, container, false);
        orderArray = BuyCards.getOrderArray();

        // наполняем список заказов
        RecyclerView rvOrderList = (RecyclerView) view.findViewById(R.id.rv_orderList);
        GridLayoutManager manager = new GridLayoutManager(this.getContext(), 1);
        rvOrderList.setLayoutManager(manager);

        final OrderRecyclerViewAdapter adapter = new OrderRecyclerViewAdapter(orderArray);
        rvOrderList.setAdapter(adapter);

        //адрес доставки
        editText_address_for_delivery = (EditText) view.findViewById(R.id.buy_editText_address);
        editText_address_for_delivery.setOnKeyListener(
                new View.OnKeyListener() {
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if(event.getAction() == KeyEvent.ACTION_DOWN &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            // сохраняем текст, введенный до нажатия Enter в переменную
                            address_for_delivery = editText_address_for_delivery.getText().toString();
                            return true;
                        }
                        return false;
                    }
                }
        );

        //Флажок срочной доставки
        final CheckBox checkbox_fast_delivery = view.findViewById(R.id.confirm_checkBox_fast_delivery);
        checkbox_fast_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) view.findViewById(R.id.confirm_textView_inputForAddress);

                if(checkbox_fast_delivery.isChecked()) {
                    is_fast_delivery = true;
                    textView.setText(R.string.buy_textView_address);
                } else {
                    is_fast_delivery = false;
                    textView.setText(R.string.buy_textView_addressAndFastDelivery);
                }
            }
        });

        final LinearLayout buttonConfirm = (LinearLayout) view.findViewById(R.id.confirm_order_sendSMS);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              sendWithSMS(makeMessage());

            }
        });

        return view;
    }

    private String makeMessage() {
        address_for_delivery = editText_address_for_delivery.getText().toString();

        StringBuilder mes = new StringBuilder();
        for (Order or : orderArray) {
            mes.append(or.getLobster().getName());
            mes.append("-");
            mes.append(or.getWeights());
            mes.append(";");
        }
        if (is_fast_delivery) {
            mes.append("Fast;");
        } else {
            mes.append("NoFast;");
        }
        mes.append(address_for_delivery);
        return mes.toString();
    }

    private void sendWithSMS(String message){
        showMessage(message);
        /*try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(
                    getResources().getString(R.string.telephone_number), null, message, null, null);
            showMessage("Сообщение успешно отправлено");
        } catch (Exception ex) {
            showMessage("Отправка не удалась, выберите другой метод. " + ex.getMessage().toString());
            ex.printStackTrace();
        }*/
    }

    private void showMessage(String message) {
        Toast.makeText(super.getActivity(),
                message
                ,Toast.LENGTH_SHORT).show();
    }
}
