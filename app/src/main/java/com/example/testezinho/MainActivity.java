package com.example.testezinho;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Parte que executa todos os comandos ao instanciar a tela
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView titulo = findViewById(R.id.textView);
        Button botao1 = findViewById(R.id.Botao1);
        Button botao2 = findViewById(R.id.Botao2);

        botao1.setOnClickListener(view -> {
            titulo.setText("Botão 1!");
        });

        botao2.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "Botão 2 foi pressionado!", Toast.LENGTH_SHORT).show();
        });
    }
}