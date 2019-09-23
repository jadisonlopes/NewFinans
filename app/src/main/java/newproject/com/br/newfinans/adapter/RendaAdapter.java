package newproject.com.br.newfinans.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.objetc.ObjectRenda;
import newproject.com.br.newfinans.util.Util;

/**
 * Created by Jadison on 24/01/2018.
 */

public class RendaAdapter extends ArrayAdapter<ObjectRenda> {

    private final Context context;
    private final ArrayList<ObjectRenda> elementos;

    public RendaAdapter(Context context, ArrayList<ObjectRenda> elementos) {
        super(context, R.layout.row_renda, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_renda,parent, false);

        TextView data = (TextView) rowView.findViewById(R.id.idRRData);
        TextView descricao = (TextView) rowView.findViewById(R.id.idRRDescricao);
        TextView valor = (TextView) rowView.findViewById(R.id.idRRValor);

        data.setText(elementos.get(position).getData());
        descricao.setText(elementos.get(position).getDescricao());
        valor.setText(Util.SetMaskNumeric(elementos.get(position).getValor(),"R$"));

        return rowView;
    }
}
