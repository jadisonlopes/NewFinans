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
import android.widget.ListView;

import java.util.ArrayList;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.adapter.FormasPagAdapter;
import newproject.com.br.newfinans.objetc.ObjectFormasPag;
import newproject.com.br.newfinans.util.SQLLite;
import newproject.com.br.newfinans.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class PFormasPagFragment extends Fragment {

    public static PFormasPagFragment PFormasPag;
    private Context context;
    private ListView listView;
    private SwipeRefreshLayout SRefresh;
    private ArrayList<ObjectFormasPag> ArrayFormasPag;
    private FormasPagLis listener;

    public PFormasPagFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        PFormasPag = this;
        View v =  inflater.inflate(R.layout.fragment_pformas_pag, container, false);

        listView  = v.findViewById(R.id.idListFPPesquisa);
        SRefresh  = v.findViewById(R.id.idFFormaPag);

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
                builder.setTitle("Opção");
                builder.setMessage("Realizar qual operação?");
                builder.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        listener.setCodigo(ArrayFormasPag.get(i).getCod());
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        listener.setCodigo(0);
                    }
                });
                builder.setNeutralButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLLite.SQLExcluir(ArrayFormasPag.get(i).getCod(), "formapag", context);
                        PreencheList();
                    }
                });
                builder.create();
                builder.show();
                return false;
            }
        });

        return v;
    }

    public void PreencheList() {
        ArrayFormasPag = adicionarFormasPag();
        ArrayAdapter adapter = new FormasPagAdapter(context, ArrayFormasPag);
        listView.setAdapter(adapter);
    }

    private ArrayList<ObjectFormasPag> adicionarFormasPag() {
        ArrayList<ObjectFormasPag> formaspag = new ArrayList<ObjectFormasPag>();
        ObjectFormasPag e = null;

        Cursor CursorFormasPag = SQLLite.ConsultaFormaPag(0);
        CursorFormasPag.moveToFirst();
        while (CursorFormasPag.getCount() > CursorFormasPag.getPosition() ){
            e = new ObjectFormasPag(CursorFormasPag.getInt(CursorFormasPag.getColumnIndex("cod")),
                                    CursorFormasPag.getInt(CursorFormasPag.getColumnIndex("diacomp")),
                                    CursorFormasPag.getInt(CursorFormasPag.getColumnIndex("venc")),
                                    Util.TestBoolean(CursorFormasPag.getString(CursorFormasPag.getColumnIndex("parcela"))),
                                    Util.TestBoolean(CursorFormasPag.getString(CursorFormasPag.getColumnIndex("exclusao"))),
                                    CursorFormasPag.getString(CursorFormasPag.getColumnIndex("tipo")),
                                    CursorFormasPag.getString(CursorFormasPag.getColumnIndex("descricao")));
            CursorFormasPag.moveToNext();
            formaspag.add(e);
        }
        return formaspag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (FormasPagLis) context;
    }

    public interface FormasPagLis {
        void setCodigo(Integer codigo);
    }
}
