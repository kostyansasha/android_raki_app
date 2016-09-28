package raki.com.raki_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * @author Kostian Sasha
 * @version %I%, %G%
 */
public class Buy extends Fragment {

    private static final String NAME_OF_EMAIL_MESSAGE_EMAIL = "email";
    private static final String NAME_OF_EMAIL_MESSAGE_SMS   = "sms";

    private boolean is_fast_delivery;
    private String lobster_weight;
    private String total_weight;
    private String address_for_delivery;
    private String method_of_send = "";
    private String telephone_number;

    private EditText editText_total_weight;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        final View view = inflater.inflate(R.layout.content_buy, container, false);

        // Выбор градации
        final RadioGroup radiogroup_gradation = (RadioGroup) view.findViewById(R.id.radioGroup_weight);
        radiogroup_gradation.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        lobster_weight = ((RadioButton) view.findViewById(group.getCheckedRadioButtonId())).getText().toString();
                        calculate(view);
                    }
                });

        // Общий вес заказа
        editText_total_weight = (EditText) view.findViewById(R.id.buy_editText_total_weight);
        editText_total_weight.setOnKeyListener(
                new View.OnKeyListener() {
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if(event.getAction() == KeyEvent.ACTION_DOWN &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            // сохраняем текст, введенный до нажатия Enter в переменную
                            total_weight = editText_total_weight.getText().toString();
                            calculate(view);
                            return true;
                        }
                        return false;
                    }
                }
                //editText.getText().toString().equals("")
                // для фокуса
                /*  <activity
                        android:name=".CatsActivity"
                        android:label="@string/app_name"
                        android:windowSoftInputMode="adjustPan" >
                    </activity>
                */
        );

        //Флажок срочной доставки
        final CheckBox checkbox_fast_delivery = (CheckBox) view.findViewById(R.id.buy_checkBox_fast_delivery);
        checkbox_fast_delivery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView textView = (TextView) view.findViewById(R.id.buy_textView_inputForAddress);

                if(checkbox_fast_delivery.isChecked()) {
                    is_fast_delivery = true;
                    textView.setText(R.string.buy_textView_address);
                } else {
                    is_fast_delivery = false;
                    textView.setText(R.string.buy_textView_addressAndFastDelivery);
                }
            }
        });

        //адрес доставки
        final EditText editText_address_for_delivery = (EditText) view.findViewById(R.id.buy_editText_address);
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

        // ввод номера
        final EditText editText_telephone = (EditText) view.findViewById(R.id.buy_editText_inputTelephone);
        editText_telephone.setOnKeyListener(
                new View.OnKeyListener() {
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if(event.getAction() == KeyEvent.ACTION_DOWN &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            // сохраняем текст, введенный до нажатия Enter в переменную
                            telephone_number = editText_telephone.getText().toString();
                            return true;
                        }
                        return false;
                    }
                }
        );

        //метод отправки заявки
        final RadioGroup radiogroup_methodForSendMessage = (RadioGroup) view.findViewById(R.id.buy_radioGroup_method_for_buy);
        radiogroup_methodForSendMessage.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.buy_radio_use_email:
                                method_of_send = NAME_OF_EMAIL_MESSAGE_EMAIL;
                                editText_telephone.setVisibility(View.VISIBLE);
                                break;

                            case R.id.buy_radio_use_sms:
                                method_of_send = NAME_OF_EMAIL_MESSAGE_SMS;
                                editText_telephone.setVisibility(View.INVISIBLE);
                                break;
                            default:
                                method_of_send = "";
                                break;
                        }
                    }
                });

        //обработка заказа
        Button button = (Button) view.findViewById(R.id.buy_buttonBuy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total_weight         = editText_total_weight.getText().toString();
                address_for_delivery = editText_address_for_delivery.getText().toString();
                telephone_number     = editText_telephone.getText().toString();

                if (lobster_weight == null) {
                    showMessage("Пожалуйста, выберите градацию");
                } else if (total_weight == null || total_weight.trim().equals("")) {
                    showMessage("Пожалуйста, введите вес");
                } else if (address_for_delivery == null || address_for_delivery.trim().equals("")) {
                    showMessage("Пожалуйста,  введите адрес доставки");
                } else {
                    sendOrder(makeMessage(), method_of_send);
                }
            }
        });

        return view;
    }

    // метод для подсчета стоимости
    private void calculate(View view) {
        int cost = 0;
        if (lobster_weight == null) {
            return;
        }

        switch (lobster_weight){
            case "20г": cost = 20;
                break;
            case "30г": cost = 30;
                break;
            case "40г": cost = 40;
                break;
            case "60г": cost = 60;
                break;
        }

        total_weight = editText_total_weight.getText().toString();
        double tot;
        try {
            tot = Double.parseDouble(total_weight);
        } catch (Throwable e){
          tot = 0;
        }

        double sum = cost * tot;

        final TextView textView = (TextView) view.findViewById(R.id.buy_textView_coast);
        textView.setVisibility(View.VISIBLE);
        textView.setText("Актуальная стоимость на момент обновления приложения: " + sum);
    }

    private void sendOrder(String message, String method) {
        switch (method) {
            case NAME_OF_EMAIL_MESSAGE_EMAIL:
                sendWithInternet(message);
                break;
            case NAME_OF_EMAIL_MESSAGE_SMS:
                sendWithSMS(message);
                break;
            default:
                showMessage("Пожалуйста выберите вариант отправки");
                break;
        }
    }
    private void showMessage(String message) {
        Toast.makeText(Buy.super.getActivity(),
                message
                ,Toast.LENGTH_SHORT).show();
    }

    private String makeMessage() {
        if (is_fast_delivery) {
            return lobster_weight + " весом: " + total_weight + ", " + address_for_delivery + ", срочно.";
        } else {
            return lobster_weight + " весом: " + total_weight + ", " + address_for_delivery + ", не срочно.";
        }
    }

    private void sendWithInternet(String message){

        if (telephone_number != null) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"kostyansasha@ukr.net"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Заказ");
            i.putExtra(Intent.EXTRA_TEXT, message);
            try {
                startActivity(Intent.createChooser(i, "Выберите приложение для email"));
            } catch (android.content.ActivityNotFoundException ex) {
                showMessage("Нет установленных приложения для email.");
            }
        } else {
            showMessage("Введите телефонный номер");
        }
    }

    private void sendWithSMS(String message){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(
                    getResources().getString(R.string.telephone_number), null, message, null, null);
            showMessage("Сообщение успешно отправлено");
        } catch (Exception ex) {
            showMessage("Отправка не удалась, выберите другой метод. " + ex.getMessage().toString());
            ex.printStackTrace();
        }
    }
}
/*
public class ButtonDemoActivity extends Activity implements OnClickListener...

final Button button1 = (Button)findViewById(R.id.button1);
final Button button2 = (Button)findViewById(R.id.button2);
final Button button3 = (Button)findViewById(R.id.button3);

// устанавливаем один обработчик для всех кнопок
button1.setOnClickListener(this);
button2.setOnClickListener(this);
button3.setOnClickListener(this);

// анализируем, какая кнопка была нажата. Всего один метод для всех кнопок
@Override
public void onClick(View v){
    switch (v.getId()) {
	    case R.id.button1: editText.setText("Нажата кнопка Button1"); break;
		case R.id.button2: editText.setText("Нажата кнопка Button2"); break;
		case R.id.button3: editText.setText("Нажата кнопка Button3"); break;
    }
}
 */