package com.example.greenlife.ActivityPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.greenlife.Model.OrderModel;
import com.example.greenlife.Model.Tree_data_model;
import com.example.greenlife.R;
import com.example.greenlife.RecylerviewHandler.HistoryAdapter;
import com.example.greenlife.RecylerviewHandler.ProfileSettingAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryPageMainActivity extends AppCompatActivity {
    private DatabaseReference dbReference;
    RecyclerView histRcv;
    HistoryAdapter histAdapter;
    List<OrderModel> listOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page_main);
        listOrder = new ArrayList<>();
        histRcv = findViewById(R.id.HistoryRecyclerViewId);
        histAdapter = new HistoryAdapter(HistoryPageMainActivity.this, listOrder);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryPageMainActivity.this,LinearLayoutManager.VERTICAL, false );
        histRcv.setLayoutManager(linearLayoutManager);
        histRcv.setAdapter(histAdapter);
        dbReference = FirebaseDatabase.getInstance().getReference("Orders");
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot dataSnapshot : snapshot.getChildren()){
                    OrderModel data = dataSnapshot.getValue(OrderModel.class);
                    System.out.print("tree name :" + data.getId());
                        listOrder.add(data);
                }
                histAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}