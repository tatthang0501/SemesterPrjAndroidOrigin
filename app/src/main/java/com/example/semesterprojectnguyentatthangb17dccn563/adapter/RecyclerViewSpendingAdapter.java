package com.example.semesterprojectnguyentatthangb17dccn563.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semesterprojectnguyentatthangb17dccn563.R;
import com.example.semesterprojectnguyentatthangb17dccn563.activity.EditActivityIncome;
import com.example.semesterprojectnguyentatthangb17dccn563.activity.EditActivitySpending;
import com.example.semesterprojectnguyentatthangb17dccn563.model.MoneyIncome;
import com.example.semesterprojectnguyentatthangb17dccn563.model.MoneySpending;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewSpendingAdapter extends RecyclerView.Adapter<RecyclerViewSpendingAdapter.SpendingViewHolder> {
    Context context;
    List<MoneySpending> listSpending;

    public RecyclerViewSpendingAdapter(Context context) {
        this.context = context;
        listSpending = new ArrayList<>();
    }

    public List<MoneySpending> getListSpending() {
        return listSpending;
    }

    public void setListSpending(List<MoneySpending> listSpending) {
        this.listSpending = listSpending;
    }

    @NonNull
    @Override
    public SpendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.spendingcard,parent,false);
        SpendingViewHolder svh = new SpendingViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull SpendingViewHolder holder, int position) {
        MoneySpending ms = listSpending.get(position);
        holder.spendingMoney.setText(ms.getSpendMoney());
        holder.spendingDescription.setText(ms.getSpendDescription());
        holder.spendingType.setText(ms.getSpendType());
        holder.spendingDate.setText(ms.getSpendDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivitySpending.class);
                Toast.makeText(context, ms.getSpendMoney() + " " + ms.getEmail()+ " "  + ms.getSpendDate()+ " "  + ms.getSpendDescription() + " " + ms.getSpendType(), Toast.LENGTH_SHORT).show();
                intent.putExtra("spending",ms);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSpending.size();
    }

    public class  SpendingViewHolder extends RecyclerView.ViewHolder{
        TextView spendingMoney, spendingDate, spendingType, spendingDescription;
        public SpendingViewHolder(@NonNull View itemView) {
            super(itemView);
            spendingMoney = itemView.findViewById(R.id.tvSpendingCardMoney);
            spendingDate = itemView.findViewById(R.id.tvSpendingCardDate);
            spendingType = itemView.findViewById(R.id.tvSpendingCardType);
            spendingDescription = itemView.findViewById(R.id.tvSpendingCardDescription);
        }
    }
}
