package cl.aguzman.prueba3.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cl.aguzman.prueba3.views.main.list.ListAllTalesFragment;
import cl.aguzman.prueba3.views.main.list.ListFavoritesFragment;
import cl.aguzman.prueba3.views.main.list.ListMyTalesFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return ListAllTalesFragment.newInstance();
            case 1:
                return ListFavoritesFragment.newInstance();
            case 2:
                return ListMyTalesFragment.newInstance();
            default:
                return ListAllTalesFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Cuentos de Terror";
            case 1:
                return "Mis Favoritos";
            case 2:
                return "Mis Cuentos";
        }
        return null;
    }
}
