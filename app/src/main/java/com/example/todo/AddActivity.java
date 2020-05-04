package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.structure.Tekma;

import org.greenrobot.eventbus.EventBus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "AddActivity";
    private MyApp App;
    private DatePickerDialog.OnDateSetListener mStartDateSetListener;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        App = (MyApp)getApplication();

        final Button mAddButton = findViewById(R.id.saveButton);
        final EditText mStartDate = findViewById(R.id.startDateText);
        final EditText mEndDate = findViewById(R.id.endDateText);
        final EditText mTitle = findViewById(R.id.titleText);
        final EditText mDescription = findViewById(R.id.descriptionText);
        final CheckBox mImportant = findViewById(R.id.importantCheckBox);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTitle.getText().toString().matches("")) {
                    boolean important = false;
                    if (mImportant.isChecked()) {
                        important = true;
                    }
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("d-M-yyyy");
                    LocalDate startDate = null;
                    LocalDate endDate = null;
                    if(!mStartDate.getText().toString().matches("")){
                        startDate = LocalDate.parse(mStartDate.getText().toString(), df);
                    }
                    if(!mEndDate.getText().toString().matches("")){
                        endDate = LocalDate.parse(mEndDate.getText().toString(), df);
                    }
                    Tekma tekma = new Tekma(mTitle.getText().toString(), mDescription.getText().toString(), startDate, endDate, important);
                    App.setCurrentTekma(tekma);
                    Toast.makeText(getApplicationContext(), "Successfuly added", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().postSticky(new Tekma(mTitle.getText().toString(), mDescription.getText().toString(), startDate, endDate, important));
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        mStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renderCalendar(mStartDateSetListener);
            }
        });

        mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renderCalendar(mEndDateSetListener);
            }
        });

        mStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = day + "-" + month + "-" + year;
                mStartDate.setText(date);
            }
        };

        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = day + "-" + month + "-" + year;
                mEndDate.setText(date);
            }
        };
    }

    protected void renderCalendar(DatePickerDialog.OnDateSetListener mListener){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                AddActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
