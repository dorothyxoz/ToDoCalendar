package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

public class CalendarFragment extends Fragment {
    private String readDay = null;
    private String str = null;
    private CalendarView calendarView;
    private Button cha_Btn, del_Btn, save_Btn;
    private TextView diaryTextView, textView2, textView3;
    private EditText contextEditText;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        dbHelper = new DatabaseHelper(requireContext());
        database = dbHelper.getWritableDatabase();

        calendarView = view.findViewById(R.id.calendarView);
        diaryTextView = view.findViewById(R.id.diaryTextView);
        save_Btn = view.findViewById(R.id.save_Btn);
        del_Btn = view.findViewById(R.id.del_Btn);
        cha_Btn = view.findViewById(R.id.cha_Btn);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        contextEditText = view.findViewById(R.id.contextEditText);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                diaryTextView.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
                contextEditText.setText("");
                checkDay(year, month, dayOfMonth);
            }
        });

        save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(readDay);
                str = contextEditText.getText().toString();
                textView2.setText(str);
                save_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);
            }
        });

        cha_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 기존 데이터 불러오기
                String existingData = getDataFromDatabase(readDay);

                // 수정 화면 표시
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                contextEditText.setText(existingData);

                // 버튼 상태 업데이트
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                save_Btn.setVisibility(View.VISIBLE); // 수정: 저장 버튼 표시
                save_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 수정된 데이터 저장
                        updateDiary(readDay, contextEditText.getText().toString());

                        // 화면 갱신
                        textView2.setText(contextEditText.getText());
                        contextEditText.setVisibility(View.INVISIBLE);
                        textView2.setVisibility(View.VISIBLE);

                        // 나머지 UI 업데이트 로직 추가...

                        // 버튼 상태 업데이트
                        cha_Btn.setVisibility(View.VISIBLE);
                        del_Btn.setVisibility(View.VISIBLE);
                        save_Btn.setVisibility(View.INVISIBLE);

                        // 저장 버튼 클릭 리스너를 null로 설정하여 중복 호출 방지
                        save_Btn.setOnClickListener(null);
                    }
                });

                // 나머지 UI 업데이트 로직 추가...
            }
        });

        del_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeDiary(readDay);
            }
        });

        return view;
    }

    private void checkDay(int cYear, int cMonth, int cDay) {
        readDay = String.format("%d-%02d-%02d", cYear, cMonth + 1, cDay);

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_DIARY,
                new String[]{DatabaseHelper.COLUMN_CONTENT},
                DatabaseHelper.COLUMN_DATE + " = ?",
                new String[]{readDay},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTENT);

            if (columnIndex != -1) {
                str = cursor.getString(columnIndex);
                textView2.setText(str);

                contextEditText.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);

                save_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
            } else {
                // 커서에서 지정된 열이 없는 경우
                // 상황에 맞게 처리
            }

            cursor.close();
        } else {
            // 커서가 null이거나 데이터가 없는 경우
            // 상황에 맞게 처리
        }
    }

    private String getDataFromDatabase(String date) {
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_DIARY,
                new String[]{DatabaseHelper.COLUMN_CONTENT},
                DatabaseHelper.COLUMN_DATE + " = ?",
                new String[]{date},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTENT);

            if (columnIndex != -1) {
                return cursor.getString(columnIndex);
            } else {
                // 커서에서 지정된 열이 없는 경우
                // 상황에 맞게 처리
            }

            cursor.close();
        } else {
            // 커서가 null이거나 데이터가 없는 경우
            // 상황에 맞게 처리
        }

        return "";
    }

    @SuppressLint("WrongConstant")
    private void removeDiary(String date) {
        int deletedRows = database.delete(
                DatabaseHelper.TABLE_DIARY,
                DatabaseHelper.COLUMN_DATE + " = ?",
                new String[]{date}
        );

        if (deletedRows > 0) {
            // 삭제 성공
        } else {
            // 삭제 실패
        }
    }

    private void updateDiary(String date, String content) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CONTENT, content);

        int updatedRows = database.update(
                DatabaseHelper.TABLE_DIARY,
                values,
                DatabaseHelper.COLUMN_DATE + " = ?",
                new String[]{date}
        );

        if (updatedRows > 0) {
            // 업데이트 성공
        } else {
            // 업데이트 실패
        }
    }

    private void saveDiary(String date) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_DATE, date);
        values.put(DatabaseHelper.COLUMN_CONTENT, contextEditText.getText().toString());

        long newRowId = database.insert(DatabaseHelper.TABLE_DIARY, null, values);
        if (newRowId != -1) {
            // 삽입 성공
        } else {
            // 삽입 실패
        }
    }
}
