package newproject.com.br.newfinans.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.adapter.RendaAdapter;
import newproject.com.br.newfinans.objetc.ObjectRenda;
import newproject.com.br.newfinans.util.SQLLite;
import newproject.com.br.newfinans.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class PRendaFragment extends Fragment {

    public static PRendaFragment PRenda;
    private ListView listView;
    private Context context;
    private TextView TextTot;
    private Float Tot;
    private RelativeLayout LayoutFiltro;
    private ImageView ButtonFiltro;
    private ImageView ButtonFiltrar;
    private ImageView ButtonFCancelar;
    private ArrayList<ObjectRenda> ArrayRenda;
    private Spinner SpinnerMeses;
    private Spinner SpinnerAnos;
    private SwipeRefreshLayout SRefresh;

    public PRendaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        PRenda = this;
        View v = inflater.inflate(R.layout.fragment_prenda, container, false);

        listView        = v.findViewById(R.id.idListRPesquisa);
        SpinnerMeses    = v.findViewById(R.id.idRSpinnerMes);
        SpinnerAnos     = v.findViewById(R.id.idRSpinnerAno);
        LayoutFiltro    = v.findViewById(R.id.idLayoutFiltroR);
        ButtonFiltro    = v.findViewById(R.id.idFiltrarR);
        ButtonFiltrar   = v.findViewById(R.id.idRBtFiltar);
        ButtonFCancelar = v.findViewById(R.id.idRBtFCancelar);
        TextTot         = v.findViewById(R.id.idRTot);
        SRefresh        = v.findViewById(R.id.idFRenda);


        ArrayAdapter <String> AdapterMes = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_activated_1, Util.Meses);
        AdapterMes.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        SpinnerMeses.setAdapter(AdapterMes);

        ArrayAdapter <String> AdapterAno = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_activated_1,Util.Anos);
        AdapterAno.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        SpinnerAnos.setAdapter(AdapterAno);

//        Date Hoje = new Date();
        SpinnerMeses.setSelection(Util.Hoje.getMonth());
        SpinnerAnos.setSelection(2);

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

        SRefresh.setColorSchemeResources(R.color.swip_1,R.color.swip_2,R.color.swip_1);
        SRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PreencheList();
                SRefresh.setRefreshing(false);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Excluir");
                builder.setMessage("Confirmar Exclusão?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        SQLLite.SQLExcluir(ArrayRenda.get(i).getCod(),"renda",context);
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

    public void PreencheList(){
        ArrayRenda = adicionarRenda();
        ArrayAdapter adapter = new RendaAdapter(context, ArrayRenda);
        listView.setAdapter(adapter);
        TextTot.setText("Total: "+Util.SetMaskNumeric(Tot,"R$"));
    }

    private ArrayList<ObjectRenda> adicionarRenda() {
        ArrayList<ObjectRenda> renda = new ArrayList<ObjectRenda>();
        ObjectRenda e = null;

        Cursor CursorRenda = SQLLite.ConsultaRendaData(String.valueOf(SpinnerMeses.getSelectedItemPosition()+1),
                SpinnerAnos.getSelectedItem().toString());

        Tot = Float.valueOf(0);
        while (CursorRenda.getCount() > CursorRenda.getPosition() ){
            e = new ObjectRenda(CursorRenda.getInt(CursorRenda.getColumnIndex("cod")),
                          CursorRenda.getFloat(CursorRenda.getColumnIndex("valor")),
                          CursorRenda.getString(CursorRenda.getColumnIndex("teste")),
                          CursorRenda.getString(CursorRenda.getColumnIndex("descricao")));
            Tot = Tot + CursorRenda.getFloat(CursorRenda.getColumnIndex("valor"));
            CursorRenda.moveToNext();
            renda.add(e);
        }
        return renda;
    }
}
