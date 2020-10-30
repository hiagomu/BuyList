package com.example.buylist.ui.activity.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buylist.R;
import com.example.buylist.database.AppDatabase;
import com.example.buylist.model.Alimento;
import com.example.buylist.ui.activity.recyclerview.adapter.listener.AlimentoItemClickListener;

import java.util.Collections;
import java.util.List;

public class AlimentosAdapter extends RecyclerView.Adapter<AlimentosAdapter.ViewHolder> {

    private List<Alimento> alimentos;
    private Context context;
    private AlimentoItemClickListener onItemClickListener;

    public AlimentosAdapter(Context context, List<Alimento> alimentos) {
        this.alimentos = alimentos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_alimentos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alimento alimento = alimentos.get(position);
        holder.vicula(alimento);
    }

    @Override
    public int getItemCount() {
        return alimentos.size();
    }

    public void removeItemCarro(int adapterPosition) {
        AppDatabase.getInstance(context).alimentoDao().delete(alimentos.get(adapterPosition));
        alimentos.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public void alteraPosicao(int posicaoInicial, int posicaoFinal) {
        Collections.swap(alimentos, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    public void setOnItemClickListener(AlimentoItemClickListener alimentoItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titulo;
        private TextView valor;
        private TextView local;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.itemTituloTxtView);
            valor = itemView.findViewById(R.id.itemValorTxtView);
            local = itemView.findViewById(R.id.itemLocalTxtView);
        }

        public void vicula(Alimento alimento) {
            titulo.setText(alimento.getTitulo());
            valor.setText(alimento.getValor());
            local.setText(alimento.getLocal());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicao = getAdapterPosition();
                    Alimento alimento = alimentos.get(getAdapterPosition());
                    onItemClickListener.itemClick(alimento, posicao);
                }
            });

        }
    }
}
