package com.example.teste_broker_app;

import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class Login extends AppCompatActivity {

    private MqttHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mqttHelper = new MqttHelper(this);

        startMqtt();

        EditText UserEditText = findViewById(R.id.user);
        EditText SenhaEditText = findViewById(R.id.senha);

        Button BotaoLogin = findViewById(R.id.loginbtn);

        BotaoLogin.setOnClickListener(view -> {
            String txt = "User: "+UserEditText.getText().toString()+" / Senha: "+SenhaEditText.getText().toString();
            Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
        });

        BotaoLogin.setOnClickListener(view -> {
            mqttHelper.publish(UserEditText.getText().toString()+"/"+SenhaEditText.getText().toString(),
                    "MeuNovoApp/"+mqttHelper.getClientId()+"/Login/Infos");
        });

    } //on create
    private void startMqtt() {
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
                if(topic.equals("MeuNovoApp/"+mqttHelper.getClientId()+"/Login/Infos")) {
                    try {
                        String[] strings = mqttMessage.toString().split("/");
                        if (strings[0].equals("admin") && strings[1].equals("admin")) {
                            Toast.makeText(Login.this, "Login aprovado!", Toast.LENGTH_SHORT).show();
                        } else if (strings[0].equals("admin") && !strings[1].equals("admin")) {
                            Toast.makeText(Login.this, "Senha incorreta!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "Credenciais inválidas!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Error e) {
                        Toast.makeText(Login.this, "Erro: " + e, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
    }
}