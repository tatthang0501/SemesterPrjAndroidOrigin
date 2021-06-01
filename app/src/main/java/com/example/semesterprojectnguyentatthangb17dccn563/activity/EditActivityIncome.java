package com.example.semesterprojectnguyentatthangb17dccn563.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semesterprojectnguyentatthangb17dccn563.R;
import com.example.semesterprojectnguyentatthangb17dccn563.model.MoneyIncome;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EditActivityIncome extends AppCompatActivity {
    private EditText edtMoney, edtDes;
    private TextView tvDate;
    private Button btnGetDate, btnUpdate, btnDelete, btnBack;
    private Spinner spIncome;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income);
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
                DatePickerDialog dpd = new DatePickerDialog(EditActivityIncome.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvDate.setText(dayOfMonth+"/"+(month+1)+"/"+(year));
                    }
                },y,m,d);
                dpd.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Intent intent = getIntent();
        if(intent.getSerializableExtra("income") != null){
            final MoneyIncome moneyIncome = (MoneyIncome) intent.getSerializableExtra("income");
            edtMoney.setText(moneyIncome.getIncomeMoney());
            edtDes.setText(moneyIncome.getIncomeDescription());
            tvDate.setText(moneyIncome.getIncomeDate());
            reference = FirebaseDatabase.getInstance().getReference().child("income")
                    .child("Income" + moneyIncome.getKey());
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoneyIncome editIncome = new MoneyIncome();
                    editIncome.setKey(moneyIncome.getKey());
                    editIncome.setEmail(moneyIncome.getEmail());
                    editIncome.setIncomeDate(tvDate.getText().toString());
                    editIncome.setIncomeDescription(edtDes.getText().toString());
                    editIncome.setIncomeType(spIncome.getSelectedItem().toString());
                    editIncome.setIncomeMoney(edtMoney.getText().toString());
                    Toast.makeText(EditActivityIncome.this, editIncome.getIncomeDate() + " "
                            + editIncome.getIncomeMoney() + " " + editIncome.getEmail() + editIncome.getIncomeDescription(), Toast.LENGTH_SHORT).show();
                    reference.setValue(editIncome).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditActivityIncome.this, "Update success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditActivityIncome.this, "Failed to update value", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditActivityIncome.this, "Delete success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditActivityIncome.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
    private void init(){
        edtMoney = findViewById(R.id.edtMoneyEditIncome);
        edtDes = findViewById(R.id.edtDescriptionEditIncome);
        tvDate = findViewById(R.id.tvDateEditIncome);
        btnGetDate = findViewById(R.id.btnGetDateEditIncome);
        btnUpdate = findViewById(R.id.btnUpdateEditIncome);
        btnDelete = findViewById(R.id.btnDeleteEditIncome);
        btnBack = findViewById(R.id.btnBackEditIncome);
        spIncome = findViewById(R.id.spIncomeEdit);
    }
}