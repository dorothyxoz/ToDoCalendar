// TodoAdapter.java
package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.fragment.ToDoListFragment;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<TodoItem> todoList;
    private ToDoListFragment todoListFragment;

    public TodoAdapter(List<TodoItem> todoList, ToDoListFragment todoListFragment) {
        this.todoList = todoList;
        this.todoListFragment = todoListFragment;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        TodoItem todoItem = todoList.get(position);
        holder.bind(todoItem);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private TextView textView;
        private ImageButton editBtn;
        private ImageButton deleteBtn;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.todoCheckBox);
            textView = itemView.findViewById(R.id.todoTextView);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }

        public void bind(TodoItem todoItem) {
            checkBox.setChecked(todoItem.isChecked());
            textView.setText(todoItem.getContent());

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    todoItem.setChecked(isChecked);
                }
            });

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 수정 버튼 클릭 시 ToDoListFragment의 editTodoItem 메서드 호출
                    todoListFragment.editTodoItem(todoItem);
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 삭제 버튼 클릭 시 ToDoListFragment의 deleteTodoItem 메서드 호출
                    todoListFragment.deleteTodoItem(todoItem);
                }
            });
        }
    }
}
