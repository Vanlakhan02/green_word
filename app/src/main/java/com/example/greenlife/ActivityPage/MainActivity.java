package com.example.greenlife.ActivityPage;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.greenlife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    // firebase initialize
    FirebaseAuth fireAuth;

    EditText edtEmailSign;
    EditText edtPasswordSign;
    Button btnSignUp;
    TextView btnRegisterSign;
    ProgressBar progressLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // part of connect to xml code----------------------
        edtEmailSign = findViewById(R.id.EdtEmailSignId);
        edtPasswordSign = findViewById(R.id.EdtPasswordSignId);
        btnSignUp = findViewById(R.id.BtnSignUpId);
        btnRegisterSign = findViewById(R.id.BtnRegisterSignId);
        progressLoading = findViewById(R.id.ProgessBarSignId);

        // part of set event to button---------------------------
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailSign = edtEmailSign.getText().toString();
                String passwordSign = edtPasswordSign.getText().toString();
                FireBaseAuth(emailSign, passwordSign);

            }
        });

        btnRegisterSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
              startActivity(intent);
            }
        });
    }

    //part of firebase
    public void FireBaseAuth(String email, String password){
        fireAuth = FirebaseAuth.getInstance();
        btnSignUp.setVisibility(View.GONE);
        progressLoading.setVisibility(View.VISIBLE);
        fireAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressLoading.setVisibility(View.GONE);
                    btnSignUp.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(MainActivity.this, SellingPageAcitvity.class);
                    startActivity(intent);

                }else{
                    progressLoading.setVisibility(View.VISIBLE);
                    Log.d(TAG, "Log-in is Failure");
                }
            }
        });
    }
}