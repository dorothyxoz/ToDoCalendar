package com.example.todocalender.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.todocalender.ui.fragment.CalendarFragment;
import com.example.todocalender.ui.fragment.DoneListFragment;
import com.example.todocalender.ui.fragment.TodoListFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TodoListFragment();
            case 1:
                return new CalendarFragment();
            default:
                return new DoneListFragment();
        }
    }
}
