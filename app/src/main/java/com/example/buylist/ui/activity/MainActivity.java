package com.example.buylist.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.buylist.R;
import com.example.buylist.database.AppDatabase;
import com.example.buylist.model.Alimento;
import com.example.buylist.ui.activity.recyclerview.AlimentosAdapter;
import com.example.buylist.ui.activity.recyclerview.adapter.listener.AlimentoItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mainAddFAB;
    private RecyclerView mainRecyclerView;
    private AlimentosAdapter adapter;

    static List<Alimento> alimentos;
    private int posicaoItemClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geraListaAlimentos();
        configuraRecyclerView();
        clickButton();
    }

    private void clickButton() {
        mainAddFAB = findViewById(R.id.mainAddFAB);
        mainAddFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivityForResult(intent, Constantes.SOLICITA_NOVO_ALIMENTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constantes.SOLICITA_NOVO_ALIMENTO && data.hasExtra(Constantes.CHAVE_NOVO_ALIMENTO)) {
            if (resultCode == Activity.RESULT_OK) {
                Alimento alimento = (Alimento) data.getSerializableExtra(Constantes.CHAVE_NOVO_ALIMENTO);

                AppDatabase.getInstance(getApplicationContext()).alimentoDao().insert(alimento);

                alimentos.clear();
                alimentos.addAll(AppDatabase.getInstance(getApplicationContext()).alimentoDao().getAll());
                adapter.notifyDataSetChanged();
            }
        }

        if (requestCode == Constantes.SOLICITA_EDICAO_ALIMENTO && data.hasExtra(Constantes.CHAVE_EDICAO_ALIMENTO)) {
            if(resultCode == Activity.RESULT_OK) {
                Alimento alimento = (Alimento) data.getSerializableExtra(Constantes.CHAVE_EDICAO_ALIMENTO);
                alimentos.set(posicaoItemClick, alimento);
                AppDatabase.getInstance(getApplicationContext()).alimentoDao().update(alimento);
                alimentos.set(posicaoItemClick, alimento);
                adapter.notifyItemChanged(posicaoItemClick);
            }
        }
    }

    private void configuraRecyclerView() {
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AlimentosAdapter(getApplicationContext(), alimentos);
        mainRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AlimentoItemClickListener() {
            @Override
            public void itemClick(Alimento alimento, int posicao) {
                posicaoItemClick = posicao;
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra(Constantes.CHAVE_EDICAO_ALIMENTO, alimento);
                startActivityForResult(intent, Constantes.SOLICITA_EDICAO_ALIMENTO);
            }

        });
    }

    private void geraListaAlimentos() {
        alimentos = AppDatabase.getInstance(getApplicationContext()).alimentoDao().getAll();
    }
}