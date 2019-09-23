package newproject.com.br.newfinans.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.util.DatePickerFragment;
import newproject.com.br.newfinans.util.SQLLite;
import newproject.com.br.newfinans.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class CRendaFragment extends Fragment {

    private Context context;
    private EditText EditRDescricao;
    private EditText EditData;
    private EditText EditValor;
    private ImageView ImageGravar;
    private ImageView ImageCalendar;

    private String SQL;


    public CRendaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        //Criando um View para Passar os Objetos
        final View v = inflater.inflate(R.layout.fragment_crenda, container, false);

        EditRDescricao = (EditText) v.findViewById(R.id.idEditRDescricao);
        EditData       = (EditText) v.findViewById(R.id.idEditData);
        EditValor      = (EditText) v.findViewById(R.id.idEditValor);
        ImageGravar    = (ImageView) v.findViewById(R.id.idImageGravar);
        ImageCalendar    = (ImageView) v.findViewById(R.id.idRCalendar);


        Util.MaskDate(EditData);
        EditValor.addTextChangedListener(Util.Numeric(EditValor));
        EditData.setOnFocusChangeListener(Util.ValidaData(EditData,context));
        ImageGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gravar();
            }
        });

        ImageCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerFragment(EditData,Util.Hoje);
                dialogFragment.show(getFragmentManager(),"");
            }
        });

        return v;
    }

    private void Gravar(){
        if (ValidaCampos()){
            Double Valor = 0.00;
            String Teste = Util.TrimMask(EditValor.getText().toString(),"[R,$, ]");
            if (!Util.Trim(EditValor.getText().toString()).equals("")){
                Valor = Double.parseDouble(Teste);
            }
            SQL = "INSERT INTO renda (descricao,data,valor) VALUES ("+
                    SQLLite.SQLVarchar(EditRDescricao.getText().toString())+"," +SQLLite.SQLDate(EditData.getText().toString())+","+
                    SQLLite.SQLNueric(Valor)+");";
            SQLLite.dataBase.execSQL(SQL);
            EditRDescricao.setText(null);
            EditData.setText(null);
            EditValor.setText("0.00");
            Toast.makeText(context,"Gravado Com Sucesso!",Toast.LENGTH_LONG).show();
        }
    }
    private Boolean ValidaCampos(){
        if (Util.Trim(EditRDescricao.getText().toString()).equals("")){
            Toast.makeText(context,"Informe a Descrição",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Util.Trim(EditValor.getText().toString()).equals("")){
            Toast.makeText(context,"Informe o Valor",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (EditData.length()<10){
            Toast.makeText(context, "Data Inválida", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
