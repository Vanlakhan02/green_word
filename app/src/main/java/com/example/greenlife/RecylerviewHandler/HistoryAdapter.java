package com.example.greenlife.RecylerviewHandler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenlife.Model.CartOrder;
import com.example.greenlife.Model.OrderModel;
import com.example.greenlife.R;
import java.util.List;

public class HistoryAdapter  extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    Context mContext;
    List<OrderModel> listOrder;
    public HistoryAdapter(Context mContext, List<OrderModel> listOrder){
      this.mContext = mContext;
      this.listOrder = listOrder;
    }
    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.card_history, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {
      holder.tvPriceHist.setText(listOrder.get(position).getPrice() + "$");
    }

    @Override
    public int getItemCount() {
        System.out.println(listOrder.size());
        return listOrder.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvPriceHist;
        TextView tvTitleHist;
        TextView tvAmountHist;
        TextView tvTypeHist;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPriceHist = itemView.findViewById(R.id.cardPriceId);
            tvTitleHist = itemView.findViewById(R.id.tvTitleHistoryId);
            tvAmountHist = itemView.findViewById(R.id.tvAmountHistoryId);
            tvTypeHist = itemView.findViewById(R.id.tvTypeHistoryId);
        }
    }

}
