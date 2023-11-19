package com.example.todocalender.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.todocalender.R;
import com.example.todocalender.adapter.ViewPagerAdapter;
import com.example.todocalender.databinding.ActivityMainBinding;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

//import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {
    //private ViewPager2 viewPager;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ToDo");


        // 뷰 바인딩
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 탭 설정
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 탭이 선택 되었을 때
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 탭이 선택되지 않은 상태로 변경 되었을 때
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 이미 선택된 탭이 다시 선택 되었을 때
            }
        });


        //뷰페이저에 어댑터 연결
        binding.pager.setAdapter(new ViewPagerAdapter(this));
        // 탭과 뷰페이저 연결
        new TabLayoutMediator(binding.tabLayout, binding.pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("TodoList");
                        break;
                    case 1:
                        tab.setText("Calendar");
                        break;
                    case 2:
                        tab.setText("DoneList");
                        break;
                }
            }
        }).attach();
    }
}