package com.example.deals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Audi extends AppCompatActivity{
    String Storage_Path = "images";
    String Database_Path ="allimages";
    Button ChooseButton, UploadButton;
    EditText car_name,car_location,car_reviews,car_pay;
    ImageView SelectImage;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_audi);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        ChooseButton = (Button)findViewById(R.id.btn_post_choose);
        UploadButton = (Button)findViewById(R.id.btn_post_submit);
        car_name = (EditText)findViewById(R.id.post_title);
        car_reviews=findViewById(R.id.post_reviews);
        car_location=findViewById(R.id.post_location);
        car_pay=findViewById(R.id.post_pay);
        SelectImage = (ImageView)findViewById(R.id.post_car);
        progressDialog = new ProgressDialog(Audi.this);        // Adding click listener to Choose image button.
        ChooseButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        // Creating intent.
        Intent intent = new Intent();

        // Setting intent type as image to select image from phone storage.
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

        }
        });


        // Adding click listener to Upload image button.
        UploadButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        // Calling method to upload selected image on Firebase storage.
        UploadImageFileToFirebaseStorage();

        }
        });
        }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

        FilePathUri = data.getData();

        try {

        // Getting selected image into Bitmap.
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

        // Setting up bitmap selected image into ImageView.
        SelectImage.setImageBitmap(bitmap);

        // After selecting image change choose button above text.
        ChooseButton.setText("Image Selected");

        }
        catch (IOException e) {

        e.printStackTrace();
        }
        }
        }

// Creating Method to get the selected image file Extension from File Path URI.
public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

        }

// Creating UploadImageFileToFirebaseStorage method to upload image on storage.
public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

        // Setting progressDialog Title.
        progressDialog.setTitle("Image is Uploading...");

        // Showing progressDialog.
        progressDialog.show();

        // Creating second StorageReference.
        StorageReference storageReference2nd = storageReference.child(Storage_Path +
        System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

        // Adding addOnSuccessListener to second StorageReference.
        storageReference2nd.putFile(FilePathUri)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
@Override
public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
        while (!urlTask.isSuccessful());
        Uri downloadUrl = urlTask.getResult();
        // Getting image name from EditText and store into string variable.
        String savecurrenttime,savecurrentdate;
        Calendar calfordate=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate=dateFormat.format(calfordate.getTime());
        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=currenttime.format(calfordate.getTime());
        String productrandomkey=savecurrentdate+savecurrenttime;
        String Tempcarname = car_name.getText().toString().trim();
        String Temppay = car_pay.getText().toString().trim();
        String Templocation = car_location.getText().toString().trim();
        String Tempreviews = car_reviews.getText().toString().trim();
        // Hiding the progressDialog after done uploading.
        progressDialog.dismiss();

        // Showing toast message after done uploading.
        Toast.makeText(getApplicationContext(), "Uploaded Successfully ", Toast.LENGTH_LONG).show();
        Intent inte=new Intent(Audi.this,StartActivity.class);
        startActivity(inte);
        finish();

@SuppressWarnings("VisibleForTests")
                            Entity imageUploadInfo = new Entity(downloadUrl.toString(),Tempcarname,Templocation,Temppay,Tempreviews);

                                    // Getting image upload ID.


                                    // Adding image upload id s child element into databaseReference.
                                    databaseReference.child(productrandomkey).setValue(imageUploadInfo);
                                    }
                                    })
                                    // If something goes wrong .
                                    .addOnFailureListener(new OnFailureListener() {
@Override
public void onFailure(@NonNull Exception exception) {

        // Hiding the progressDialog.
        progressDialog.dismiss();

        // Showing exception erro message.
        Toast.makeText(Audi.this,
        exception.getMessage(),
        Toast.LENGTH_LONG).show();
        }
        })

        // On progress change upload time.
        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
@Override
public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

        // Setting progressDialog Title.
        progressDialog.setTitle("Uploading...");

        }
        });
        }
        else {

        Toast.makeText(Audi.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
        }

        }