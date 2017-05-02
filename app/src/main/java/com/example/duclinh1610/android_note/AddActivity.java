package com.example.duclinh1610.android_note;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends ActionBarActivity implements View.OnClickListener{
    private TextView tvDatePresent, tvAlarm;
    private LinearLayout layoutAlarm;
    private ImageView ivClose;
    private Spinner spnDate, spnTime;

    //Lấy ngày giờ hiện tại
    Calendar ngayGioHienTai = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(R.drawable.icon_note);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        init();
        layoutAlarm.setVisibility(View.INVISIBLE);
        tvDatePresent.setText(showCalendar());
        tvAlarm.setOnClickListener(this);
        ivClose.setOnClickListener(this);

        showSpinner();
    }

    public void init(){
        tvDatePresent = (TextView) findViewById(R.id.tvDatePresent);
        tvAlarm = (TextView) findViewById(R.id.tvAlarm);
        layoutAlarm = (LinearLayout) findViewById(R.id.layoutAlarm);
        ivClose = (ImageView) findViewById(R.id.ivClose);
        spnDate = (Spinner) findViewById(R.id.spnDate);
        spnTime = (Spinner) findViewById(R.id.spnTime);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

// Ham lay thoi gian hien tai
    public String showCalendar(){
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd hh:mm");

        return ft.format(date);
    }

//Ham hien thi Spinner
    public void showSpinner(){
        ArrayAdapter<String> adapterDate = new ArrayAdapter<String>(AddActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.arrDate));
        adapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(AddActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.arrTime));
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnDate.setAdapter(adapterDate);
        spnDate.setOnItemSelectedListener(new spinnerDateListener());

        spnTime.setAdapter(adapterTime);
        spnTime.setOnItemSelectedListener(new spinnerTimeListener());
    }
// chon cac item trong spinner
    class spinnerDateListener implements Spinner.OnItemSelectedListener {
        @Override
        public void onItemSelected(final AdapterView parent, View v, final int position,
                                   long id) {
            // TODO Auto-generated method stub
            Toast.makeText(parent.getContext(), "The day is :" +
                    parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Other..."))
            {
                //setSpnDate();
                DatePickerDialog date = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //Gán ngày tháng năm lên EditText
                                Toast.makeText(AddActivity.this, dayOfMonth + "/" + (month+1) + "/" + year,
                                        Toast.LENGTH_LONG).show();
                            }
                        },
                        //Định dạng ngày tháng năm
                        ngayGioHienTai.get(Calendar.YEAR),
                        ngayGioHienTai.get(Calendar.MONTH),
                        ngayGioHienTai.get(Calendar.DAY_OF_MONTH));
                date.show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView parent) {
            // TODO Auto-generated method stub
            // Do nothing.
        }

    }

    class spinnerTimeListener implements Spinner.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Other..."))
                setSpnTime();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
// Hàm DatePicker
    void setSpnDate(){
        DatePickerDialog date = new DatePickerDialog(AddActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //Gán ngày tháng năm lên EditText
                        Toast.makeText(AddActivity.this, dayOfMonth + "/" + (month+1) + "/" + year,
                                Toast.LENGTH_LONG).show();
                        //edtNgay.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },
                //Định dạng ngày tháng năm
                ngayGioHienTai.get(Calendar.YEAR),
                ngayGioHienTai.get(Calendar.MONTH),
                ngayGioHienTai.get(Calendar.DAY_OF_MONTH));
        date.show();
    }
    //Ham TimePicker
    void setSpnTime() {
        TimePickerDialog time = new TimePickerDialog(AddActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Gán giờ lên EditText
                        Toast.makeText(AddActivity.this, hourOfDay + ":" + minute,
                                Toast.LENGTH_LONG).show();
                        //edtGio.setText(hourOfDay + ":" + minute);
                    }
                },
                //Định dạng giờ phút
                ngayGioHienTai.get(Calendar.HOUR_OF_DAY),
                ngayGioHienTai.get(Calendar.MINUTE), true);
        time.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAlarm:
                layoutAlarm.setVisibility(View.VISIBLE);
                break;
            case R.id.ivClose:
                layoutAlarm.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
