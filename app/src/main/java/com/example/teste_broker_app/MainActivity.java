package com.example.teste_broker_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {

    private MqttHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startMqtt();

        Button botao1 = findViewById(R.id.Botao1);
        Button botao2 = findViewById(R.id.Botao2);

        botao1.setOnClickListener(view -> {
            mqttHelper.subscribeToTopic("MeuNovoApp/"+mqttHelper.getClientId()+"/MainActivity/#");
            mqttHelper.publish("Botao 1 foi pressionado!","MeuNovoApp/"+mqttHelper.getClientId()+"/MainActivity");
        });

        botao2.setOnClickListener(view -> {
            try {
                mqttHelper.getMqttAndroidClient().unsubscribe("MeuNovoApp/"+mqttHelper.getClientId()+"/MainActivity/#");
            }catch (Exception e){
                Toast.makeText(MainActivity.this, "Exception: "+e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startMqtt() {
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
            }
            @Override
            public void connectionLost(Throwable throwable) {
                //Aparece essa mensagem sempre que a conexão for perdida
                //Toast.makeText(getApplicationContext(), "Conexão perdida", Toast.LENGTH_SHORT).show();
            }
            @Override
            // messageArrived é uma função que é chamada toda vez que o cliente MQTT recebe uma mensagem
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", mqttMessage.toString());
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
    }

}