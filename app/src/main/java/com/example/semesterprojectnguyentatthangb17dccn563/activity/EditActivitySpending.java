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
import com.example.semesterprojectnguyentatthangb17dccn563.model.MoneySpending;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EditActivitySpending extends AppCompatActivity {
    private EditText edtMoney, edtDes;
    private TextView tvDate;
    private Button btnGetDate, btnUpdate, btnDelete, btnBack;
    private Spinner spSpending;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_spending);
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
                DatePickerDialog dpd = new DatePickerDialog(EditActivitySpending.this, new DatePickerDialog.OnDateSetListener() {
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
        if(intent.getSerializableExtra("spending") != null){
            final MoneySpending moneySpending = (MoneySpending) intent.getSerializableExtra("spending");
            MoneySpending ms = (MoneySpending) intent.getSerializableExtra("spending");
            System.out.println("aloaloalaoalaoaoaoaoaoaoaoaolao");
            System.out.println("aloaloalaoalaoaoaoaoaoaoaoaolao" + " " +ms.getSpendMoney() + " " + ms.getEmail()+ " "  + ms.getSpendDate()+ " "  + ms.getSpendDescription() + " " + ms.getSpendType());
            edtMoney.setText(moneySpending.getSpendMoney());
            edtDes.setText(moneySpending.getSpendDescription());
            tvDate.setText(moneySpending.getSpendDate());
            reference = FirebaseDatabase.getInstance().getReference().child("spending")
                    .child("Spending" + moneySpending.getKey());
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoneySpending editSpending = new MoneySpending();
                    editSpending.setKey(moneySpending.getKey());
                    editSpending.setEmail(moneySpending.getEmail());
                    editSpending.setSpendDate(tvDate.getText().toString());
                    editSpending.setSpendDescription(edtDes.getText().toString());
                    editSpending.setSpendType(spSpending.getSelectedItem().toString());
                    editSpending.setSpendMoney(edtMoney.getText().toString());

                    reference.setValue(editSpending).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditActivitySpending.this, "Update success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditActivitySpending.this, "Failed to update value", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(EditActivitySpending.this, "Delete success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditActivitySpending.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
    private void init(){
        edtMoney = findViewById(R.id.edtMoneyEditSpending);
        edtDes = findViewById(R.id.edtDescriptionEditSpending);
        tvDate = findViewById(R.id.tvDateEditSpending);
        btnGetDate = findViewById(R.id.btnGetDateEditSpending);
        btnUpdate = findViewById(R.id.btnUpdateEditSpending);
        btnDelete = findViewById(R.id.btnDeleteEditSpending);
        btnBack = findViewById(R.id.btnBackEditSpending);
        spSpending = findViewById(R.id.spSpendingEdit);
    }
}