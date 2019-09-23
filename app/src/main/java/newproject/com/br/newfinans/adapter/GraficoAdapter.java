package newproject.com.br.newfinans.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.objetc.ObjectGrafico;
import newproject.com.br.newfinans.util.Util;

/**
 * Created by Jadison on 17/02/2018.
 */

public class GraficoAdapter extends ArrayAdapter<ObjectGrafico> {

    private final Context context;
    private final ArrayList<ObjectGrafico> elementos;

    public GraficoAdapter(Context context, ArrayList<ObjectGrafico> elementos) {
        super(context, R.layout.row_grafico, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_grafico,parent, false);

        TextView descricao = (TextView) rowView.findViewById(R.id.idTextGrafico1);
        TextView valor = (TextView) rowView.findViewById(R.id.idTextGrafico2);

        descricao.setText(elementos.get(position).getDescricao());
        valor.setText(Util.SetMaskNumeric(elementos.get(position).getValor(),"R$"));

        return rowView;
    }
}
