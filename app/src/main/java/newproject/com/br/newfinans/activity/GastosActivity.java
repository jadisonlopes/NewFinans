package newproject.com.br.newfinans.activity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.adapter.TabAdapterG;
import newproject.com.br.newfinans.fragment.CGastoFragment;
import newproject.com.br.newfinans.fragment.PGastoFragment;
import newproject.com.br.newfinans.tabs.SlidingTabLayout;
import newproject.com.br.newfinans.util.Util;

public class GastosActivity extends AppCompatActivity implements PGastoFragment.GastoLis {

    private Toolbar ToolbarPrincipal;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    public  static Context c = null;

    private Integer i;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);

/*        ToolbarPrincipal = (Toolbar) findViewById(R.id.toolbar);
        ToolbarPrincipal.setLogo(R.drawable.logo_toolbar);*/

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabsG);
        viewPager = (ViewPager) findViewById(R.id.vp_paginaG);

        //Configurando as Tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.SilverWhite));

        //Configurando o Adapter
        TabAdapterG GastoAdapterTabs = new TabAdapterG(getSupportFragmentManager());
        viewPager.setAdapter(GastoAdapterTabs);

        slidingTabLayout.setViewPager(viewPager);

        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    PGastoFragment.PGasto.PreencheList();
                    Util.OcultaTeclado(slidingTabLayout,GastosActivity.this);
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
        CGastoFragment.CGasto.getPesquisa(codigo);
    }
}
