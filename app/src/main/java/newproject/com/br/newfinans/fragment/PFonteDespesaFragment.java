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
import newproject.com.br.newfinans.adapter.FonteDespesaAdapter;
import newproject.com.br.newfinans.objetc.ObjectFonteDespesa;
import newproject.com.br.newfinans.util.SQLLite;

/**
 * A simple {@link Fragment} subclass.
 */
public class PFonteDespesaFragment extends Fragment {

    public static PFonteDespesaFragment PFDespesa;
    private ListView listView;
    private Context context;
    private SwipeRefreshLayout SRefresh;
    private ArrayList<ObjectFonteDespesa> ArrayFonteDespesa;
    private FonteDespesaLis listener;


    public PFonteDespesaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        PFDespesa = this;
        View v = inflater.inflate(R.layout.fragment_pfonte_despesa, container, false);

        listView = v.findViewById(R.id.idListFDPesquisa);
        SRefresh = v.findViewById(R.id.idFFDespesa);

        SRefresh.setColorSchemeResources(R.color.swip_1, R.color.swip_2, R.color.swip_1);
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
                        listener.setCodigo(ArrayFonteDespesa.get(i).getCod());
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
                        SQLLite.SQLExcluir(ArrayFonteDespesa.get(i).getCod(), "fonte_despesa", context);
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
        ArrayFonteDespesa = adicionarFonteDespesa();
        ArrayAdapter adapter = new FonteDespesaAdapter(context, ArrayFonteDespesa);
        listView.setAdapter(adapter);
    }

    private ArrayList<ObjectFonteDespesa> adicionarFonteDespesa() {
        ArrayList<ObjectFonteDespesa> fontedespesa = new ArrayList<ObjectFonteDespesa>();
        ObjectFonteDespesa e = null;

        Cursor CursorFonteDespesa = SQLLite.ConsultaFonteDespesa(0);
        CursorFonteDespesa.moveToFirst();
        while (CursorFonteDespesa.getCount() > CursorFonteDespesa.getPosition()) {
            e = new ObjectFonteDespesa(CursorFonteDespesa.getInt(CursorFonteDespesa.getColumnIndex("cod")),
                    CursorFonteDespesa.getString(CursorFonteDespesa.getColumnIndex("descricao")),
                    CursorFonteDespesa.getString(CursorFonteDespesa.getColumnIndex("observ")));
            CursorFonteDespesa.moveToNext();
            fontedespesa.add(e);
        }
        return fontedespesa;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (FonteDespesaLis) context;
    }

    public interface FonteDespesaLis {
        void setCodigo(Integer codigo);
    }

}
