package com.example.pettour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class tela_seletor_cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_seletor_cadastro);

        View btn_tutor = findViewById(R.id.btn_cad_tutor);
        View btn_passeador = findViewById(R.id.btn_cad_passeador);

        btn_tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tela_seletor_cadastro.this, TelaCadastro.class);
                startActivity(intent);
            }
        });
        btn_passeador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tela_seletor_cadastro.this, TelaCadastro.class);
                startActivity(intent);
            }
        });
    }
}