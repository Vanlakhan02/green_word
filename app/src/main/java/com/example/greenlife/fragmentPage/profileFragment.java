package com.example.greenlife.fragmentPage;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.greenlife.ActivityPage.AddPageActivity;
import com.example.greenlife.ActivityPage.HistoryPageMainActivity;
import com.example.greenlife.ActivityPage.ProfileSettingActivity;
import com.example.greenlife.Model.Tree_data_model;
import com.example.greenlife.Model.UserAccount;
import com.example.greenlife.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final int REQUEST_CODE_GALLERY = 999;
    private DatabaseReference dbReference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    UserAccount user_data;
    String imageUrl;
    String userName;

    public profileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profileFragment newInstance(String param1, String param2) {
        profileFragment fragment = new profileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar toolbar = view.findViewById(R.id.appBarID);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        // part of connection to xml file
        CircleImageView imvImageUser = view.findViewById(R.id.imvUserImageProfileId);
        TextView tvUserName = view.findViewById(R.id.tvUsernameProfileID);
        CardView cardPostFv = view.findViewById(R.id.postCardPfID);
        CardView cardOwner = view.findViewById(R.id.cardOwnerProfileId);
        CardView cardOrderCart = view.findViewById(R.id.cardOrderProfileId);
        CardView cardHistory = view.findViewById(R.id.cardHistoryProfileId);

        cardOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfileSettingActivity.class);
                intent.putExtra("key","Owner");
                startActivity(intent);
            }
        });
        cardOrderCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfileSettingActivity.class);
                intent.putExtra("key","Cart");
                startActivity(intent);
            }
        });
        cardHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HistoryPageMainActivity.class);
                startActivity(intent);
            }
        });

        RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_baseline_wifi_protected_setup_24).error(R.drawable.ic_baseline_error_24);
        // work with firebase
        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
        dbReference = FirebaseDatabase.getInstance().getReference();
        String userId = userAuth.getUid();
        dbReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    user_data = dataSnapshot.getValue(UserAccount.class);
                    if(userId.equals(user_data.getUserId())){
                        imageUrl = user_data.getUserImage();
                        userName = user_data.getUserName();
                        Glide.with(getContext()).load(imageUrl).apply(options).into(imvImageUser);
                        tvUserName.setText(userName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        cardPostFv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddPageActivity.class);

                startActivity(intent);
            }
        });
        return view;
    }

}