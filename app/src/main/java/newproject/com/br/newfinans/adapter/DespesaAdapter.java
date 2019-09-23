package newproject.com.br.newfinans.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.objetc.ObjectDespesa;

/**
 * Created by Jadison on 24/01/2018.
 */

public class DespesaAdapter extends ArrayAdapter<ObjectDespesa> {

    private final Context context;
    private final ArrayList<ObjectDespesa> elementos;

    public DespesaAdapter(Context context, ArrayList<ObjectDespesa> elementos) {
        super(context, R.layout.row_despesa, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_despesa,parent, false);

        TextView descricao = (TextView) rowView.findViewById(R.id.idRDDescricao);
        TextView obs = (TextView) rowView.findViewById(R.id.idRDObs);

        descricao.setText(elementos.get(position).getDescricao());
        if (!elementos.get(position).getObs().equals("") && elementos.get(position).getObs()!=null) {
            obs.setVisibility(View.VISIBLE);
            obs.setText(elementos.get(position).getObs());
        }
        return rowView;
    }
}
