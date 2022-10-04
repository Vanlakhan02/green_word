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
import android.widget.Toast;

import com.example.greenlife.Model.Tree_data_model;
import com.example.greenlife.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddPageActivity extends AppCompatActivity {
     private DatabaseReference dbReference;


     EditText edtNamePost;
     EditText edtTypePost;
     EditText edtPricePost;
     EditText edtLocationPost;
     EditText edtDescriptionPost;
     ImageView imvImagePost;
     Button btnCancelPost;
     Button btnSavePost;
     private Uri imageUri;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);

        //part of connection to xml file
        edtNamePost = (EditText) findViewById(R.id.edtNamePostId);
        edtTypePost = (EditText) findViewById(R.id.edtTypePostId);
        edtPricePost = (EditText) findViewById(R.id.edtPricePostId);
        edtLocationPost = (EditText) findViewById(R.id.edtLocationPostId);
        edtDescriptionPost = (EditText) findViewById(R.id.edtDescriptionPostId);
        imvImagePost = (ImageView) findViewById(R.id.imvImagePostId);
        btnCancelPost = (Button) findViewById(R.id.btnCancelPostId);
        btnSavePost = (Button) findViewById(R.id.btnSavePostId);

        //part of setting event to button
        imvImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(AddPageActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY );
            }
        });
        btnCancelPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSavePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double price = Double.parseDouble(edtPricePost.getText().toString());
             AddDataToFirebase(edtNamePost.getText().toString(),imageUri, edtTypePost.getText().toString(),price, edtLocationPost.getText().toString(),edtDescriptionPost.getText().toString());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(AddPageActivity.this, "can't select the image from your phone", Toast.LENGTH_SHORT);
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
                imvImagePost.setImageBitmap(bitmap);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AddDataToFirebase(String treeName, Uri uri,String treeType, double price,String treeLocation,String description){
        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtention(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       String userId = userAuth.getUid();
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        List<Tree_data_model> commentData =new ArrayList<>();
                        Tree_data_model tree_data = new Tree_data_model(userId, treeName, uri.toString(),treeType,price,treeLocation,description, false);
                        mDatabase.child("Tree").child(treeName).setValue(tree_data);
                        Toast.makeText(AddPageActivity.this, "successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });
    }

    private String getFileExtention(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}