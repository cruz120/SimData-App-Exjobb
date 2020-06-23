package com.example.simdata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simdata.room.database.db.SwimpoolDatabase;
import com.example.simdata.room.database.db.entity.Swimpool;
import com.example.simdata.ui.reports.ReportsFragment;
import com.example.simdata.ui.swimlocation.SwimlocationFragment;

import java.util.List;
import java.util.Set;

import static com.example.simdata.room.database.db.SwimpoolDatabase.getInstance;

public class MainMenuActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
/*
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ACCESS_TOKEN = settings.getString("accessToken","defValue");
        MainMenuActivity main = new MainMenuActivity();
        Download startDownload = new Download(ACCESS_TOKEN,getApplicationContext(), main.getApplication());
        startDownload.getLocation();
        startDownload.getSwimPool();
        startDownload.getActivityType();
        SwimmingPoolDataRepository data = new SwimmingPoolDataRepository(main.getApplication());
        List<Swimpool> swimpools = data.getSwimpool();
        for(Swimpool id : swimpools){
            startDownload.getTemplates(Integer.toString(id.getId()));
        }
   */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        /**
         * LOGOUT STUFF!
         */
        if (id == R.id.update) {
            Intent intent = new Intent(this,LoginView.class);
            Toast.makeText(getApplicationContext(), "Uppdaterar",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
            return true;
        }
        /**
         * LOGOUT STUFF!
         */
        if (id == R.id.action_logout) {
            /*Intent intent = new Intent(this,LoginView.class);
            Toast.makeText(getApplicationContext(), "Logging out",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish(); */

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

/*
            View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.textView);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView;*/
return null;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                SwimlocationFragment swimFrag = new SwimlocationFragment();
                return swimFrag;
            } else if (position == 1) {
                ReportsFragment repFrag = new ReportsFragment();
                return repFrag;
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }

    public void download(){


    }
}
