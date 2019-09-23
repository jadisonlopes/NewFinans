package newproject.com.br.newfinans.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.objetc.ObjectGasto;
import newproject.com.br.newfinans.util.Preference;
import newproject.com.br.newfinans.util.SQLLite;
import newproject.com.br.newfinans.util.Util;

/**
 * Created by Jadison on 25/01/2018.
 */

public class GastoAdapter extends ArrayAdapter<ObjectGasto> {
    private final Context context;
    private final ArrayList<ObjectGasto> elementos;
    Preference pf;

    public GastoAdapter(Context context, ArrayList<ObjectGasto> elementos) {
        super(context, R.layout.row_gastos, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_gastos,parent, false);

        pf = new Preference(getContext().getSharedPreferences("Config", getContext().MODE_PRIVATE));

        TextView data = (TextView) rowView.findViewById(R.id.idRGData);
        TextView descricao = (TextView) rowView.findViewById(R.id.idRGDescricao);
        TextView valor = (TextView) rowView.findViewById(R.id.idRGValor);
        TextView formaPag = (TextView) rowView.findViewById(R.id.idRGDescricaoFormaPag);
        TextView fonteDespesa = (TextView) rowView.findViewById(R.id.idRGDescricaoFonteDespesa);
        TextView despesa = (TextView) rowView.findViewById(R.id.idRGDescricaoDespesa);
        LinearLayout complemento = (LinearLayout) rowView.findViewById(R.id.idRGLLComplemento);

        if (!elementos.get(position).getDatacartao().equals("")){
            data.setText(Util.DataToString(elementos.get(position).getDatacartao(), "dd/MM/yy"));
        }else {
            data.setText(Util.DataToString(elementos.get(position).getData(), "dd/MM/yy"));
        }
        descricao.setText(elementos.get(position).getDescricao());
        valor.setText(Util.SetMaskNumeric(elementos.get(position).getValor(),"R$"));
        if ( pf.preferences.getBoolean("detalhes",false) ) {
            complemento.setVisibility(View.VISIBLE);
            Cursor FormaPag = SQLLite.ConsultaFormaPag(elementos.get(position).getFormapag());
            formaPag.setText(FormaPag.getString(FormaPag.getColumnIndex("descricao")));

            Cursor Despesa = SQLLite.ConsultaDespesa(elementos.get(position).getDespesa());
            despesa.setText(Despesa.getString(Despesa.getColumnIndex("descricao")));

            Cursor FonteDespesa = SQLLite.ConsultaFonteDespesa(elementos.get(position).getFonte_despesa());
            fonteDespesa.setText(FonteDespesa.getString(FonteDespesa.getColumnIndex("descricao")));
        }

        return rowView;
    }
    private boolean isArry (int[] array, int mes){
        if ( array[mes] != 0 ){
            array[mes] = 0;
            return true;
        }
        else
            return false;
    }
}
