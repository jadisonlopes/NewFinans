package newproject.com.br.newfinans.teste;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.adapter.TabAdapter;
import newproject.com.br.newfinans.tabs.SlidingTabLayout;

public class TesteActivity extends AppCompatActivity {

    private Toolbar ToolbarPrincipal;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        ToolbarPrincipal = (Toolbar) findViewById(R.id.toolbar);
        ToolbarPrincipal.setLogo(R.drawable.logo_toolbar);


        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_pagina);

        //Configurando as Tabs
        slidingTabLayout.setDistributeEvenly(true);


        //Configurando o Adapter
        TabAdapter TesteAdapterTabs = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(TesteAdapterTabs);

        slidingTabLayout.setViewPager(viewPager);
    }
}
