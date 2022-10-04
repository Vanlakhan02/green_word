package com.example.greenlife.RecylerviewHandler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenlife.R;

import java.util.List;

public class Tree_type_Adapter extends RecyclerView.Adapter<Tree_type_Adapter.MyViewHolder> {
   String[] typeList;
   Context mContext;

   public Tree_type_Adapter(Context mContext, String[] typeList){
       this.mContext = mContext;
       this.typeList = typeList;
   }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.card_type, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Tree_type_Adapter.MyViewHolder holder, int position) {
        holder.tvTypeName.setText(typeList[position]);
      holder.type_card.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
      });
    }

    @Override
    public int getItemCount() {
        return typeList.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView type_card;
        TextView tvTypeName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type_card = itemView.findViewById(R.id.Type_CardViewId);
            tvTypeName = itemView.findViewById(R.id.tvTypeNameCardId);
        }
    }
}
