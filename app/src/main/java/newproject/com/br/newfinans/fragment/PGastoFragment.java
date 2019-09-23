package newproject.com.br.newfinans.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.adapter.GastoAdapter;
import newproject.com.br.newfinans.objetc.ObjectGasto;
import newproject.com.br.newfinans.util.DatePickerFragment;
import newproject.com.br.newfinans.util.SQLLite;
import newproject.com.br.newfinans.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class PGastoFragment extends Fragment {

    public static PGastoFragment PGasto;
    public  Context context;
    private ListView listView;
    private TextView TextTot;
    private EditText DataIni;
    private EditText DataFim;
    private EditText Descricao;
    private Spinner SpinnerDespesa;
    private Spinner SpinnerFonteDespesa;
    private Spinner SpinnerFormaPag;
    private ArrayList<ObjectGasto> ArrayGasto;

//    private SwipeRefreshLayout SRefresh;
    private RelativeLayout LayoutFiltro;
    private ImageView CalendarIni;
    private ImageView CalendarFim;
    private ImageView ButtonFiltro;
    private ImageView ButtonFiltrar;
    private ImageView ButtonFCancelar;
    private Float Tot;
    private GastoLis listener;
    private Date data;

    public PGastoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        PGasto = this;

        View v = inflater.inflate(R.layout.fragment_pgasto, container, false);

        listView             = v.findViewById(R.id.idListGPesquisa);
//        SRefresh             = v.findViewById(R.id.idFGasto);
        TextTot              = v.findViewById(R.id.idGTot);
        LayoutFiltro         = v.findViewById(R.id.idLayoutFiltroG);
        ButtonFiltro         = v.findViewById(R.id.idFiltrarG);
        ButtonFiltrar        = v.findViewById(R.id.idGBtFiltar);
        ButtonFCancelar      = v.findViewById(R.id.idGBtFCancelar);
        CalendarIni          = v.findViewById(R.id.idGImageCalendarIni);
        CalendarFim          = v.findViewById(R.id.idGImageCalendarFim);
        DataIni              = v.findViewById(R.id.idGEditDataIni);
        DataFim              = v.findViewById(R.id.idGEditDataFim);
        Descricao            = v.findViewById(R.id.idGEditPDescricao);
        SpinnerDespesa       = v.findViewById(R.id.idGSpinnerDespesa);
        SpinnerFonteDespesa  = v.findViewById(R.id.idGSpinnerFonteDespesa);
        SpinnerFormaPag      = v.findViewById(R.id.idGSpinnerFormaPag);

        PreencerSpinner();

        /*Validando data*/
        data = Util.Hoje;
        Util.MaskDate(DataIni);
        Util.MaskDate(DataFim);
        DataIni.setOnFocusChangeListener(Util.ValidaData(DataIni,context));
        DataFim.setOnFocusChangeListener(Util.ValidaData(DataFim,context));
        DataIni.setText(Util.DataToString(Util.DataPriDiaMes(Util.Hoje),""));
        DataFim.setText(Util.DataToString(Util.DataUltDiaMes(Util.Hoje),""));
        CalendarIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !DataIni.getText().toString().equals("") )
                    data = Util.DataRetorno(DataIni.getText().toString());
                DialogFragment dialogFragment = new DatePickerFragment(DataIni,data);
                dialogFragment.show(getFragmentManager(),"");
            }
        });
        CalendarFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !DataFim.getText().toString().equals("") )
                    data = Util.DataRetorno(DataFim.getText().toString());
                DialogFragment dialogFragment = new DatePickerFragment(DataFim,data);
                dialogFragment.show(getFragmentManager(),"");
            }
        });

        /*Tornando o filtro visível*/
        ButtonFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutFiltro.setVisibility(View.VISIBLE);
            }
        });
        ButtonFCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutFiltro.setVisibility(View.GONE);
            }
        });
        ButtonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutFiltro.setVisibility(View.GONE);
                PreencheList();
            }
        });

