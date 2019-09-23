package newproject.com.br.newfinans.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.adapter.TabAdapterFD;
import newproject.com.br.newfinans.fragment.CFonteDespesaFragment;
import newproject.com.br.newfinans.fragment.PFonteDespesaFragment;
import newproject.com.br.newfinans.tabs.SlidingTabLayout;
import newproject.com.br.newfinans.util.Util;

public class FonteDespesaActivity extends AppCompatActivity implements PFonteDespesaFragment.FonteDespesaLis{

    private Toolbar ToolbarPrincipal;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fonte_despesa);


/*
        ToolbarPrincipal = (Toolbar) findViewById(R.id.toolbar);
        ToolbarPrincipal.setLogo(R.drawable.logo_toolbar);
*/


        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabsFD);
        viewPager = (ViewPager) findViewById(R.id.vp_paginaFD);

        //Configurando as Tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.SilverWhite));


        //Configurando o Adapter
        TabAdapterFD FonteDespesaAdapterTabs = new TabAdapterFD(getSupportFragmentManager());
        viewPager.setAdapter(FonteDespesaAdapterTabs);

        slidingTabLayout.setViewPager(viewPager);

        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    PFonteDespesaFragment.PFDespesa.PreencheList();
                    Util.OcultaTeclado(slidingTabLayout,FonteDespesaActivity.this);
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
        CFonteDespesaFragment.CFDespesa.getPesquisa(codigo);
    }
}
