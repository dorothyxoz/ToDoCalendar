// ToDoListFragment.java
package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.TodoAdapter;
import com.example.myapplication.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class ToDoListFragment extends Fragment {
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private List<TodoItem> todoList;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

        dbHelper = new DatabaseHelper(requireContext());
        database = dbHelper.getReadableDatabase();

        recyclerView = view.findViewById(R.id.todoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        todoList = new ArrayList<>();
        todoAdapter = new TodoAdapter(todoList, this);
        recyclerView.setAdapter(todoAdapter);

        loadTodoData();

        return view;
    }

    private void loadTodoData() {
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_DIARY,
                new String[]{DatabaseHelper.COLUMN_DATE, DatabaseHelper.COLUMN_CONTENT},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTENT));
                boolean isChecked = false;

                TodoItem todoItem = new TodoItem(date, content, isChecked);
                todoList.add(todoItem);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        todoAdapter.notifyDataSetChanged();
    }

    public void editTodoItem(TodoItem todoItem) {
        showEditDialog(todoItem);
    }

    public void deleteTodoItem(TodoItem todoItem) {
        removeTodoItemFromDatabase(todoItem);
        todoList.remove(todoItem);
        todoAdapter.notifyDataSetChanged();
    }

    private void removeTodoItemFromDatabase(TodoItem todoItem) {
        SQLiteDatabase writeDatabase = dbHelper.getWritableDatabase();
        writeDatabase.delete(
                DatabaseHelper.TABLE_DIARY,
                DatabaseHelper.COLUMN_DATE + " = ? AND " + DatabaseHelper.COLUMN_CONTENT + " = ?",
                new String[]{todoItem.getDate(), todoItem.getContent()}
        );
        writeDatabase.close();
    }

    private void showEditDialog(final TodoItem todoItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Todo");
        final EditText editText = new EditText(requireContext());
        editText.setText(todoItem.getContent());
        builder.setView(editText);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String editedContent = editText.getText().toString();
                updateTodoItemInDatabase(todoItem, editedContent);
                // 수정된 부분: TodoItem 클래스에서 setContent 메서드를 사용하여 content 필드 수정
                todoItem.setContent(editedContent);
                todoAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing if canceled
            }
        });

        builder.show();
    }

    private void updateTodoItemInDatabase(TodoItem todoItem, String editedContent) {
        SQLiteDatabase writeDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CONTENT, editedContent);

        writeDatabase.update(
                DatabaseHelper.TABLE_DIARY,
                values,
                DatabaseHelper.COLUMN_DATE + " = ? AND " + DatabaseHelper.COLUMN_CONTENT + " = ?",
                new String[]{todoItem.getDate(), todoItem.getContent()}
        );

        writeDatabase.close();
    }
}
