package com.example.buylist.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.buylist.R;
import com.example.buylist.model.Alimento;

public class FormActivity extends AppCompatActivity {

    private EditText formEditTitulo;
    private EditText formEditValor;
    private EditText formEditLocal;
    private Button formBtnSalvar;
    private Boolean eFormularioEdicao = false;
    private Alimento alimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        carregaCampos();
        cliqueBotao();

        Intent intent = getIntent();
        if (intent.hasExtra(Constantes.CHAVE_EDICAO_ALIMENTO)) {
            eFormularioEdicao = true;
            alimento = (Alimento) intent.getSerializableExtra(Constantes.CHAVE_EDICAO_ALIMENTO);
            carregaDadosFormulario();
        }

    }

    private void carregaCampos() {
        formEditTitulo = findViewById(R.id.formTituloEditTxt);
        formEditLocal = findViewById(R.id.formLocalEditTxt);
        formEditValor = findViewById(R.id.formValorEditText);
        formBtnSalvar = findViewById(R.id.formSalvarButton);
    }

    private void carregaDadosFormulario() {
        formEditTitulo.setText(alimento.getTitulo());
        formEditValor.setText(alimento.getValor());
        formEditLocal.setText(alimento.getLocal());
    }

    private void cliqueBotao() {
        formBtnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eFormularioEdicao) {
                    atualizarAlimento();

                    Intent intent = new Intent(FormActivity.this, MainActivity.class);
                    intent.putExtra(Constantes.CHAVE_EDICAO_ALIMENTO, alimento);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else  {
                    alimento = pegaAlimentoFormulario();

                    Intent intent = new Intent(FormActivity.this, MainActivity.class);
                    intent.putExtra(Constantes.CHAVE_NOVO_ALIMENTO, alimento);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void atualizarAlimento() {
        String titulo = formEditTitulo.getText().toString();
        String local = formEditLocal.getText().toString();
        String valor = formEditValor.getText().toString();

        alimento.setTitulo(titulo);
        alimento.setLocal(local);
        alimento.setValor(valor);
    }

    private Alimento pegaAlimentoFormulario() {
        String titulo = formEditTitulo.getText().toString();
        String local = formEditLocal.getText().toString();
        String valor = formEditValor.getText().toString();

        return new Alimento(titulo, valor, local);
    }


}