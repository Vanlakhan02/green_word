package com.example.greenlife.ActivityPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenlife.Model.CartOrder;
import com.example.greenlife.Model.OrderModel;
import com.example.greenlife.Model.Tree_data_model;
import com.example.greenlife.R;
import com.example.greenlife.RecylerviewHandler.CartOrder_Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPageActivity extends AppCompatActivity {
    private DatabaseReference dbReference;
    FirebaseUser userAuth;
    List<CartOrder> listTree;
    double totalPrice;
    TextView tvTotalPrice;
    CardView btnOrder;
    CartOrder cart;
    int total;
    String title;
    CartOrder_Adapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        RecyclerView cartRcv = findViewById(R.id.cartRecyclerViewId);
        tvTotalPrice = findViewById(R.id.totalPriceOrderId);
        btnOrder = findViewById(R.id.btnOrderId);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            OrderTree();
            }
        });
        listTree = new ArrayList<>();
        cartAdapter = new CartOrder_Adapter(OrderPageActivity.this,listTree);
        cartRcv.setLayoutManager(new LinearLayoutManager(OrderPageActivity.this, LinearLayoutManager.VERTICAL, false));
        cartRcv.setAdapter(cartAdapter);

         userAuth = FirebaseAuth.getInstance().getCurrentUser();
         String userId = userAuth.getUid();
        dbReference = FirebaseDatabase.getInstance().getReference();
        dbReference.child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                    String id = data.get("id").toString();
                    String userId = data.get("userId").toString();
                    title = data.get("treeName").toString();
                    String userImage = data.get("imageUrl").toString();
                    double price = Double.parseDouble(data.get("price").toString());
                    double sumPrice =+ price;
                    int amount = Integer.parseInt(data.get("amount").toString());
                    cart = new CartOrder(id, userId, title,userImage, price, amount);
                    listTree.add(cart);
                    total = listTree.size() + amount - 1;
                    totalPrice = sumPrice * total;
                    Toast.makeText(OrderPageActivity.this, "successfully", Toast.LENGTH_SHORT).show();
                }
                tvTotalPrice.setText(totalPrice + " $");
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void OrderTree(){
        dbReference = FirebaseDatabase.getInstance().getReference();
        String key = dbReference.child("Orders").push().getKey();
        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
        String userId =userAuth.getUid();
        OrderModel ordered = new OrderModel(key,userId,totalPrice,total,listTree );
        dbReference.child("Orders").child(key+ "ID").setValue(ordered);
        listTree.clear();
        tvTotalPrice.setText("");
        Query applesQuery = dbReference.child(title);
        dbReference.child("cart").child(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                snapshot.getRef().removeValue();
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}