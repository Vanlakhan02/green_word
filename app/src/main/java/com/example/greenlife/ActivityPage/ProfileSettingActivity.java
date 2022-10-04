package com.example.greenlife.ActivityPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.greenlife.Model.Tree_data_model;
import com.example.greenlife.R;
import com.example.greenlife.RecylerviewHandler.ProfileSettingAdapter;
import com.example.greenlife.RecylerviewHandler.Tree_type_Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileSettingActivity extends AppCompatActivity {
    private DatabaseReference dbReference;
    RecyclerView treeRcv;
    ProfileSettingAdapter treeAdapter;
    List<Tree_data_model> treeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
        treeList = new ArrayList<>();
        treeRcv = findViewById(R.id.ProfileRecyclerViewId);
        treeAdapter = new ProfileSettingAdapter(ProfileSettingActivity.this, treeList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProfileSettingActivity.this,LinearLayoutManager.VERTICAL, false );
        treeRcv.setLayoutManager(linearLayoutManager);
        treeRcv.setAdapter(treeAdapter);
         String userId = userAuth.getUid();
         String key = getIntent().getStringExtra("key");
         String path = "";
         if(key.equals("Owner")){
             path = "Tree";
         }else if(key.equals("Cart")){
             path = "cart";
         }else if(key.equals("History")){
             path = "order";
         }

        dbReference = FirebaseDatabase.getInstance().getReference(path);
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Tree_data_model tree_data = dataSnapshot.getValue(Tree_data_model.class);
                    System.out.print("tree name :" + tree_data.getId());
                    if(userId.equals(tree_data.getId())){
                        treeList.add(tree_data);
                    }
                }
                treeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}