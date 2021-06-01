package com.example.semesterprojectnguyentatthangb17dccn563.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.semesterprojectnguyentatthangb17dccn563.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddActivitySpending extends AppCompatActivity {

    private EditText spendingMoney, spendingDes;
    private TextView tvDate;
    private Spinner spSpendingType;
    private Integer spendingNum = new Random().nextInt();
    private String key = Integer.toString(spendingNum);
    private Button btnAdd, btnBack, btnGetDate;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spending);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        init();

        btnGetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(AddActivitySpending.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvDate.setText(dayOfMonth+"/"+(month+1)+"/"+(year));
                    }
                },y,m,d);
                dpd.show();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String email = user.getEmail();
                    reference = FirebaseDatabase.getInstance().getReference()
                            .child("spending").child("Spending" + key);
                    Map<String, Object> data = new HashMap<>();
                    data.put("key", key);
                    data.put("spendMoney", spendingMoney.getText().toString());
                    data.put("spendDate", tvDate.getText().toString());
                    data.put("spendType", spSpendingType.getSelectedItem().toString());
                    data.put("spendDescription", spendingDes.getText().toString());
                    data.put("email", email);
                    reference.setValue(data);
                    String [] time_spilt=tvDate.getText().toString().split("/");
                    int date=Integer.parseInt(time_spilt[0]);
                    int month=Integer.parseInt(time_spilt[1])-1;
                    int year=Integer.parseInt(time_spilt[2]);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(year,month,date);

                    AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                    Intent intent = new Intent(AddActivitySpending.this, AddRecevier.class);
                    intent.putExtra("myAction", "mDoNotify");
                    intent.putExtra("money",spendingMoney.getText().toString());
                    intent.putExtra("date", tvDate.getText().toString());
                    intent.putExtra("type", spSpendingType.getSelectedItem().toString());
                    intent.putExtra("description", spendingDes.getText().toString());
                    intent.putExtra("email", email);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivitySpending.this,
                            0, intent, 0);
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    finish();
                }

            }
        });
    }
    public void init(){
        spendingMoney = findViewById(R.id.edtMoneyAddSpending);
        spendingDes = findViewById(R.id.edtDescriptionAddSpending);
        tvDate = findViewById(R.id.tvDateAddSpending);
        spSpendingType = findViewById(R.id.spSpendingAdd);
        btnAdd = findViewById(R.id.btnAddAddSpending);
        btnBack = findViewById(R.id.btnBackAddSpending);
        btnGetDate = findViewById(R.id.btnGetDateAddSpending);
    }
}