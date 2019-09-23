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
import newproject.com.br.newfinans.adapter.DespesaAdapter;
import newproject.com.br.newfinans.objetc.ObjectDespesa;
import newproject.com.br.newfinans.util.SQLLite;

/**
 * A simple {@link Fragment} subclass.
 */
public class PDespesaFragment extends Fragment {

    public static PDespesaFragment PDespesa;
    private ListView listView;
    private Context context;
    private SwipeRefreshLayout SRefresh;
    private ArrayList<ObjectDespesa> ArrayDespesa;
    private DespesaLis listener;

    public PDespesaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        PDespesa = this;
        View v = inflater.inflate(R.layout.fragment_pdespesa, container, false);

        listView = v.findViewById(R.id.idListDPesquisa);
        SRefresh = v.findViewById(R.id.idFDespesa);

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
                        listener.setCodigo(ArrayDespesa.get(i).getCod());
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
                        SQLLite.SQLExcluir(ArrayDespesa.get(i).getCod(), "despesa", context);
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
        ArrayDespesa = adicionarDespesa();
        ArrayAdapter adapter = new DespesaAdapter(context, ArrayDespesa);
        listView.setAdapter(adapter);
    }

    private ArrayList<ObjectDespesa> adicionarDespesa() {
        ArrayList<ObjectDespesa> despesa = new ArrayList<ObjectDespesa>();
        ObjectDespesa e = null;

        Cursor CursorDespesa = SQLLite.ConsultaDespesa(0);
        CursorDespesa.moveToFirst();
        while (CursorDespesa.getCount() > CursorDespesa.getPosition()) {
            e = new ObjectDespesa(CursorDespesa.getInt(CursorDespesa.getColumnIndex("cod")),
                    CursorDespesa.getString(CursorDespesa.getColumnIndex("descricao")),
                    CursorDespesa.getString(CursorDespesa.getColumnIndex("observ")));
            CursorDespesa.moveToNext();
            despesa.add(e);
        }
        return despesa;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DespesaLis) context;
    }

    public interface DespesaLis {
        void setCodigo(Integer codigo);
    }
}
