package newproject.com.br.newfinans.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.adapter.PagamentoAdapter;
import newproject.com.br.newfinans.objetc.ObjectPagamento;
import newproject.com.br.newfinans.util.DatePickerFragment;
import newproject.com.br.newfinans.util.Preference;
import newproject.com.br.newfinans.util.SQLLite;
import newproject.com.br.newfinans.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class CGastoFragment extends Fragment {
    public static CGastoFragment CGasto;
    static Context context;
    private Spinner SpinnerDespesas;
    private Spinner SpinnerFonteDespesa;
    private Spinner SpinnerFormaPag;
    private EditText EditDescricao;
    private static EditText EditValor;
    private EditText EditData;
    private EditText EditNumParc;
    private TextView TextNumParc;
    private ImageView ImageGravar;
    private ImageView ImageCalendar;
    private Button ButtonInserir;
    private static ListView ListPagamento;
    static ArrayList<ObjectPagamento> Pagamento;
    private Preference pf;

    private String SQL;

    public CGastoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        View v = inflater.inflate(R.layout.fragment_cgasto, container, false);

        SpinnerDespesas     = (Spinner)  v.findViewById(R.id.idSpDespesas);
        SpinnerFonteDespesa = (Spinner)  v.findViewById(R.id.idSpFonteDespesa);
        SpinnerFormaPag     = (Spinner)  v.findViewById(R.id.idSpFormaPag);
        EditDescricao       = (EditText) v.findViewById(R.id.idEditGDescricao);
        EditValor           = (EditText) v.findViewById(R.id.idEditGValor);
        EditData            = (EditText) v.findViewById(R.id.idEditGData);
        EditNumParc         = (EditText) v.findViewById(R.id.idEditNumParc);
        TextNumParc         = (TextView) v.findViewById(R.id.idTextNumParc);
        ImageGravar         = (ImageView)v.findViewById(R.id.idImageGravar);
        ImageCalendar       = (ImageView)v.findViewById(R.id.idGCalendar);
        ButtonInserir       = (Button)   v.findViewById(R.id.idGBtInserir);
        ListPagamento       = (ListView) v.findViewById(R.id.idGListPagamento);

        Pagamento = new ArrayList<ObjectPagamento>();
        pf = new Preference(getContext().getSharedPreferences("Config", getContext().MODE_PRIVATE));

        PreencerSpinner();
        Util.MaskDate(EditData);
        EditData.setOnFocusChangeListener(Util.ValidaData(EditData,context));
        EditValor.addTextChangedListener(Util.Numeric(EditValor));

        SpinnerFormaPag.setOnItemSelectedListener(CapturaSelecao());

        ImageCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerFragment(EditData,Util.Hoje);
                dialogFragment.show(getFragmentManager(),"");
            }
        });

        if ( pf.preferences.getBoolean("multiplos_pagamentos",false) ) {
            ListPagamento.setVisibility(View.VISIBLE);
            ButtonInserir.setVisibility(View.VISIBLE);

            ButtonInserir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ValidaCamposInserir()) {
                        AddPagamento(Util.getNumeric(EditValor.getText().toString()),
                                SpinnerDespesas.getSelectedItem().toString(),
                                SpinnerFonteDespesa.getSelectedItem().toString());
                    }
                    PreencheListPagamento();
                }
            });
        }

        ImageGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gravar();
            }
        });

        return v;
    }

    private AdapterView.OnItemSelectedListener CapturaSelecao (){
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor CursorFormaPag = SQLLite.ConsultaFormasPagNome(SpinnerFormaPag.getSelectedItem().toString());
                if ( CursorFormaPag.getString(CursorFormaPag.getColumnIndex("tipo")).equals("CC") &&
                     CursorFormaPag.getString(CursorFormaPag.getColumnIndex("parcela")).equals("T") ){
                    EditNumParc.setVisibility(View.VISIBLE);
                    TextNumParc.setVisibility(View.VISIBLE);
                }else{
                    EditNumParc.setVisibility(View.GONE);
                    TextNumParc.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
    }

    private void PreencerSpinner(){
        List ListaFormaPag = new ArrayList();
        Cursor CursorFormaPag = SQLLite.ConsultaFormaPag(0);
        int IndexF = CursorFormaPag.getColumnIndex("descricao");
        CursorFormaPag.moveToFirst();
        while ( CursorFormaPag.getCount() > CursorFormaPag.getPosition() ){
            ListaFormaPag.add(CursorFormaPag.getString(IndexF));
            CursorFormaPag.moveToNext();
        }
        ArrayAdapter <List> AdapterFormaPag = new ArrayAdapter<List>(context,android.R.layout.simple_dropdown_item_1line,ListaFormaPag);
        AdapterFormaPag.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SpinnerFormaPag.setAdapter(AdapterFormaPag);

        List ListaDespesa = new ArrayList();
        Cursor CursorDespesa = SQLLite.ConsultaDespesa(0);
        int IndexD = CursorDespesa.getColumnIndex("descricao");
        CursorDespesa.moveToFirst();
        while ( CursorDespesa.getCount() > CursorDespesa.getPosition() ){
            ListaDespesa.add(CursorDespesa.getString(IndexD));
            CursorDespesa.moveToNext();
        }
        ArrayAdapter <List> AdapterDespesa = new ArrayAdapter<List>(context,android.R.layout.simple_dropdown_item_1line,ListaDespesa);
        AdapterDespesa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SpinnerDespesas.setAdapter(AdapterDespesa);

        List ListaFonteDespesa = new ArrayList();
        Cursor CursorFonteDespesa = SQLLite.ConsultaFonteDespesa(0);
        int IndexFD = CursorFonteDespesa.getColumnIndex("descricao");
        CursorFonteDespesa.moveToFirst();
        while ( CursorFonteDespesa.getCount() > CursorFonteDespesa.getPosition() ){
            ListaFonteDespesa.add(CursorFonteDespesa.getString(IndexFD));
            CursorFonteDespesa.moveToNext();
        }
        ArrayAdapter <List> AdapterFonteDespesa = new ArrayAdapter<List>(context,android.R.layout.simple_dropdown_item_1line,ListaFonteDespesa);
        AdapterFonteDespesa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SpinnerFonteDespesa.setAdapter(AdapterFonteDespesa);
    }

    private void Gravar(){
        if ( ValidaCampos() ) {
            Double Valor = 0.00;
            int count = 1;
            String Descricao;
            Integer i, p;
            Integer DiaComp;
            Integer QtdeParc = 1;

            Integer CodFormaPag;
            Integer CodDespesa;
            Integer CodFonteDespesa;

            Cursor CursorFormaPag;
            Cursor CursorDespesa;
            Cursor CursorFonteDespesa;
            if (ListPagamento != null && ListPagamento.getCount()>0 )
                count = ListPagamento.getCount();
            for (p = 0; p < count; p++) {
                if (pf.preferences.getBoolean("multiplos_pagamentos", false) && ListPagamento.getCount() > 0) {
                    Valor = Double.valueOf(Pagamento.get(p).getValor());
                    CursorDespesa = SQLLite.ConsultaDespesaNome(Pagamento.get(p).getDespesa());
                    CursorFonteDespesa = SQLLite.ConsultaFonteDespesaNome(Pagamento.get(p).getFonteDespesa());
                } else {
                    if (!Util.Trim(EditValor.getText().toString()).equals("")) {
                        Valor = Double.parseDouble(Util.TrimMask(EditValor.getText().toString(), "[R,$, ]"));
                    }
                    CursorDespesa = SQLLite.ConsultaDespesaNome(SpinnerDespesas.getSelectedItem().toString());
                    CursorFonteDespesa = SQLLite.ConsultaFonteDespesaNome(SpinnerFonteDespesa.getSelectedItem().toString());
                }
                CursorFormaPag = SQLLite.ConsultaFormasPagNome(SpinnerFormaPag.getSelectedItem().toString());

                CodFormaPag = CursorFormaPag.getInt(CursorFormaPag.getColumnIndex("cod"));
                CodDespesa = CursorDespesa.getInt(CursorDespesa.getColumnIndex("cod"));
                CodFonteDespesa = CursorFonteDespesa.getInt(CursorFonteDespesa.getColumnIndex("cod"));

                Date DataMove = Util.DataRetorno(EditData.getText().toString());
                Date DataInc = DataMove;

                Integer CodParc = SQLLite.ConsultaUltimoCod("gasto") + 1;

                if (!Util.Trim(EditNumParc.getText().toString()).equals("")) {
                    QtdeParc = Integer.valueOf(EditNumParc.getText().toString());
                }
                SQL = "";
                DiaComp = Util.AchaDiaComp(CursorFormaPag.getInt(CursorFormaPag.getColumnIndex("diacomp")), CursorFormaPag.getInt(CursorFormaPag.getColumnIndex("venc")));
                for (i = 1; i <= QtdeParc; i++) {
                    if (CursorFormaPag.getInt(CursorFormaPag.getColumnIndex("venc")) < DiaComp &&
                            DiaComp <= Util.DataDia(EditData.getText().toString()) &&
                            CursorFormaPag.getString(CursorFormaPag.getColumnIndex("tipo")).equals("CC")) {
                        DataInc = Util.DataMesInc(DataMove, (1 + i));
                    } else if (CursorFormaPag.getInt(CursorFormaPag.getColumnIndex("venc")) > DiaComp &&
                            DiaComp > Util.DataDia(EditData.getText().toString()) &&
                            CursorFormaPag.getString(CursorFormaPag.getColumnIndex("tipo")).equals("CC")) {
                        if (i > 1) {
                            DataInc = Util.DataMesInc(DataMove, (i - 1));
                        }
                    } else if (CursorFormaPag.getString(CursorFormaPag.getColumnIndex("parcela")).equals("T")) {
                        DataInc = Util.DataMesInc(DataMove, i);
                    } else {
                        i = QtdeParc;
                    }

                    if (QtdeParc > 1) {
                        Descricao = EditDescricao.getText().toString() + "  " + Util.PreencheZeros(i.toString(), 2) + "/" + Util.PreencheZeros(QtdeParc.toString(), 2);
                    } else {
                        Descricao = EditDescricao.getText().toString();
                        CodParc = null;
                    }

                    SQL = "INSERT INTO gasto (descricao,formapag,despesa,fonte_despesa,datacartao,codparc,data,valor) VALUES (" +
                            SQLLite.SQLVarchar(Descricao) + "," + SQLLite.SQLInteger(CodFormaPag) + "," +
                            SQLLite.SQLInteger(CodDespesa) + "," + SQLLite.SQLInteger(CodFonteDespesa) + "," +
                            SQLLite.SQLDate(EditData.getText().toString()) + "," + SQLLite.SQLInteger(CodParc) + "," +
                            SQLLite.SQLDate(Util.DataToString(DataInc, "")) + "," + SQLLite.SQLNueric(Valor / QtdeParc) + ");\n";
                    SQLLite.dataBase.execSQL(SQL);
                }
            }
            if ( pf.preferences.getBoolean("multiplos_pagamentos",false) ){
                Pagamento = new ArrayList<ObjectPagamento>();
                PreencheListPagamento();
            }
            EditDescricao.setText(null);
            EditData.setText(null);
            EditValor.setText("0.00");
            EditNumParc.setText(null);
            Toast.makeText(context, "Gravado Com Sucesso!", Toast.LENGTH_LONG).show();
        }
    }

    private Boolean ValidaCampos(){
        if ( Util.Trim(EditDescricao.getText().toString()).equals("") ){
            Toast.makeText(context,"Informe a Descrição",Toast.LENGTH_SHORT).show();
            return false;
        }
        if ( EditData.length()<10 ){
            Toast.makeText(context, "Data Inválida", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ( pf.preferences.getBoolean("multiplos_pagamentos",false) && ListPagamento.getCount()<=0 ||
             !pf.preferences.getBoolean("multiplos_pagamentos",false)) {
            if (!ValidaCamposInserir())
                return false;
        }
        if ( SpinnerFormaPag.getCount()==0 ){
            Toast.makeText(context, "Cadastre as Formas de Pagamento", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            Cursor CursorFormaPag = SQLLite.ConsultaFormasPagNome(SpinnerFormaPag.getSelectedItem().toString());
            if ( CursorFormaPag.getString(CursorFormaPag.getColumnIndex("parcela")).equals("T") &&
                 Util.Trim(EditNumParc.getText().toString()).equals("") ){
                Toast.makeText(context, "Informe o Numero de Parcelas", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private Boolean ValidaCamposInserir(){
        if ( Util.Trim(EditValor.getText().toString()).equals("") ){
            Toast.makeText(context,"Informe o Valor",Toast.LENGTH_SHORT).show();
            return false;
        }
        if ( SpinnerDespesas.getCount()==0 ){
            Toast.makeText(context, "Cadastre os Tipos de Despesa", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ( SpinnerFonteDespesa.getCount()==0 ){
            Toast.makeText(context, "Cadastre as Fontes de Despesa", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private static void setListViewHeight(ArrayAdapter adapter, ListView view){
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, view);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams dParam = view.getLayoutParams();
        dParam.height = totalHeight + ((view.getDividerHeight()) * (adapter.getCount() - 1));
        view.setLayoutParams(dParam);
        view.requestLayout();
    }

    public static void PreencheListPagamento(){
        PagamentoAdapter adapter = new PagamentoAdapter(context, Pagamento);
        ListPagamento.setAdapter(adapter);
        setListViewHeight(adapter, ListPagamento);
        EditValor.setText("0.00");
    }

    private void AddPagamento(Float valor, String despesa, String fonteDespesa){
        ObjectPagamento e = null;
        e = new ObjectPagamento(valor, despesa, fonteDespesa);
        Pagamento.add(e);
    }

    public void getPesquisa(Integer codigo) {
    }

}
