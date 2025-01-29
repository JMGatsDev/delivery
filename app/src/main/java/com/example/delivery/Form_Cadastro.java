package com.example.delivery;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Form_Cadastro extends AppCompatActivity {
    private CircleImageView fotoUsuario;
    private Button btSelecionarFoto, btCadastrar;
    private EditText cadastroNome, cadastroEmail, cadastroSenha;
    private TextView cadastroMsgError;

    public void IniciarComponentes() {
        fotoUsuario = findViewById(R.id.fotoUsuario);
        btSelecionarFoto = findViewById(R.id.selecionarFoto);
        btCadastrar = findViewById(R.id.btCadastrar);
        cadastroNome = findViewById(R.id.cadastroNome);
        cadastroEmail = findViewById(R.id.cadastroEmail);
        cadastroSenha = findViewById(R.id.cadastroSenha);
        cadastroMsgError = findViewById(R.id.cadastroMsgError);
        cadastroNome.addTextChangedListener(cadastroTexteWatcher);
        cadastroEmail.addTextChangedListener(cadastroTexteWatcher);
        cadastroSenha.addTextChangedListener(cadastroTexteWatcher);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);


        IniciarComponentes();
        cadastroNome.addTextChangedListener(cadastroTexteWatcher);
        cadastroEmail.addTextChangedListener(cadastroTexteWatcher);
        cadastroSenha.addTextChangedListener(cadastroTexteWatcher);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario(v);
            }
        });
    }

    private void cadastrarUsuario(View view) {
        String email = cadastroEmail.getText().toString();
        String senha = cadastroSenha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Snackbar snackbar = Snackbar.make(view, "Cadastro realizado com sucesso", Snackbar.LENGTH_INDEFINITE).setAction("ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });

                    snackbar.show();

                } else {
                    String erro;
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Coloque uma senha com no minimo de 6 caracteres";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "E-mail inválido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "E-mail ja cadastrado";
                    } catch (FirebaseNetworkException e) {
                        erro = "Sem acesso a internet";

                    } catch (Exception e) {
                        erro = "Erro ao cadastrar usuário";
                    }
                    cadastroMsgError.setText(erro);
                }

            }
        });
    }

    TextWatcher cadastroTexteWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //ler os campos dos forms
            String textoNome = cadastroNome.getText().toString();
            String textoEmail = cadastroEmail.getText().toString();
            String textoSenha = cadastroSenha.getText().toString();

            boolean camposPreenchidos = !textoNome.isEmpty() && !textoEmail.isEmpty() && !textoSenha.isEmpty();//como se fosse um if
            btCadastrar.setEnabled(camposPreenchidos);
            btCadastrar.setBackgroundColor(camposPreenchidos ? getResources().getColor(R.color.dark_red) : getResources().getColor(R.color.gray));


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}