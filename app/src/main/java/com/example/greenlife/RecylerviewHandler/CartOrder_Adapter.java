package com.example.greenlife.RecylerviewHandler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greenlife.ActivityPage.OrderPageActivity;
import com.example.greenlife.Model.CartOrder;
import com.example.greenlife.Model.Tree_data_model;
import com.example.greenlife.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CartOrder_Adapter extends RecyclerView.Adapter<CartOrder_Adapter.MyViewHolder> {
    Context mContext;
    List<CartOrder> listCart;
    public CartOrder_Adapter(Context mContext, List<CartOrder> listCart){
        this.mContext = mContext;
        this.listCart = listCart;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.cardd_order, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartOrder_Adapter.MyViewHolder holder, int position) {
        String image = listCart.get(position).getImageUrl();
        Glide.with(mContext).load(image).into(holder.imvImageOrder);
        holder.tvTitleOrder.setText(listCart.get(position).getTreeName());
        holder.tvAmountOrder.setText(listCart.get(position).getAmount() + "X");
        holder.tvTreePrice.setText(listCart.get(position).getPrice() + " $");
    }

    @Override
    public int getItemCount() {
        System.out.println(listCart.size());
        return listCart.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imvImageOrder;
        TextView tvTitleOrder;
        TextView tvAmountOrder;
        TextView tvTreePrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imvImageOrder = itemView.findViewById(R.id.imvImageOrderId);
            tvTitleOrder = itemView.findViewById(R.id.tvTitleOrderId);
            tvAmountOrder = itemView.findViewById(R.id.tvAmountOrderId);
            tvTreePrice = itemView.findViewById(R.id.tvPriceOrderId);
        }
    }

}
