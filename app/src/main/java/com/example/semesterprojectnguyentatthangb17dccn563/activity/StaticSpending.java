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
import com.example.semesterprojectnguyentatthangb17dccn563.model.MoneySpending;

import java.util.ArrayList;
import java.util.List;

public class StaticSpending extends AppCompatActivity {
    private AnyChartView anyChartView;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_spending);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        ArrayList<MoneySpending> listSpending = (ArrayList<MoneySpending>) intent.getSerializableExtra("listSpending");
        ArrayList<String> listType = new ArrayList<>();
        ArrayList<Integer> listMoney = new ArrayList<>();
        for(MoneySpending ms : listSpending){
            if(!listType.contains(ms.getSpendType())){
                listType.add(ms.getSpendType());
            }
        }
        for(String s : listType){
            listMoney.add(0);
        }
        for(MoneySpending ms : listSpending){
            if(listType.contains(ms.getSpendType())){
                int temp = listMoney.get(listType.indexOf(ms.getSpendType()));
                temp += Integer.parseInt(ms.getSpendMoney());
                listMoney.set(listType.indexOf(ms.getSpendType()),temp);
            }
        }

        anyChartView = findViewById(R.id.spendingChartView);
        btnBack = findViewById(R.id.btnStaticSpendingBack);
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