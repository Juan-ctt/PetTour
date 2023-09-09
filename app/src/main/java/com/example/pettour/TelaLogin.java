package com.example.pettour;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pettour.databinding.ActivityTelaLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TelaLogin extends AppCompatActivity {

    private ActivityTelaLoginBinding binding;
    private FirebaseAuth mAuth;
    private SharedPreferences preferences;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        binding.botaoEntrar.setOnClickListener(view -> validarDados());

        binding.textCadastro.setOnClickListener(view -> {
            startActivity(new Intent(this, tela_seletor_cadastro.class));
        });

        Switch switchStayLoggedIn = binding.switchManterconectado;
        switchStayLoggedIn.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            // Usar a variável preferences definida fora do onCreate()
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("stayLoggedIn", isChecked);
            editor.apply();
        });

        // Inicializar a variável preferences aqui
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Verificar a preferência do usuário de permanecer logado
        boolean stayLoggedIn = preferences.getBoolean("stayLoggedIn", false);
        if (stayLoggedIn) {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }

    }


    private void validarDados(){
        String email = binding.editTextUsuario.getText().toString().trim();
        String senha = binding.editTextSenha.getText().toString().trim();

        if (!email.isEmpty()){
            if (!senha.isEmpty()){
                exibirProgressBar();  // Exibir a ProgressBar enquanto realiza o login
                loginUsuario(email, senha);
            }else {
                Toast.makeText(this, "Informe a sua senha!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Informe o seu e-mail!", Toast.LENGTH_SHORT).show();
        }
    }
    private void exibirProgressBar() {
        progressBar.setVisibility(View.VISIBLE);  // Torna a ProgressBar visível
    }

    private void esconderProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);  // Torna a ProgressBar invisível
    }

    private void loginUsuario(String email, String senha){
        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            esconderProgressBar();  // Esconde a ProgressBar quando o login for concluído
            if (task.isSuccessful()){
                String userEmail = mAuth.getCurrentUser().getEmail();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("userEmail", userEmail); // Passa o email como um extra
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "Usuário ou Senha inválidos!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}