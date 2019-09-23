package newproject.com.br.newfinans.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.util.SQLLite;
import newproject.com.br.newfinans.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class CDespesaFragment extends Fragment {

    public static CDespesaFragment CDespesa;
    private Context context;
    private ImageView ImageGravar;
    private EditText EditDescricao;
    private EditText EditObs;
    private String SQL;
    private boolean update;
    private Integer codigo;

    public CDespesaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        CDespesa = this;
        View v = inflater.inflate(R.layout.fragment_cdespesa, container, false);

        ImageGravar      = v.findViewById(R.id.idImageGravar);
        EditDescricao    = v.findViewById(R.id.idEditDDescricao);
        EditObs          = v.findViewById(R.id.idEditDObs);

        ImageGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gravar();
            }
        });

        return v;
    }

    private void Gravar(){
        if ( ValidaCampos() ) {
            if ( update ){
                SQL = "UPDATE despesa SET descricao=" + SQLLite.SQLVarchar(EditDescricao.getText().toString()) +
                        " ,observ=" + SQLLite.SQLVarchar(EditObs.getText().toString()) +
                        " WHERE cod=" + SQLLite.SQLInteger(codigo) + ";";
            }else {
                SQL = "INSERT INTO despesa (descricao,observ,exclusao) VALUES (" +
                        SQLLite.SQLVarchar(EditDescricao.getText().toString()) + "," +
                        SQLLite.SQLVarchar(EditObs.getText().toString()) + "," +
                        SQLLite.SQLBoolean(false) + ");";
            }
            SQLLite.dataBase.execSQL(SQL);
            clearFields();
            Toast.makeText(context, "Gravado Com Sucesso!", Toast.LENGTH_LONG).show();
        }
    }
    private Boolean ValidaCampos(){
        if ( Util.Trim(EditDescricao.getText().toString()).equals("") ){
            Toast.makeText(context,"Informe a Descrição",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearFields(){
        this.update = false;
        this.codigo = null;
        EditDescricao.setText(null);
        EditObs.setText(null);
    }

    public void getPesquisa(Integer codigo) {
        if ( codigo>0 ){
            Cursor CursorDespesa = SQLLite.ConsultaDespesa(codigo);
            CursorDespesa.moveToFirst();
            if ( CursorDespesa.getCount()>0 ) {
                this.update = true;
                this.codigo = codigo;
                EditDescricao.setText(CursorDespesa.getString(CursorDespesa.getColumnIndex("descricao")));
                EditObs.setText(CursorDespesa.getString(CursorDespesa.getColumnIndex("observ")));
            }
        }else {
            clearFields();
        }
    }
}
