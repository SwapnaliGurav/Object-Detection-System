package com.example.smartshopassistant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartshopassistant.Activity.Homescreen;
import com.example.smartshopassistant.ml.MobilenetV110224Quant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    Button selectBtn, predictBtn, captureBtn,btnFlipcart, btnSearch, btnsearchCompare;
    TextView result;
    ImageView imageView;
    Bitmap bitmap;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permission
        getPermission();

        String[] labels=new String[1001];
        int cnt=0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("labels.txt")));
            String line=bufferedReader.readLine();
            while(line!=null){
                labels[cnt]=line;
                cnt++;
                line=bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        selectBtn = findViewById(R.id.selectBtn);
        captureBtn = findViewById(R.id.captureBtn);
        predictBtn = findViewById(R.id.predictBtn);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);
//        btnFlipcart = findViewById(R.id.btn_flipkart_products);
        btnSearch = findViewById(R.id.btnSearch);
        btnsearchCompare=findViewById(R.id.btnsearchCompare);

//        btnFlipcart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(result.getText().toString().isEmpty())
//                {
//                    Toast.makeText(MainActivity.this, "Product name not found.", Toast.LENGTH_SHORT).show();
//                }else
//                    startActivity(new Intent(MainActivity.this, ProductsActivity.class).putExtra(
//                            "url",  "https://www.flipkart.com/search?q="+ result.getText().toString() +"=search&otracker1=search&marketplace=FLIPKART&as-show=off&as=off"
//
//
//                    ).putExtra(
//                            "url1",  "https://www.amazon.in/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords="+ result.getText().toString()
//                    ));
//            }
//
//        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = String.valueOf(result.getText());
                Intent intent = new Intent(MainActivity.this, Homescreen.class);
                intent.putExtra("result",str);
                startActivity(intent);
            }
        });

        btnsearchCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = String.valueOf(result.getText());
                if (str != null && !str.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, ComparisonAmaFlip.class);
                    intent.putExtra("result", str);
                    startActivity(intent);
                } else {
                    // Handle the case where str is null or empty
                    // For example, you can show a Toast message or log an error
                    Toast.makeText(MainActivity.this, "Please upload/capture image then click on predict Buttom", Toast.LENGTH_LONG).show();
                }
            }
        });



        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,12);
            }
        });

        predictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null) {
                    try {
                        MobilenetV110224Quant model = MobilenetV110224Quant.newInstance(MainActivity.this);

                        // Creates inputs for reference.
                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);

                        bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
                        inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer());

                        // Runs model inference and gets result.
                        MobilenetV110224Quant.Outputs outputs = model.process(inputFeature0);
                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                        result.setText(labels[getMax(outputFeature0.getFloatArray())] + " ");

                        // Releases model resources if no longer used.
                        model.close();
                    } catch (IOException e) {
                        // TODO Handle the exception
                    }
                } else {
                    // Handle the case where bitmap is null
                    // For example, you can show a Toast message or log an error
                    Toast.makeText(MainActivity.this, "Please Upload/Capture Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    int getMax(float[] arr){
        int max=20;
        for(int i=0; i<arr.length; i++){
            if(arr[i] > arr[max]) max=i;
        }
        return max;
    }

    void getPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission("android.permission.CAMERA")!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.CAMERA"}, 11);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==11){
            if(grantResults.length>0){
                if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==10){
            if(data!=null){
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 12) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}