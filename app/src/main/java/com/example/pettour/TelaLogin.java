package com.example.pettour;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pettour.databinding.ActivityTelaLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class TelaLogin extends AppCompatActivity {

    private ActivityTelaLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.botaoEntrar.setOnClickListener(view -> validarDados());

        binding.textCadastro.setOnClickListener(view -> {
            startActivity(new Intent(this, tela_seletor_cadastro.class));
        });
    }

    private void validarDados(){
        String email = binding.editTextUsuario.getText().toString().trim();
        String senha = binding.editTextSenha.getText().toString().trim();

        if (!email.isEmpty()){
            if (!senha.isEmpty()){
                loginUsuario(email, senha);
            }else {
                Toast.makeText(this, "Informe a sua senha!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Informe o seu e-mail!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUsuario(String email, String senha){
        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               finish();
               startActivity(new Intent(this, MainActivity.class));
           }else {
               Toast.makeText(this, "Usuário ou Senha inválidos!", Toast.LENGTH_SHORT).show();
           }
        });
    }
}