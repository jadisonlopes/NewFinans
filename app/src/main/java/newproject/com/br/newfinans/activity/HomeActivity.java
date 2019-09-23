package newproject.com.br.newfinans.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.adapter.GraficoAdapter;
import newproject.com.br.newfinans.objetc.ObjectGrafico;
import newproject.com.br.newfinans.teste.TesteActivity;
import newproject.com.br.newfinans.util.SQLLite;
import newproject.com.br.newfinans.util.Util;

public class HomeActivity extends AppCompatActivity {

    private Toolbar ToolbarPrincipal;
    private ImageView IconGastos;
    private ImageView IconRenda;
    private ImageView IconFormasPag;
    private ImageView IconDespesas;
    private ImageView IconFonteDespesas;
    private ImageView ButtonFiltro;
    private ImageView ButtonFiltrar;
    private ImageView ButtonFCancelar;
    private ImageView ButtonVerDatalhes;
    private RelativeLayout LayoutFiltro;
    private ScrollView ScrolFiltro;
    private Button Teste;
    private PieChart GraficoSaldo;
    private ListView ListFormaPag;
    private ListView ListDespesas;
    private ListView ListFonteDespesas;
    private TextView TotRenda;
    private TextView TotGasto;
    private TextView TotSaldo;
    private TextView Porcent;
    private TextView Compentencia;
    private Spinner SpinnerMeses;
    private  Spinner SpinnerAnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ToolbarPrincipal  = (Toolbar) findViewById(R.id.toolbar);
        IconFormasPag     = (ImageView) findViewById(R.id.idIconFormasPag);
        IconRenda         = (ImageView) findViewById(R.id.idIconRenda);
        IconGastos        = (ImageView) findViewById(R.id.idIconGastos);
        IconDespesas      = (ImageView) findViewById(R.id.idIconDespesas);
        IconFonteDespesas = (ImageView) findViewById(R.id.idIconFonteDespesa);
        GraficoSaldo      = (PieChart) findViewById(R.id.idPieChartHSaldo);
        ListFormaPag      = (ListView) findViewById(R.id.idListGraficoFormaPag);
        ListDespesas      = (ListView) findViewById(R.id.idListGraficoDespesa);
        ListFonteDespesas = (ListView) findViewById(R.id.idListGraficoFonteDespesa);
        ButtonFiltro      = (ImageView) findViewById(R.id.idFiltrarH);
        ButtonFiltrar     = (ImageView) findViewById(R.id.idHBtFiltar);
        ButtonFCancelar   = (ImageView) findViewById(R.id.idHBtFCancelar);
        ButtonVerDatalhes = (ImageView) findViewById(R.id.idHBtVerDetalhes);
        ScrolFiltro       = (ScrollView) findViewById(R.id.idScrolViewDetalhe);
        LayoutFiltro      = (RelativeLayout) findViewById(R.id.idLayoutFiltroH);
        TotRenda          = (TextView) findViewById(R.id.idTextTotRenda);
        TotGasto          = (TextView) findViewById(R.id.idTextTotGasto);
        TotSaldo          = (TextView) findViewById(R.id.idTextTotSaldo);
        Porcent           = (TextView) findViewById(R.id.idTextPorcent);
        Compentencia      = (TextView) findViewById(R.id.idTextCompentencia);
        SpinnerMeses      = (Spinner)  findViewById(R.id.idHSpinnerMes);
        SpinnerAnos       = (Spinner)  findViewById(R.id.idHSpinnerAno);

        CarregaGrafico("");

        //Teste
        Teste = (Button) findViewById(R.id.button);

        //Atribuindo logo a toolbar
        ToolbarPrincipal.setLogo(R.drawable.logo_toolbar);
        ToolbarPrincipal.setTitle("");

        //Setando toolbar
        setSupportActionBar(ToolbarPrincipal);

