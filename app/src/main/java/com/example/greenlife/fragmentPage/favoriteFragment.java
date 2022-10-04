package com.example.greenlife.fragmentPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.greenlife.Model.Tree_data_model;
import com.example.greenlife.R;
import com.example.greenlife.RecylerviewHandler.FavoriteAdapter;
import com.example.greenlife.RecylerviewHandler.Tree_Gride_Adapter;
import com.example.greenlife.RecylerviewHandler.Tree_type_Adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link favoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class favoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference dbReference;
    RecyclerView typeRcv;
    FavoriteAdapter treeAdapter;
    List<Tree_data_model> treeList;

    public favoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static favoriteFragment newInstance(String param1, String param2) {
        favoriteFragment fragment = new favoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        Toolbar toolbar = view.findViewById(R.id.appBarID);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
       try{
           treeList = new ArrayList<>();
           RecyclerView treeGridRcv = view.findViewById(R.id.FavoriteRecyclerViewId);
           FavoriteAdapter treeGrideAdapter = new FavoriteAdapter(getContext(), treeList);
           GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
           treeGridRcv.setLayoutManager(gridLayoutManager);
           treeGridRcv.setAdapter(treeGrideAdapter);
           // fecth data from firebase
           dbReference = FirebaseDatabase.getInstance().getReference("Tree");
           dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   for( DataSnapshot dataSnapshot : snapshot.getChildren()){
                       Tree_data_model tree_data = dataSnapshot.getValue(Tree_data_model.class);
                       System.out.print("tree name :" + tree_data.getName());
                       if(tree_data.getIsFavorite()){
                           treeList.add(tree_data);
                       }
                   }
                   treeGrideAdapter.notifyDataSetChanged();
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
       }catch (Exception ex){
           System.out.println(ex.toString());
       }

        return view;
    }
}