//        SRefresh.setColorSchemeResources(R.color.swip_1,R.color.swip_2,R.color.swip_1);
//        SRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                PreencheList();
//                SRefresh.setRefreshing(false);
//            }
//        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Excluir");
                builder.setMessage("Confirmar Exclusão?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        SQLLite.SQLExcluir(ArrayGasto.get(i).getCod(),"gasto",context);
                        PreencheList();
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getActivity(), "Oparação Cancelada!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create();
                builder.show();
                return false;
            }
        });
        return v;
    }
    /*========================*/
    public void PreencheList(){
        ArrayGasto = adicionarGasto(CarregaFiltro());
        ArrayAdapter adapter = new GastoAdapter(context, ArrayGasto);
        listView.setAdapter(adapter);
        TextTot.setText("Total: "+Util.SetMaskNumeric(Tot,"R$"));
    }
    /*========================*/
    public ArrayList<ObjectGasto> adicionarGasto(String S) {
        ArrayList<ObjectGasto> gasto = new ArrayList<ObjectGasto>();
        ObjectGasto e = null;
        Cursor CursorGasto = SQLLite.ConsultaGasto(S);
        Tot = Float.valueOf(0);
        while (CursorGasto.getCount() > CursorGasto.getPosition() ){
            e = new ObjectGasto(CursorGasto.getInt(CursorGasto.getColumnIndex("cod")),
                    CursorGasto.getString(CursorGasto.getColumnIndex("descricao")),
                    CursorGasto.getInt(CursorGasto.getColumnIndex("formapag")),
                    CursorGasto.getInt(CursorGasto.getColumnIndex("codparc")),
                    CursorGasto.getInt(CursorGasto.getColumnIndex("fonte_despesa")),
                    CursorGasto.getInt(CursorGasto.getColumnIndex("despesa")),
                    SQLLite.SQLDateToDate(CursorGasto.getString(CursorGasto.getColumnIndex("datacartao"))),
                    SQLLite.SQLDateToDate(CursorGasto.getString(CursorGasto.getColumnIndex("data"))),
                    CursorGasto.getFloat(CursorGasto.getColumnIndex("valor")));
            Tot = Tot + CursorGasto.getFloat(CursorGasto.getColumnIndex("valor"));
            CursorGasto.moveToNext();
            gasto.add(e);
        }
        return gasto;
    }
    /*===========================*/
    private void PreencerSpinner(){
        List ListaFormaPag = new ArrayList();
        Cursor CursorFormaPag = SQLLite.ConsultaFormaPag(0);
        int IndexF = CursorFormaPag.getColumnIndex("descricao");
        ListaFormaPag.add("TODAS");
        while ( CursorFormaPag.getCount() > CursorFormaPag.getPosition() ){
            ListaFormaPag.add(CursorFormaPag.getString(IndexF));
            CursorFormaPag.moveToNext();
        }
        ArrayAdapter <List> AdapterFormaPag = new ArrayAdapter<List>(context,android.R.layout.simple_list_item_activated_1,ListaFormaPag);
        AdapterFormaPag.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        SpinnerFormaPag.setAdapter(AdapterFormaPag);

        List ListaDespesa = new ArrayList();
        Cursor CursorDespesa = SQLLite.ConsultaDespesa(0);
        int IndexD = CursorDespesa.getColumnIndex("descricao");
        ListaDespesa.add("TODAS");
        while ( CursorDespesa.getCount() > CursorDespesa.getPosition() ){
            ListaDespesa.add(CursorDespesa.getString(IndexD));
            CursorDespesa.moveToNext();
        }
        ArrayAdapter <List> AdapterDespesa = new ArrayAdapter<List>(context,android.R.layout.simple_list_item_activated_1,ListaDespesa);
        AdapterDespesa.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        SpinnerDespesa.setAdapter(AdapterDespesa);

        List ListaFonteDespesa = new ArrayList();
        Cursor CursorFonteDespesa = SQLLite.ConsultaFonteDespesa(0);
        int IndexFD = CursorFonteDespesa.getColumnIndex("descricao");
        ListaFonteDespesa.add("TODAS");
        while ( CursorFonteDespesa.getCount() > CursorFonteDespesa.getPosition() ){
            ListaFonteDespesa.add(CursorFonteDespesa.getString(IndexFD));
            CursorFonteDespesa.moveToNext();
        }
        ArrayAdapter <List> AdapterFonteDespesa = new ArrayAdapter<List>(context,android.R.layout.simple_list_item_activated_1,ListaFonteDespesa);
        AdapterFonteDespesa.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        SpinnerFonteDespesa.setAdapter(AdapterFonteDespesa);
    }
    private String CarregaFiltro(){
        String Sql;
        Cursor CursorFormaPag = SQLLite.ConsultaFormasPagNome(SpinnerFormaPag.getSelectedItem().toString());
        Cursor CursorDespesa = SQLLite.ConsultaDespesaNome(SpinnerDespesa.getSelectedItem().toString());
        Cursor CursorFonteDespesa = SQLLite.ConsultaFonteDespesaNome(SpinnerFonteDespesa.getSelectedItem().toString());
        Sql = "SELECT * FROM gasto WHERE 1=1 "; //Alterar consulta por data.
        if (!Descricao.getText().toString().equals(""))
            Sql += " AND descricao like '%'||"+SQLLite.SQLVarchar(Descricao.getText().toString())+"||'%'";
        if (!DataIni.getText().toString().equals(""))
            Sql += " AND data>="+SQLLite.SQLDate(DataIni.getText().toString());
        if (!DataFim.getText().toString().equals(""))
            Sql += " AND data<="+SQLLite.SQLDate(DataFim.getText().toString());
        if ( !SpinnerDespesa.getSelectedItem().toString().equals("TODAS") )
            Sql = Sql+" AND despesa="+SQLLite.SQLInteger(CursorDespesa.getInt(CursorDespesa.getColumnIndex("cod")));
        if ( !SpinnerFonteDespesa.getSelectedItem().toString().equals("TODAS") )
            Sql = Sql+" AND fonte_despesa="+SQLLite.SQLInteger(CursorFonteDespesa.getInt(CursorFonteDespesa.getColumnIndex("cod")));
        if ( !SpinnerFormaPag.getSelectedItem().toString().equals("TODAS") )
            Sql = Sql+" AND formapag="+SQLLite.SQLInteger(CursorFormaPag.getInt(CursorFormaPag.getColumnIndex("cod")));
        Sql = Sql+" ORDER BY data; ";
        return Sql;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (GastoLis) context;
    }

    public interface GastoLis {
        void setCodigo(Integer codigo);
    }

}
