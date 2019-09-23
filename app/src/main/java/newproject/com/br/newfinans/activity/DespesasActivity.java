package newproject.com.br.newfinans.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.adapter.TabAdapterD;
import newproject.com.br.newfinans.fragment.CDespesaFragment;
import newproject.com.br.newfinans.fragment.PDespesaFragment;
import newproject.com.br.newfinans.tabs.SlidingTabLayout;
import newproject.com.br.newfinans.util.Util;

public class DespesasActivity extends AppCompatActivity implements PDespesaFragment.DespesaLis {

    private Toolbar ToolbarPrincipal;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);
/*
        ToolbarPrincipal = (Toolbar) findViewById(R.id.toolbar);
        ToolbarPrincipal.setLogo(R.drawable.logo_toolbar);
*/
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabsD);
        viewPager = (ViewPager) findViewById(R.id.vp_paginaD);

        //Configurando as Tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.SilverWhite));

        //Configurando o Adapter
        TabAdapterD DespesaAdapterTabs = new TabAdapterD(getSupportFragmentManager());
        viewPager.setAdapter(DespesaAdapterTabs);

        slidingTabLayout.setViewPager(viewPager);

        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    PDespesaFragment.PDespesa.PreencheList();
                    Util.OcultaTeclado(slidingTabLayout, DespesasActivity.this);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void setCodigo(Integer codigo) {
        if (codigo>0)
            viewPager.setCurrentItem(0);
        CDespesaFragment.CDespesa.getPesquisa(codigo);
    }
}
