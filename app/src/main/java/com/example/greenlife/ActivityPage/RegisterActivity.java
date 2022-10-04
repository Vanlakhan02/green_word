package com.example.greenlife.ActivityPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenlife.Model.Tree_data_model;
import com.example.greenlife.Model.UserAccount;
import com.example.greenlife.R;
import com.example.greenlife.fragmentPage.profileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth fireAuth;
    private DatabaseReference dbReference;
    final int REQUEST_CODE_GALLERY = 999;

    EditText edtUsername;
    EditText edtEmail;
    EditText edtPhone;
    EditText edtPassword;
    EditText edtCFPassword;
    Button btnRegister;
    TextView btnBack;
    ImageView imvUserImage;
    ProgressBar progressLoading;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // part of connect to xml language
        edtUsername = findViewById(R.id.edtUsernameRegisId);
        edtEmail = findViewById(R.id.edtEmailRegisId);
        edtPhone = findViewById(R.id.edtPhoneRegisId);
        edtPassword = findViewById(R.id.edtPasswordRegisId);
        edtCFPassword = findViewById(R.id.edtCFPasswordRegisId);
        imvUserImage = findViewById(R.id.imvUserImageId);
        btnRegister = findViewById(R.id.btnRegisterId);
        btnBack = findViewById(R.id.btnBackRegisId);
        progressLoading = findViewById(R.id.progressBarLoadingId);
        // set Event to Button
        imvUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY );
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edtUsername.getText().toString();
                String email = edtEmail.getText().toString();
                String phone = edtPhone.getText().toString();
                String password = edtPassword.getText().toString();
                String cfPassword = edtCFPassword.getText().toString();
              CreateAccount(userName,imageUri,email, phone,password);
            }
        });
       btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             finish();
           }
       });
    }

    private void CreateAccount(String userName,Uri uri,String email,String phone,String password){
        fireAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();

        progressLoading.setVisibility(View.VISIBLE);
        btnRegister.setVisibility(View.GONE);
        if(imageUri == null){
            progressLoading.setVisibility(View.GONE);
            btnRegister.setVisibility(View.VISIBLE);
            Toast.makeText(RegisterActivity.this, "ກະລຸນນເລືອກຮູບດ້ວຍ", Toast.LENGTH_SHORT).show();
        }else{
            fireAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressLoading.setVisibility(View.GONE);
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtention(uri));
                        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
                                        String userId = userAuth.getUid();
                                        String imageUrl = uri.toString();
                                        UserAccount userData = new UserAccount(userId, userName, imageUrl, phone,email);
                                        dbReference.child("Users").child(userName).setValue(userData);
                                        Toast.makeText(RegisterActivity.this, "successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                            }
                        });
                    }else{
                        progressLoading.setVisibility(View.GONE);
                        btnRegister.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(RegisterActivity.this, "can't select the image from your phone", Toast.LENGTH_SHORT);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imvUserImage.setImageBitmap(bitmap);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String getFileExtention(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}