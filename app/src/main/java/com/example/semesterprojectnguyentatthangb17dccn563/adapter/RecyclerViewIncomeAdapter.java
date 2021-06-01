package com.example.semesterprojectnguyentatthangb17dccn563.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semesterprojectnguyentatthangb17dccn563.R;
import com.example.semesterprojectnguyentatthangb17dccn563.activity.EditActivityIncome;
import com.example.semesterprojectnguyentatthangb17dccn563.model.MoneyIncome;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewIncomeAdapter extends RecyclerView.Adapter<RecyclerViewIncomeAdapter.IncomeViewHolder>{
    Context context;
    List<MoneyIncome> listIncome;

    public RecyclerViewIncomeAdapter(Context context) {
        this.context = context;
        listIncome = new ArrayList<>();
    }

    public List<MoneyIncome> getListIncome() {
        return listIncome;
    }

    public void setListIncome(List<MoneyIncome> listIncome) {
        this.listIncome = listIncome;
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.incomecard,parent,false);
        IncomeViewHolder ivh = new IncomeViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position) {
        MoneyIncome mi = listIncome.get(position);
        holder.incomeMoney.setText(mi.getIncomeMoney());
        holder.incomeDescription.setText(mi.getIncomeDescription());
        holder.incomeType.setText(mi.getIncomeType());
        holder.incomeDate.setText(mi.getIncomeDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivityIncome.class);
                intent.putExtra("income",mi);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listIncome.size();
    }

    public class  IncomeViewHolder extends RecyclerView.ViewHolder{
        TextView incomeMoney, incomeDate, incomeType, incomeDescription;
        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            incomeMoney = itemView.findViewById(R.id.tvIncomeCardMoney);
            incomeDate = itemView.findViewById(R.id.tvIncomeCardDate);
            incomeType = itemView.findViewById(R.id.tvIncomeCardType);
            incomeDescription = itemView.findViewById(R.id.tvIncomeCardDescription);
        }
    }
}
