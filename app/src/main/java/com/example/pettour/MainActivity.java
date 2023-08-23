package com.example.pettour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //SET DE FRAGMENTE DENTRO DA ACTIVITY-------------------------------------------------------


        // Criar uma instância do Fragment
        Fragment_solicitacao Fragment_solicitacao = new Fragment_solicitacao();

        // Substituir o conteúdo do FrameLayout pelo Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, Fragment_solicitacao)
                .commit();


        //MENU LATERAL------------------------------------------------------------------------------
        drawerLayout = findViewById(R.id.drawerLayout);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //CONFIGURAÇÃO DOS BOTÕES DO MENU-----------------------------------------------------------

        // Inicialização do FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Configuração do NavigationView
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout) {
            // Realizar o logout do Firebase
            mAuth.signOut();

            // Iniciar a TelaLogin
            Intent loginIntent = new Intent(this, TelaLogin.class);
            startActivity(loginIntent);
            finish(); // Encerrar a MainActivity

            return true;
        }

        // Outros  cliques de itens do menu

        return false;
    }
}