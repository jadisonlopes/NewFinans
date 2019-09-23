package newproject.com.br.newfinans.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import newproject.com.br.newfinans.fragment.CFonteDespesaFragment;
import newproject.com.br.newfinans.fragment.PFonteDespesaFragment;

public class TabAdapterFD extends FragmentPagerAdapter {

    private String [] TituloAbas = {"CADASTRO","PESQUISA"};

    public TabAdapterFD(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //Pegando a posição do Fragmento e Setando fragmento de retorno;
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new CFonteDespesaFragment();
                break;
            case 1:
                fragment = new PFonteDespesaFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        //Setando a quantidade de Abas
        return TituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //Setando titulo nas Abas
        return TituloAbas[position];
    }
}
