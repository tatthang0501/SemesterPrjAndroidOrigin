package com.example.semesterprojectnguyentatthangb17dccn563.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.semesterprojectnguyentatthangb17dccn563.R;
import com.example.semesterprojectnguyentatthangb17dccn563.model.MoneyIncome;

import java.util.ArrayList;
import java.util.List;

public class StaticIncome extends AppCompatActivity {
    private AnyChartView anyChartView;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_income);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        ArrayList<MoneyIncome> listIncome = (ArrayList<MoneyIncome>) intent.getSerializableExtra("listIncome");
        ArrayList<String> listType = new ArrayList<>();
        ArrayList<Integer> listMoney = new ArrayList<>();
        for(MoneyIncome mi : listIncome){
            if(!listType.contains(mi.getIncomeType())){
                listType.add(mi.getIncomeType());
            }
        }
        for(String s : listType){
            listMoney.add(0);
        }
        for(MoneyIncome mi : listIncome){
            if(listType.contains(mi.getIncomeType())){
                int temp = listMoney.get(listType.indexOf(mi.getIncomeType()));
                temp += Integer.parseInt(mi.getIncomeMoney());
                listMoney.set(listType.indexOf(mi.getIncomeType()),temp);
            }
        }

        anyChartView = findViewById(R.id.incomeChartView);
        btnBack = findViewById(R.id.btnStaticIncomeBack);
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for(int i = 0; i < listMoney.size(); i++){
            dataEntries.add(new ValueDataEntry(listType.get(i), listMoney.get(i)));
        }
        pie.data(dataEntries);
        anyChartView.setChart(pie);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}