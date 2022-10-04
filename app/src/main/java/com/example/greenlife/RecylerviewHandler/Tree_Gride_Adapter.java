package com.example.greenlife.RecylerviewHandler;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;

import com.bumptech.glide.request.target.Target;
import com.example.greenlife.ActivityPage.DetailPageActivity;
import com.example.greenlife.Model.CartOrder;
import com.example.greenlife.Model.Tree_data_model;
import com.example.greenlife.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree_Gride_Adapter extends RecyclerView.Adapter<Tree_Gride_Adapter.MyViewHolder>{
    int numberOrder = 0;
    boolean isFavorite;
    List<Tree_data_model> tree_data_List;
    Context mContext;
    public Tree_Gride_Adapter(Context mContext, List<Tree_data_model> tree_data_List){
        this.mContext = mContext;
        this.tree_data_List = tree_data_List;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.card_tree_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Tree_Gride_Adapter.MyViewHolder holder, int position) {


        holder.tv_tree_name.setText(tree_data_List.get(position).getName());
        holder.tv_tree_price.setText(tree_data_List.get(position).getPrice() + " $");
        holder.tv_tree_rate.setText(tree_data_List.get(position).getType());
        holder.btnAddFavorite.setChecked(tree_data_List.get(position).getIsFavorite());

        holder.btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.btnAddFavorite.isChecked()){
                    isFavorite = true;
                }else{
                    isFavorite = false;
                }
                AddToFavorite(tree_data_List.get(position).getId(),tree_data_List.get(position).getName(),tree_data_List.get(position).getImageUrl(),tree_data_List.get(position).getType(),
                        tree_data_List.get(position).getPrice(),tree_data_List.get(position).getLocation(),tree_data_List.get(position).getDescription(),isFavorite);
            }
        });


        holder.btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder + 1;
             AddToCart(tree_data_List.get(position).getName(),tree_data_List.get(position).getImageUrl(),tree_data_List.get(position).getType(),
                     tree_data_List.get(position).getPrice(),tree_data_List.get(position).getLocation(),tree_data_List.get(position).getDescription(),numberOrder, position);
            }
        });
        Glide.with(mContext).load(tree_data_List.get(position).getImageUrl()).listener(new RequestListener<Drawable>() {

            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressLoad.setVisibility(View.GONE);
                return false;
            }


            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressLoad.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.imv_tree_Image);
       holder.treeCardItem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(mContext, DetailPageActivity.class);
               mContext.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return tree_data_List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView treeCardItem;
        ImageView imv_tree_Image;
        TextView tv_tree_name;
        TextView tv_tree_price;
        TextView tv_tree_rate;
        ImageView btnAddCart;
        ToggleButton btnAddFavorite;
        ProgressBar progressLoad;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            treeCardItem = itemView.findViewById(R.id.treeCardItemId);
            imv_tree_Image = itemView.findViewById(R.id.ImageViewTreeShowId);
            tv_tree_name = itemView.findViewById(R.id.tvTreeNameId);
            tv_tree_price = itemView.findViewById(R.id.tvTreePriceId);
            tv_tree_rate = itemView.findViewById(R.id.tvTreeRateId);
            progressLoad = itemView.findViewById(R.id.progress_loadingImg_id);
            btnAddCart = itemView.findViewById(R.id.btnAddToCartId);
            btnAddFavorite = itemView.findViewById(R.id.btnAddToFavoriteId);
        }
    }

    public void AddToCart(String treeName, String imageUrl, String treeType, double price, String treeLocation, String description, int amount, int position){
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
        String userId = userAuth.getUid();

        CartOrder cart = new CartOrder(userId + position,userId,treeName,imageUrl, price, amount);
        String key = dbReference.child("cart").push().getKey();
        dbReference.child("cart").child(treeName).setValue(cart);
    }
    public void AddToFavorite(String userId,String treeName, String imageUrl, String treeType, double price, String treeLocation, String description,boolean isFavorite){
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
        Tree_data_model treeUpdate = new Tree_data_model(userId,treeName, imageUrl, treeType, price, treeLocation,description,isFavorite);
        Map<String, Object> postValue = treeUpdate.toMap();
        Map<String, Object> childUpdate = new HashMap<>();
        childUpdate.put("/Tree/"+treeName, postValue);
        dbReference.updateChildren(childUpdate);
    }
}
