package com.example.pettour;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Criar uma instância do Fragment
        Fragment_solicitacao Fragment_solicitacao = new Fragment_solicitacao();

        // Substituir o conteúdo do FrameLayout pelo Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, Fragment_solicitacao)
                .commit();
    }
}