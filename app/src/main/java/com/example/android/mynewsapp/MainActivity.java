package com.example.android.mynewsapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.os.Bundle;

    /**
    *This is the only activity that this app uses, it uses a ViewPager, TabLayout and ViewPagerAdapter to
    * manage 3 different fragments
     * it implements {@link SportsNewsFragment.OnListFragmentInteractionListener} to respond to
     * interaction on each fragment.
    */
public class MainActivity extends AppCompatActivity
        implements SportsNewsFragment.OnListFragmentInteractionListener,
        PoliticsNewsFragment.OnListFragmentInteractionListener,
        TechNewsFragment.OnListFragmentInteractionListener{


    ViewPager viewPager;
    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout) ;
        tabLayout.setupWithViewPager(viewPager);
        // Returns the page title for the top indicator
        tabLayout.addTab(tabLayout.newTab().setText("Sports"));
        tabLayout.addTab(tabLayout.newTab().setText("Tech"));
        tabLayout.addTab(tabLayout.newTab().setText("Politics"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onListFragmentInteraction(News item) {

    }
}
