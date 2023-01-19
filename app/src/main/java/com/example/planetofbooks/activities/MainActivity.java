package com.example.planetofbooks.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.planetofbooks.R;
import com.example.planetofbooks.SharedPref.SharedPrefManager;
import com.example.planetofbooks.fragments.AboutUsFragment;
import com.example.planetofbooks.fragments.ContactUsFragment;
import com.example.planetofbooks.fragments.FavoriteFragment;
import com.example.planetofbooks.fragments.NovelsFragment;
import com.example.planetofbooks.fragments.ProfileFragment;
import com.example.planetofbooks.fragments.SelfDevelopmentFragment;
import com.example.planetofbooks.fragments.SettingsFragment;
import com.example.planetofbooks.fragments.HomeFragment;
import com.example.planetofbooks.fragments.TechnologyFragment;
import com.example.planetofbooks.fragments.ViewBookFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    public static TextView navUsername,navEmail,navPhone;

    FragmentTransaction mfragmentTransaction;

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
        {
            finish();
            startActivity(new Intent(getApplicationContext(),SignIn.class));
        }


        drawerLayout =(DrawerLayout) findViewById(R.id.navLayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        //define the header of navigation view
        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.user_name_nav);
        navEmail = (TextView) headerView.findViewById(R.id.Email_nav);
        navPhone = (TextView) headerView.findViewById(R.id.Phone_nav);

        mfragmentTransaction = getSupportFragmentManager().beginTransaction();
        mfragmentTransaction.replace(R.id.container,new HomeFragment());
        mfragmentTransaction.addToBackStack(null);
        mfragmentTransaction.commit();

        setHeaderInfo();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_log_out:
                        finish();
                        SharedPrefManager.getInstance(getApplicationContext()).Logout();
                        break;
                    case R.id.nav_home:
                        mfragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mfragmentTransaction.replace(R.id.container,new HomeFragment());
                        mfragmentTransaction.addToBackStack(null);
                        mfragmentTransaction.commit();
                        break;
                  case R.id.nav_profile:
                        mfragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mfragmentTransaction.replace(R.id.container,new ProfileFragment());
                        mfragmentTransaction.addToBackStack(null);
                        mfragmentTransaction.commit();
                        break;
                    case R.id.nav_settings:
                        mfragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mfragmentTransaction.replace(R.id.container,new SettingsFragment());
                        mfragmentTransaction.addToBackStack(null);
                        mfragmentTransaction.commit();
                        break;
                    case R.id.nav_about_us:
                        mfragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mfragmentTransaction.replace(R.id.container,new AboutUsFragment());
                        mfragmentTransaction.addToBackStack(null);
                        mfragmentTransaction.commit();
                        break;
                    case R.id.nav_contact_us:
                        mfragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mfragmentTransaction.replace(R.id.container,new ContactUsFragment());
                        mfragmentTransaction.addToBackStack(null);
                        mfragmentTransaction.commit();
                        break;
                    case R.id.nav_fav:
                        mfragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mfragmentTransaction.replace(R.id.container,new FavoriteFragment());
                        mfragmentTransaction.addToBackStack(null);
                        mfragmentTransaction.commit();
                        break;
                }
                return true;
            }
        });


        toolbar =(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Planet Of Books");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fragment = new ViewBookFragment();
        mfragmentTransaction = getSupportFragmentManager().beginTransaction();
        mfragmentTransaction.replace(R.id.container,new ViewBookFragment());

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bnv);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId())
                {
                    case R.id.Novels_nav:
                        mfragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mfragmentTransaction.replace(R.id.container,new NovelsFragment());
                        mfragmentTransaction.addToBackStack(null);
                        mfragmentTransaction.commit();
                        break;
                    case R.id.Self_development_nav:
                        mfragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mfragmentTransaction.replace(R.id.container,new SelfDevelopmentFragment());
                        mfragmentTransaction.addToBackStack(null);
                        mfragmentTransaction.commit();
                        break;
                    case R.id.technology_nav:
                        mfragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mfragmentTransaction.replace(R.id.container,new TechnologyFragment());
                        mfragmentTransaction.addToBackStack(null);
                        mfragmentTransaction.commit();
                        break;
                }

                return true;

            }
        });
    }

    public void setHeaderInfo() {
        String name = SharedPrefManager.getInstance(MainActivity.this).getUsers().getName();
        String email = SharedPrefManager.getInstance(MainActivity.this).getUsers().getEmail();
        String phone = SharedPrefManager.getInstance(MainActivity.this).getUsers().getPhone();

        navUsername.setText(name);
        navEmail.setText(email);
        navPhone.setText(phone);
    }


}