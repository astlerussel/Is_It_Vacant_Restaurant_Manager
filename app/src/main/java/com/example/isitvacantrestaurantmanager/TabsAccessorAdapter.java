package com.example.isitvacantrestaurantmanager;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsAccessorAdapter extends FragmentPagerAdapter {


    public TabsAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                PreviousReservations previousReservations = new PreviousReservations();
                return previousReservations;
            case 1:
                CurrentReservations currentReservations = new CurrentReservations();
                return currentReservations;
                default:
                    return null;

        }

    }


    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Previous Reservations";
            case 1:
                return "Current Reservations";

            default:
                return null;

        }
    }
}
