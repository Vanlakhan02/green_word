package com.example.greenlife.ActivityPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.greenlife.fragmentPage.HomeFragment;
import com.example.greenlife.R;
import com.example.greenlife.fragmentPage.favoriteFragment;
import com.example.greenlife.fragmentPage.profileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SellingPageAcitvity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_page_acitvity);

        bottomNavigationView = findViewById(R.id.bottomNavigationViewId);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.homepageID);

    }
     HomeFragment homeFragment = new HomeFragment();
     favoriteFragment fvFragment = new favoriteFragment();
     profileFragment pfFragment = new profileFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.homepageID:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
                return true;
            case R.id.favoriteId:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fvFragment).commit();
                return true;
            case R.id.profileId:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pfFragment).commit();
                return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}