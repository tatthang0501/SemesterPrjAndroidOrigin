package com.example.semesterprojectnguyentatthangb17dccn563.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semesterprojectnguyentatthangb17dccn563.R;
import com.example.semesterprojectnguyentatthangb17dccn563.activity.AddActivityIncome;
import com.example.semesterprojectnguyentatthangb17dccn563.activity.AddActivitySpending;
import com.example.semesterprojectnguyentatthangb17dccn563.activity.StaticIncome;
import com.example.semesterprojectnguyentatthangb17dccn563.activity.StaticSpending;
import com.example.semesterprojectnguyentatthangb17dccn563.adapter.RecyclerViewIncomeAdapter;
import com.example.semesterprojectnguyentatthangb17dccn563.adapter.RecyclerViewSpendingAdapter;
import com.example.semesterprojectnguyentatthangb17dccn563.model.MoneyIncome;
import com.example.semesterprojectnguyentatthangb17dccn563.model.MoneySpending;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpendingFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SpendingFragment extends Fragment {
    private RecyclerViewSpendingAdapter adapter;
    private RecyclerView rev;
    private FloatingActionButton fabSpending;
    private TextView totalSpending, dateFrom, dateTo;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private Button btnGetDateFrom, btnGetDateTo, btnFilter, btnGetAll, btnViewStatic;
    List<MoneySpending> listSpending;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SpendingFragment() {
        // Required empty public constructor
    }

    public List<MoneySpending> getListSpending() {
        return listSpending;
    }

    public void setListSpending(List<MoneySpending> listSpending) {
        this.listSpending = listSpending;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSpendingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpendingFragment newInstance(String param1, String param2) {
        SpendingFragment fragment = new SpendingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_spending, container, false);
        rev = v.findViewById(R.id.revSpending);
        fabSpending = v.findViewById(R.id.fabSpending);
        totalSpending = v.findViewById(R.id.totalSpending);
        dateFrom = v.findViewById(R.id.tvDateFromSpending);
        dateTo = v.findViewById(R.id.tvDateToSpending);
        btnGetDateFrom = v.findViewById(R.id.btnGetDateFromSpending);
        btnGetDateTo = v.findViewById(R.id.btnGetDateToSpending);
        btnFilter = v.findViewById(R.id.btnSpendingFilter);
        btnViewStatic = v.findViewById(R.id.btnSpendingViewStatic);
        btnGetAll = v.findViewById(R.id.btnSpendingGetAll);
        adapter = new RecyclerViewSpendingAdapter(this.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL,false);
        rev.setLayoutManager(manager);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("spending");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSpending = new ArrayList<MoneySpending>();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = user.getEmail();
                float total = 0;
                for(DataSnapshot item : snapshot.getChildren()){
                    if(item.child("email").getValue().toString().equals(email)){
                        MoneySpending spending = item.getValue(MoneySpending.class);
                        total += Float.parseFloat(spending.getSpendMoney());
                        System.out.println("Tien day " + total);
                        listSpending.add(spending);
                    }
                }
                totalSpending.setText("Tổng tiền đã chi: " + total);
                adapter.setListSpending(listSpending);
                rev.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference().child("spending");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listSpending = new ArrayList<MoneySpending>();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String email = user.getEmail();
                        float total = 0;
                        for(DataSnapshot item : snapshot.getChildren()){
                            if(item.child("email").getValue().toString().equals(email)){
                                MoneySpending spending = item.getValue(MoneySpending.class);
                                total += Float.parseFloat(spending.getSpendMoney());
                                System.out.println("Tien day " + total);
                                listSpending.add(spending);

                            }
                        }
                        totalSpending.setText("Tổng tiền đã chi: " + total);
                        adapter.setListSpending(listSpending);
                        rev.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        fabSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddActivitySpending.class);
                startActivity(intent);
            }
        });

        btnGetDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateTo.setText(dayOfMonth+"/"+(month+1)+"/"+(year));
                    }
                },y,m,d);
                dpd.show();
            }
        });

        btnGetDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateFrom.setText(dayOfMonth+"/"+(month+1)+"/"+(year));
                    }
                },y,m,d);
                dpd.show();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dFrom = dateFrom.getText().toString();
                String dTo = dateTo.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date dateF = format.parse(dFrom);
                    Date dateT = format.parse(dTo);
                    if(dateF.after(dateT)){
                        Toast.makeText(getContext(), "Date (from) must be before date (to)!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<MoneySpending> listFilter = new ArrayList<>();
                    for(MoneySpending mi : listSpending){
                        Date temp = format.parse(mi.getSpendDate());
                        if((temp.after(dateF) && temp.before(dateT)) || (temp.after(dateF) && temp.equals(dateT))
                                || (temp.equals(dateF) && temp.equals(dateT)) || (temp.equals(dateF) && temp.before(dateT))){
                            listFilter.add(mi);
                        }
                    }
                    adapter.setListSpending(listFilter);
                    rev.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btnViewStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StaticSpending.class);
                intent.putExtra("listSpending", (Serializable) listSpending);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}