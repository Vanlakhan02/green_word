package com.example.greenlife.RecylerviewHandler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greenlife.Model.Tree_data_model;
import com.example.greenlife.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
     boolean isFavorite;
    List<Tree_data_model> treeList;
     Context mContext;
     public FavoriteAdapter(Context mContext, List<Tree_data_model> treeList){
         this.mContext = mContext;
         this.treeList = treeList;
     }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.card_tree_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.MyViewHolder holder, int position) {
        holder.tv_treeName_Favorite.setText(treeList.get(position).getName());
        holder.tv_price_Favorite.setText(treeList.get(position).getPrice() + " $");
        holder.tv_rate_Favorite.setText(treeList.get(position).getType());
         Glide.with(mContext).load(treeList.get(position).getImageUrl()).into(holder.imv_ImageFavorite);
        holder.btnAddFavorite.setChecked(treeList.get(position).getIsFavorite());
        holder.btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.btnAddFavorite.isChecked()){
                    isFavorite = true;
                }else{
                    isFavorite = false;
                }
                AddToFavorite(treeList.get(position).getId(),treeList.get(position).getName(),treeList.get(position).getImageUrl(),treeList.get(position).getType(),
                        treeList.get(position).getPrice(),treeList.get(position).getLocation(),treeList.get(position).getDescription(),isFavorite);
            }
        });
     }

    @Override
    public int getItemCount() {
        return treeList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
         CardView cardTree_Favorite;
        ImageView imv_ImageFavorite;
        TextView tv_treeName_Favorite;
        TextView tv_price_Favorite;
        TextView tv_rate_Favorite;
        ToggleButton btnAddFavorite;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_ImageFavorite = itemView.findViewById(R.id.ImageViewTreeShowId);
            tv_treeName_Favorite = itemView.findViewById(R.id.tvTreeNameId);
            tv_price_Favorite = itemView.findViewById(R.id.tvTreePriceId);
            tv_rate_Favorite = itemView.findViewById(R.id.tvTreeRateId);
            btnAddFavorite = itemView.findViewById(R.id.btnAddToFavoriteId);
        }
    }
    public void AddToFavorite(String userId, String treeName, String imageUrl, String treeType, double price, String treeLocation, String description,boolean isFavorite){
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
        Tree_data_model treeUpdate = new Tree_data_model(userId,treeName, imageUrl, treeType, price, treeLocation,description,isFavorite);
        Map<String, Object> postValue = treeUpdate.toMap();
        Map<String, Object> childUpdate = new HashMap<>();
        childUpdate.put("/Tree/"+treeName, postValue);
        dbReference.updateChildren(childUpdate);
    }

}
