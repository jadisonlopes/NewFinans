package newproject.com.br.newfinans.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.adapter.TabAdapter;
import newproject.com.br.newfinans.fragment.PRendaFragment;
import newproject.com.br.newfinans.tabs.SlidingTabLayout;
import newproject.com.br.newfinans.util.SQLLite;
import newproject.com.br.newfinans.util.Util;

public class RendaActivity extends AppCompatActivity {

    private Toolbar ToolbarPrincipal;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renda);

/*        ToolbarPrincipal = (Toolbar) findViewById(R.id.toolbar);
        ToolbarPrincipal.setLogo(R.drawable.logo_toolbar);*/

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabsR);
        viewPager = (ViewPager) findViewById(R.id.vp_paginaR);

        //Configurando as Tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.SilverWhite));

        //Configurando o Adapter
        TabAdapter RendaAdapterTabs = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(RendaAdapterTabs);

        slidingTabLayout.setViewPager(viewPager);

        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    PRendaFragment.PRenda.PreencheList();
                    Util.OcultaTeclado(slidingTabLayout,RendaActivity.this);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}

