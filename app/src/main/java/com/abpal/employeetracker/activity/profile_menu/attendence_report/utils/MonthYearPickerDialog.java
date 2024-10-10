package com.abpal.employeetracker.activity.profile_menu.attendence_report.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.abpal.tel.R;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthYearPickerDialog extends Dialog {

    private Spinner spinnerMonth;
    private Spinner spinnerYear;
    private Spinner spinnerDay;

    private LinearLayout dayLayout;
    private Button buttonSelect;
    private OnDateSetListener onDateSetListener;
    private int monthValue;
    private int yearValue;

    private int dayValue;
    private boolean isMonthSelected=false;

    public MonthYearPickerDialog(@NonNull Context context,
                                 OnDateSetListener listener,
                                 int monthValue,
                                 int yearValue,
                                 int dayValue,
                                 boolean isMonthSelected) {
        super(context);
        this.onDateSetListener = listener;
        this.monthValue = monthValue;
        this.yearValue = yearValue;
        this.dayValue = dayValue;
        this.isMonthSelected = isMonthSelected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_month_year_picker);

        spinnerMonth = findViewById(R.id.spinner_month);
        spinnerYear = findViewById(R.id.spinner_year);
        buttonSelect = findViewById(R.id.button_select);

        dayLayout = findViewById(R.id.linearLayoutDay);
        spinnerDay = findViewById(R.id.spinner_Day);

        if(isMonthSelected){
           dayLayout.setVisibility(View.GONE);
        }else {
            dayLayout.setVisibility(View.VISIBLE);
        }


        setupSpinners();
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDateSetListener != null) {
                    String selectedMonthName = (String) spinnerMonth.getSelectedItem();
                    int monthIndex = getMonthIndex(selectedMonthName);
                    int selectedYear = (int) spinnerYear.getSelectedItem();
                    int selectedDay = 1;
                    if(isMonthSelected){
                        selectedDay = 1; // by default
                    }else {
                        selectedDay = Integer.parseInt(spinnerDay.getSelectedItem().toString());
                    }

                    onDateSetListener.onDateSet(selectedYear, monthIndex,selectedDay,isMonthSelected);
                    dismiss();
                }
            }
        });
    }

    private void setupSpinners() {

        List<String> daysList = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            daysList.add(i+"");
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, daysList);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(dayAdapter);

        // Setup Month Spinner
        List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            months.add(new DateFormatSymbols().getMonths()[i]);
        }
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(monthAdapter);

        // Setup Year Spinner
        Calendar calendar = Calendar.getInstance();
        List<Integer> years = new ArrayList<>();
        for (int i = calendar.get(Calendar.YEAR) - 10; i <= calendar.get(Calendar.YEAR) + 10; i++) {
            years.add(i);
        }
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        // Set current month and year as selected
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);

        spinnerMonth.setSelection(monthValue);
        spinnerYear.setSelection(years.indexOf(yearValue));
        spinnerDay.setSelection(dayValue-1);
    }

    private int getMonthIndex(String monthName) {
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(monthName)) {
                return i;
            }
        }
        return -1; // Default to January if not found
    }

    public interface OnDateSetListener {
        void onDateSet(int year, int month,int day, boolean ismonthSelected);
    }
}
