package com.example.deeplinking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.signed.Signature;
import com.cloudinary.android.signed.SignatureProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView imageView;
    MyAdapter adapter;
    EditText et;
    String[] str;
    ArrayList arrayList;
    Cloudinary cloudinary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.iv);
        MediaManager.init(this);

                recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this,ContentsCollection.getContents());
        recyclerView.setAdapter(adapter);
        System.out.println(ContentsCollection.getContents().get(0).getUrl());
        et = findViewById(R.id.et);
        arrayList = new ArrayList();



        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,1);
                //Toast.makeText(this,)*/
               // geo();

            }
        });



    }

    private void geo() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(et.getText().toString(), 1);
            Address address = addresses.get(0);
            double longitude = address.getLongitude();
            double latitude = address.getLatitude();

            System.out.println(longitude + "\n" + latitude);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null){
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            get(selectedImage);


        }
    }

    private void get(Uri filepath){
        try {


            String requestId = MediaManager.get().upload(filepath)



                    .callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {

                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            Double progress = (double) bytes/totalBytes;
                            // post progress to app UI (e.g. progress bar, notification)
                            System.out.println("progress : " + requestId + "\n" + progress);
                        }
                        @Override
                        public void onSuccess(String requestId, Map resultData) {

                            System.out.println("success" + requestId + resultData);

                            try {

                                System.out.println(resultData.get("secure_url"));
                                arrayList.add(resultData.get("secure_url"));



                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println(e.getMessage());
                            }

                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            System.out.println("error" + requestId + "\n" + error.getDescription());

                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {

                        }

                    })
                    .dispatch();

            System.out.println(requestId);
        }
        catch (Exception e){
            System.out.println("error : " + e.getMessage());
        }

    }

    public void btnshow(View view) {

        System.out.println(arrayList);
    }
    /*public void loadPic()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1)
        {
            Uri selectedImage=data.getData();

            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            assert selectedImage != null;
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            File f = new File(picturePath);
            String imageName = f.getName();
        }
    }*/
}
