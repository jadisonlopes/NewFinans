package newproject.com.br.newfinans.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.util.SQLLite;
import newproject.com.br.newfinans.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class CFormasPagFragment extends Fragment {

    public static CFormasPagFragment CFormasPag;
    private Context context;
    private EditText EditFDescricao;
    private EditText EditDataComp;
    private TextView TextDataComp;
    private EditText EditDataVenc;
    private TextView TextDataVenc;
    private CheckBox CbParcela;
    private RadioButton RbAvista;
    private RadioButton RbCartao;
    private RadioButton RbCheque;
    private RadioButton RbOutros;
    private ImageView ImageTipoPag;
    private ImageView ImageGravar;
    private RadioGroup RgUm;
    private boolean update;
    private Integer codigo;

    private String doc;
    private String SQL;

    public CFormasPagFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        CFormasPag = this;
        View v = inflater.inflate(R.layout.fragment_cformas_pag, container, false);

        EditFDescricao  = v.findViewById(R.id.idEditFDescricao);
        EditDataComp    = v.findViewById(R.id.idEditDataComp);
        TextDataComp    = v.findViewById(R.id.idTextDataComp);
        EditDataVenc    = v.findViewById(R.id.idEditDataVenc);
        TextDataVenc    = v.findViewById(R.id.idTextDataVenc);
        ImageTipoPag    = v.findViewById(R.id.idImageTipoPag);
        ImageGravar     = v.findViewById(R.id.idImageGravar);
        CbParcela       = v.findViewById(R.id.idCbParcela);
        RbAvista        = v.findViewById(R.id.idRbAvista);
        RbCartao        = v.findViewById(R.id.idRbCartao);
        RbCheque        = v.findViewById(R.id.idRbCheque);
        RbOutros        = v.findViewById(R.id.idRbOutros);
        RgUm            = v.findViewById(R.id.idRgUm);

        RgUm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //Ocultar Ojeto
                TextDataComp.setVisibility(View.GONE);
                EditDataComp.setVisibility(View.GONE);
                TextDataVenc.setVisibility(View.GONE);
                EditDataVenc.setVisibility(View.GONE);
                CbParcela.setChecked(false);
                doc = getDocumento(i);
            }
        });

        ImageGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gravar();
            }
        });

        return v;
    }

    private void Gravar() {
        if (ValidaCampos()) {
            Integer DiaComp = 0;
            Integer Venc = 0;
            if (!Util.Trim(EditDataComp.getText().toString()).equals("")) {
                DiaComp = DiaComp.parseInt(EditDataComp.getText().toString());
            }
            if (!Util.Trim(EditDataVenc.getText().toString()).equals("")) {
                Venc = DiaComp.parseInt(EditDataVenc.getText().toString());
            }
            if ( update ){
                SQL = "UPDATE formapag SET descricao="+ SQLLite.SQLVarchar(EditFDescricao.getText().toString()) +
                        " ,parcela="+ SQLLite.SQLBoolean(CbParcela.isChecked()) +
                        " ,tipo="+ SQLLite.SQLVarchar(doc) +
                        " ,diacomp="+ SQLLite.SQLInteger(DiaComp) +
                        " ,venc="+ SQLLite.SQLInteger(Venc) +
                        " WHERE cod=" + SQLLite.SQLInteger(codigo) + ";";
            }else {
                SQL = "INSERT INTO formapag (descricao,parcela,tipo,diacomp,venc,exclusao) VALUES (" +
                        SQLLite.SQLVarchar(EditFDescricao.getText().toString()) + "," + SQLLite.SQLBoolean(CbParcela.isChecked()) + "," +
                        SQLLite.SQLVarchar(doc) + "," + SQLLite.SQLInteger(DiaComp) + "," + SQLLite.SQLInteger(Venc) + "," + SQLLite.SQLBoolean(false) + ");";
            }
            SQLLite.dataBase.execSQL(SQL);
            clearFields();
            Toast.makeText(context, "Gravado Com Sucesso!", Toast.LENGTH_LONG).show();
        }
    }

    private Boolean ValidaCampos() {
        if (Util.Trim(EditFDescricao.getText().toString()).equals("")) {
            Toast.makeText(context, "Informe a Descrição", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (doc == "CC" && Util.Trim(EditDataComp.getText().toString()).equals("")) {
            Toast.makeText(context, "Informe o Melhor dia para Compra", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (doc == "CC" && Util.Trim(EditDataVenc.getText().toString()).equals("")) {
            Toast.makeText(context, "Informe o Vencimento", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        this.update = false;
        this.codigo = null;
        EditFDescricao.setText(null);
        EditDataComp.setText(null);
        EditDataVenc.setText(null);
        RbAvista.setChecked(true);
        RbCartao.setChecked(false);
        RbCheque.setChecked(false);
        RbOutros.setChecked(false);
        CbParcela.setChecked(false);
    }

    private String getDocumento(Integer positon) {
        switch (positon) {
            case R.id.idRbAvista:
                //Setando Imagem Dinamicamente.
                ImageTipoPag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dinheiro));
                return "AV";
            case R.id.idRbCartao:
                ImageTipoPag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cartao));
                TextDataComp.setVisibility(View.VISIBLE);
                EditDataComp.setVisibility(View.VISIBLE);
                TextDataVenc.setVisibility(View.VISIBLE);
                EditDataVenc.setVisibility(View.VISIBLE);
                CbParcela.setChecked(true);
                return "CC";
            case R.id.idRbCheque:
                ImageTipoPag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cheque));
                return "CH";
            case R.id.idRbOutros:
                ImageTipoPag.setImageDrawable(null);
                return "OU";
        }
        return null;
    }

    private void setDocumento(String documento) {
        switch (documento) {
            case "AV":
                //Setando Imagem Dinamicamente.
                RbAvista.setChecked(true);
                ImageTipoPag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dinheiro));
                break;
            case "CC":
                RbCartao.setChecked(true);
                ImageTipoPag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cartao));
                TextDataComp.setVisibility(View.VISIBLE);
                EditDataComp.setVisibility(View.VISIBLE);
                TextDataVenc.setVisibility(View.VISIBLE);
                EditDataVenc.setVisibility(View.VISIBLE);
                CbParcela.setChecked(true);
                break;
            case "CH":
                RbCheque.setChecked(true);
                ImageTipoPag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cheque));
                break;
            case "OU":
                RbOutros.setChecked(true);
                ImageTipoPag.setImageDrawable(null);
                break;
        }
    }

    public void getPesquisa(Integer codigo) {
        if (codigo > 0) {
            Cursor CursorFormasPag = SQLLite.ConsultaFormaPag(codigo);
            CursorFormasPag.moveToFirst();
            if (CursorFormasPag.getCount() > 0) {
                this.update = true;
                this.codigo = codigo;
                EditFDescricao.setText(CursorFormasPag.getString(CursorFormasPag.getColumnIndex("descricao")));
                EditDataComp.setText(CursorFormasPag.getString(CursorFormasPag.getColumnIndex("diacomp")));
                EditDataVenc.setText(CursorFormasPag.getString(CursorFormasPag.getColumnIndex("venc")));
                setDocumento(CursorFormasPag.getString(CursorFormasPag.getColumnIndex("tipo")));
            }
        } else {
            clearFields();
            setDocumento("AV");
        }
    }
}
