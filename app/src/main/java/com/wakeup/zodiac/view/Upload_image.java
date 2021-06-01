package com.wakeup.zodiac.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.wakeup.zodiac.R;

import java.io.IOException;

public class Upload_image extends AppCompatActivity {
    public static Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    Button submitimagebtn;
    ImageView uploadimageView;
    Button uploadimagebtn;

    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_activity);
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        init();
        this.submitimagebtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (Upload_image.this.uploadimageView != null) {
                    Upload_image.this.startActivity(new Intent(Upload_image.this, Share.class));
                    Upload_image.this.finish();
                    return;
                }
                Toast.makeText(Upload_image.this.getApplicationContext(), "please put image ", Toast.LENGTH_SHORT).show();
            }
        });
        this.uploadimagebtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.GET_CONTENT");
                Upload_image.this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), Upload_image.this.PICK_IMAGE_REQUEST);
            }
        });
    }

    private void init() {
        this.uploadimagebtn = (Button) findViewById(R.id.uploadimagebtn);
        this.submitimagebtn = (Button) findViewById(R.id.submitimagebtn);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.PICK_IMAGE_REQUEST && resultCode == -1 && data != null && data.getData() != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                this.uploadimageView = (ImageView) findViewById(R.id.galleryimage);
                this.uploadimageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
