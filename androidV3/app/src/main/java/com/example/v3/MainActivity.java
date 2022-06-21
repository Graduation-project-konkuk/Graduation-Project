package com.example.v3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    LinearLayout home_layout;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        SettingListener(); // 리스너 등록

    }

    private void init(){
        home_layout = findViewById(R.id.home_layout);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_layout, new ExerciseFragment())
                .commit();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void SettingListener() {
        bottomNavigationView.setOnItemSelectedListener(new
                TabSelectedListener());
    }

    class TabSelectedListener implements NavigationBarView.OnItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab_home: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_layout, new ExerciseFragment())
                            .commit();
                    return true;
                }
                case R.id.tab_plan: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_layout, new PlanFragment())
                            .commit();
                    return true;
                }
                case R.id.tab_user:{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_layout, new UserFragment())
                            .commit();
                    return true;
                }
            }
            return false;
        }
    }
}