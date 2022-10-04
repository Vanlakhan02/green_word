package com.example.greenlife.fragmentPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.greenlife.ActivityPage.OrderPageActivity;
import com.example.greenlife.Model.Tree_data_model;
import com.example.greenlife.R;
import com.example.greenlife.RecylerviewHandler.Tree_Gride_Adapter;
import com.example.greenlife.RecylerviewHandler.Tree_type_Adapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference dbReference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView typeRcv;
    String[] typeList;
    Tree_type_Adapter treeAdapter;
    List<Tree_data_model> treeList;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // add custom appbar
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar = view.findViewById(R.id.appBarID);
        toolbar.inflateMenu(R.menu.appbar_menu);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


         // handle type of tree that show on the top of page
        typeList = new String[]{"cactus", "sabian", "lapa"};
        typeRcv = view.findViewById(R.id.TypeRecyclerViewId);
        treeAdapter = new Tree_type_Adapter(getContext(), typeList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false );
        typeRcv.setLayoutManager(linearLayoutManager);
        typeRcv.setAdapter(treeAdapter);


        //handle item show on bottom card
        treeList = new ArrayList<>();
        RecyclerView treeGridRcv = view.findViewById(R.id.TreeGridRcvId);
        Tree_Gride_Adapter treeGrideAdapter = new Tree_Gride_Adapter(getContext(), treeList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        treeGridRcv.setLayoutManager(gridLayoutManager);
        treeGridRcv.setAdapter(treeGrideAdapter);
        // fecth data from firebase
        dbReference = FirebaseDatabase.getInstance().getReference("Tree");
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Tree_data_model  tree_data = dataSnapshot.getValue(Tree_data_model.class);
                    System.out.print("tree name :" + tree_data.getName());
                    treeList.add(tree_data);
                }
                treeGrideAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        return  view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.clear(); / / this sentence is useless. You don't need to add it
        inflater.inflate(R.menu.appbar_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cartIconId) {
            Intent intent = new Intent(getContext(), OrderPageActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}