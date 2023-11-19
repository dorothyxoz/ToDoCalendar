package com.example.todocalender.ui.fragment;

import android.os.Bundle;
import android.view.*;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;

import com.example.todocalender.R;
import com.example.todocalender.databinding.FragmentTodoListBinding;

public class TodoListFragment extends Fragment {

    private FragmentTodoListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 상단 메뉴 추가
        setHasOptionsMenu(true);
        // 뷰바인딩
        binding = FragmentTodoListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    // 서치바 추가]

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem search = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) search.getActionView();
    }



    // 프래그먼트는 뷰보다 오래 지속 . 프래그먼트의 onDestroyView() 메서드에서 결합 클래스 인스턴스 참조를 정리
    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}