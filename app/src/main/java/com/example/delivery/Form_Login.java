package com.example.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Form_Login extends AppCompatActivity {

    private TextView txt_criar_conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        IniciarComponentes();
        txt_criar_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Form_Login.this, Form_Cadastro.class);
                startActivity(intent);
            }
        });

    }

    public void IniciarComponentes() {
        txt_criar_conta = findViewById(R.id.txtCriarConta);
    }


}