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

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSettingAdapter extends RecyclerView.Adapter<ProfileSettingAdapter.MyViewHolder> {
    Context mContext;
    List<Tree_data_model> listTree;
    public ProfileSettingAdapter(Context mContext, List<Tree_data_model> listCart){
        this.mContext = mContext;
        this.listTree = listCart;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.card_profile_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileSettingAdapter.MyViewHolder holder, int position) {
               System.out.println(listTree.get(position).getImageUrl());
               //Glide.with(mContext).load(listTree.get(position).getImageUrl()).into(holder.imvImageProfile);
               holder.tvTitleProfile.setText(listTree.get(position).getName());
               holder.tvTypeProfile.setText(listTree.get(position).getType());
               holder.tvPriceProfile.setText(listTree.get(position).getPrice() + " $");
    }

    @Override
    public int getItemCount() {
        System.out.println(listTree.size());
        return listTree.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imvImageProfile;
        TextView tvTitleProfile;
        TextView tvPriceProfile;
        TextView tvTypeProfile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imvImageProfile = itemView.findViewById(R.id.imvImageProfileSettingId);
            tvTitleProfile = itemView.findViewById(R.id.tvTitleProfileId);
            tvPriceProfile = itemView.findViewById(R.id.tvPriceProfileId);
            tvTypeProfile= itemView.findViewById(R.id.tvTypeProfileId);
        }
    }

}