        ArrayAdapter <String> AdapterMes = new ArrayAdapter<String>(HomeActivity.this,android.R.layout.simple_list_item_activated_1, Util.Meses);
        AdapterMes.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        SpinnerMeses.setAdapter(AdapterMes);

        ArrayAdapter <String> AdapterAno = new ArrayAdapter<String>(HomeActivity.this,android.R.layout.simple_list_item_activated_1,Util.Anos);
        AdapterAno.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        SpinnerAnos.setAdapter(AdapterAno);

//        Date Hoje = new Date();
        SpinnerMeses.setSelection(Util.Hoje.getMonth());
        Compentencia.setText(SpinnerMeses.getSelectedItem().toString());
        SpinnerAnos.setSelection(2);

        ButtonVerDatalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ScrolFiltro.getVisibility()==View.VISIBLE) {
                    ScrolFiltro.setVisibility(View.GONE);
                }else{
                    ScrolFiltro.setVisibility(View.VISIBLE);
                }
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
                CarregaGrafico("01/"+Util.PreencheZeros(String.valueOf(SpinnerMeses.getSelectedItemPosition()+1),2)+"/"+
                        SpinnerAnos.getSelectedItem().toString());
                Compentencia.setText(SpinnerMeses.getSelectedItem().toString());
            }
        });


        IconFormasPag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,FormasPagActivity.class));
            }
        });

        IconRenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,RendaActivity.class));
            }
        });

        IconGastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,GastosActivity.class));
            }
        });

        IconGastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,GastosActivity.class));
            }
        });

        IconDespesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,DespesasActivity.class));
            }
        });

        IconFonteDespesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,FonteDespesaActivity.class));
            }
        });

        Teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, TesteActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ){
            case (R.id.idItem_Configuracoes):
                startActivity(new Intent(HomeActivity.this, ConfigActivity.class));
                break;
            case (R.id.idItem_Sair):
                Toast.makeText(HomeActivity.this,"Até Logo!",Toast.LENGTH_SHORT).show();
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        SpinnerMeses.setSelection(Util.Hoje.getMonth());
        Compentencia.setText(SpinnerMeses.getSelectedItem().toString());
        SpinnerAnos.setSelection(2);
        CarregaGrafico("");

    }

    private void CarregaGrafico(String Data){
        Date RetornoData = null;
        Integer Porcentagem;
        if (!Data.equals("")){
            RetornoData = Util.DataRetorno(Data);
        }else {
            RetornoData = Util.Hoje;
        }
        Float Renda = SQLLite.TotalizaRenda(RetornoData);
        Float Gastos = SQLLite.TotalizaGasto(RetornoData);
        Cursor CursorFormaPag = SQLLite.TotalizaFormaPag(RetornoData);
        Cursor CursorDespesa = SQLLite.TotalizaDespesa(RetornoData);
        Cursor CursorFonteDespesa = SQLLite.TotalizaFonteDespesa(RetornoData);

        ArrayList<ObjectGrafico> formapag = new ArrayList<ObjectGrafico>();
        ObjectGrafico f = null;
        while ( CursorFormaPag.getCount() > CursorFormaPag.getPosition() ){
            f = new ObjectGrafico(
                    CursorFormaPag.getFloat(CursorFormaPag.getColumnIndex("total")),
                    CursorFormaPag.getString(CursorFormaPag.getColumnIndex("descricao")));
            CursorFormaPag.moveToNext();
            formapag.add(f);
        }

        ArrayList<ObjectGrafico> despesa = new ArrayList<ObjectGrafico>();
        ObjectGrafico d = null;
        while ( CursorDespesa.getCount() > CursorDespesa.getPosition() ){
            d = new ObjectGrafico(
                    CursorDespesa.getFloat(CursorDespesa.getColumnIndex("total")),
                    CursorDespesa.getString(CursorDespesa.getColumnIndex("descricao")));
            CursorDespesa.moveToNext();
            despesa.add(d);
        }

        ArrayList<ObjectGrafico> fontedespesa = new ArrayList<ObjectGrafico>();
        ObjectGrafico fd = null;
        while ( CursorFonteDespesa.getCount() > CursorFonteDespesa.getPosition() ){
            fd = new ObjectGrafico(
                    CursorFonteDespesa.getFloat(CursorFonteDespesa.getColumnIndex("total")),
                    CursorFonteDespesa.getString(CursorFonteDespesa.getColumnIndex("descricao")));
            CursorFonteDespesa.moveToNext();
            fontedespesa.add(fd);
        }

        ArrayAdapter adapterFormaPag = new GraficoAdapter(HomeActivity.this, formapag);
        ListFormaPag.setAdapter(adapterFormaPag);
        setListViewHeight(adapterFormaPag,ListFormaPag);

        ArrayAdapter adapterDespesa = new GraficoAdapter(HomeActivity.this, despesa);
        ListDespesas.setAdapter(adapterDespesa);
        setListViewHeight(adapterDespesa,ListDespesas);

        ArrayAdapter adapterFonteDespesa = new GraficoAdapter(HomeActivity.this, fontedespesa);
        ListFonteDespesas.setAdapter(adapterFonteDespesa);
        setListViewHeight(adapterFonteDespesa,ListFonteDespesas);

        Float Saldo;
        if (Renda<Gastos){
            Saldo=Float.valueOf(0);
        }else {
            Saldo=Renda-Gastos;
        }
        ArrayList<PieEntry> SaldoArray = new ArrayList<>();

        SaldoArray.add(new PieEntry(Saldo,"Saldo"));
        SaldoArray.add(new PieEntry(Gastos,"Gastos"));

        TotRenda.setText(Util.SetMaskNumeric(Renda,"R$"));
        TotGasto.setText(Util.SetMaskNumeric(Gastos,"R$"));
        TotSaldo.setText(Util.SetMaskNumeric(Renda-Gastos,"R$"));
        if ( Renda > 0 ){
            Porcentagem = (int)((1-Gastos/Renda)*100);
        }else{
            Porcentagem = 0;
        }
        Porcent.setText(Integer.toString(Porcentagem)+"%");

        ConfiguraGrafico(GraficoSaldo);
        GraficoSaldo.invalidate();
        GraficoSaldo.setData(PreencheGrafico(SaldoArray));
    }

    private void setListViewHeight(ArrayAdapter adapter, ListView view ){
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

    private void ConfiguraGrafico(PieChart Grafico){
        Grafico.setVisibility(View.VISIBLE);
        Grafico.setDrawEntryLabels(false); //Visible do Texto dos Percentuais
        Grafico.setUsePercentValues(true); //Mostrar percentual
        Grafico.setEntryLabelTextSize(25f); //Tamanho do Texto dos Percentuais
        Grafico.setEntryLabelColor(Color.BLACK); //Color do Texto dos Percentuais
        Grafico.getDescription().setEnabled(false);
        Grafico.setExtraOffsets(1,10,1,-10);
        Grafico.setDragDecelerationFrictionCoef(0.99f); //Rotação do grafico

        Grafico.setDrawHoleEnabled(true); //Circulo interno Transparente
        Grafico.setHoleRadius(90f); //Raio do Circulo
        Grafico.setHoleColor(Color.TRANSPARENT); //Color Circulo
        Grafico.setTransparentCircleRadius(93f); //Raio do Transparence
    }

    private PieData PreencheGrafico(ArrayList<PieEntry> Array){

        PieDataSet dataSet = null;
        dataSet = new PieDataSet(Array,""); //Descrição do Grafico
        dataSet.setSliceSpace(3f); //Espaço entre os percentuais
        dataSet.setSelectionShift(10f); //Tamanho ao selecionar
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS); //Cor do grafico

        PieData data = null;
        data = new PieData((dataSet));
        data.setValueTextSize(0f); //Tamanho dos Percentuais
        data.setValueTextColor(Color.DKGRAY); //Cor dos Percetuais

        return data;
    }

}
