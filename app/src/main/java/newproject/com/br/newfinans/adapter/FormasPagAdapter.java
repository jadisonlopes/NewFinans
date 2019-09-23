package newproject.com.br.newfinans.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.objetc.ObjectFormasPag;

/**
 * Created by Jadison on 25/01/2018.
 */

public class FormasPagAdapter extends ArrayAdapter<ObjectFormasPag> {
    private final Context context;
    private final ArrayList<ObjectFormasPag> elementos;

    public FormasPagAdapter(Context context, ArrayList<ObjectFormasPag> elementos) {
        super(context, R.layout.row_formas_pag, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_formas_pag,parent, false);

        TextView descricao = (TextView) rowView.findViewById(R.id.idRFPDescricao);
        TextView diacompra = (TextView) rowView.findViewById(R.id.idRFPMelhorDia);
        TextView vencimento = (TextView) rowView.findViewById(R.id.idRFPVencimento);

        descricao.setText(elementos.get(position).getDescricao());
        if (elementos.get(position).getTipo().equals("CC")) {
            diacompra.setVisibility(View.VISIBLE);
            vencimento.setVisibility(View.VISIBLE);
            diacompra.setText("Melhor Dia: " + elementos.get(position).getDiacomp().toString());
            vencimento.setText("Vencimento: "+elementos.get(position).getVenc().toString());
        }
        return rowView;
    }
}
