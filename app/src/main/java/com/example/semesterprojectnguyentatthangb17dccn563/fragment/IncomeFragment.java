package com.example.semesterprojectnguyentatthangb17dccn563.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.SearchManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semesterprojectnguyentatthangb17dccn563.R;
import com.example.semesterprojectnguyentatthangb17dccn563.activity.AddActivityIncome;
import com.example.semesterprojectnguyentatthangb17dccn563.activity.StaticIncome;
import com.example.semesterprojectnguyentatthangb17dccn563.adapter.RecyclerViewIncomeAdapter;
import com.example.semesterprojectnguyentatthangb17dccn563.model.MoneyIncome;
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
 * Use the {@link IncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncomeFragment extends Fragment {
    private RecyclerViewIncomeAdapter adapter;
    private RecyclerView rev;
    private FloatingActionButton fabIncome;
    private TextView totalIncome, dateFrom, dateTo;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private Button btnGetDateFrom, btnGetDateTo, btnFilter, btnGetAll, btnViewStatic;
    List<MoneyIncome> listIncome;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IncomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddIncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncomeFragment newInstance(String param1, String param2) {
        IncomeFragment fragment = new IncomeFragment();
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
        v = inflater.inflate(R.layout.fragment_income, container, false);
        rev = v.findViewById(R.id.revIncome);
        fabIncome = v.findViewById(R.id.fabIncome);
        totalIncome = v.findViewById(R.id.totalIncome);
        dateFrom = v.findViewById(R.id.tvDateFromIncome);
        dateTo = v.findViewById(R.id.tvDateToIncome);
        btnGetDateFrom = v.findViewById(R.id.btnGetDateFromIncome);
        btnGetDateTo = v.findViewById(R.id.btnGetDateToIncome);
        btnFilter = v.findViewById(R.id.btnIncomeFilter);
        btnViewStatic = v.findViewById(R.id.btnIncomeViewStatic);
        btnGetAll = v.findViewById(R.id.btnIncomeGetAll);
        adapter = new RecyclerViewIncomeAdapter(this.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL,false);
        rev.setLayoutManager(manager);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("income");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listIncome = new ArrayList<MoneyIncome>();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = user.getEmail();
                float total = 0;
                for(DataSnapshot item : snapshot.getChildren()){
                    if(item.child("email").getValue().toString().equals(email)){
                        MoneyIncome income = item.getValue(MoneyIncome.class);
                        total += Float.parseFloat(income.getIncomeMoney());
                        System.out.println("Tien day " + total);
                        listIncome.add(income);

                    }
                }
                totalIncome.setText("Tổng tiền đã thu: " + total);
                adapter.setListIncome(listIncome);
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
                databaseReference = database.getReference().child("income");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listIncome = new ArrayList<MoneyIncome>();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String email = user.getEmail();
                        float total = 0;
                        for(DataSnapshot item : snapshot.getChildren()){
                            if(item.child("email").getValue().toString().equals(email)){
                                MoneyIncome income = item.getValue(MoneyIncome.class);
                                total += Float.parseFloat(income.getIncomeMoney());
                                System.out.println("Tien day " + total);
                                listIncome.add(income);

                            }
                        }
                        totalIncome.setText("Tổng tiền đã thu: " + total);
                        adapter.setListIncome(listIncome);
                        rev.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        fabIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddActivityIncome.class);
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
                    List<MoneyIncome> listFilter = new ArrayList<>();
                    for(MoneyIncome mi : listIncome){
                        Date temp = format.parse(mi.getIncomeDate());
                        if((temp.after(dateF) && temp.before(dateT)) || (temp.after(dateF) && temp.equals(dateT))
                                || (temp.equals(dateF) && temp.equals(dateT)) || (temp.equals(dateF) && temp.before(dateT))){
                            listFilter.add(mi);
                        }
                    }
                    adapter.setListIncome(listFilter);
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
                Intent intent = new Intent(getContext(), StaticIncome.class);
                intent.putExtra("listIncome", (Serializable) listIncome);
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