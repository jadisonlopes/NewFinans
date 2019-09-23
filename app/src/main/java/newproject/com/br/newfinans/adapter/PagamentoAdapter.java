package newproject.com.br.newfinans.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.fragment.CGastoFragment;
import newproject.com.br.newfinans.objetc.ObjectPagamento;
import newproject.com.br.newfinans.util.Util;

public class PagamentoAdapter extends ArrayAdapter<ObjectPagamento> {
    private final Context context;
    private final ArrayList<ObjectPagamento> elementos;

    public PagamentoAdapter(Context context, ArrayList<ObjectPagamento> elementos) {
        super(context, R.layout.row_pagamentos, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_pagamentos,parent, false);

        TextView depesa = (TextView) rowView.findViewById(R.id.idPTextDespesa);
        TextView fonteDepesa = (TextView) rowView.findViewById(R.id.idPTextFonteDespesa);
        TextView valor = (TextView) rowView.findViewById(R.id.idPTextValor);
        Button Excluir = (Button) rowView.findViewById(R.id.idPButtonExcluir);

        depesa.setText(elementos.get(position).getDespesa());
        fonteDepesa.setText(elementos.get(position).getFonteDespesa());
        valor.setText(Util.SetMaskNumeric(elementos.get(position).getValor(),"R$"));
        Excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elementos.remove(position);
                notifyDataSetChanged();
                CGastoFragment.PreencheListPagamento();
            }
        });
        return rowView;
    }
}
