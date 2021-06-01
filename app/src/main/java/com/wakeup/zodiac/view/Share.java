package com.wakeup.zodiac.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.clans.fab.FloatingActionButton;
import com.wakeup.zodiac.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class Share extends AppCompatActivity {
    public static ImageView showrandomimage;
    public static ImageView showuploadimage;
    int[] MyimageArray = new int[10];
    Bitmap bitmap;
    Bitmap bitmap1;
    Bitmap mergedImages;
    ImageView mergeimage;
    private Random rand;
    RelativeLayout relativeLayout;
    FloatingActionButton share;
    Uri uri;

    public Share() {
        int[] iArr = this.MyimageArray;
        iArr[0] = R.drawable.one;
        iArr[2] = R.drawable.second;
        iArr[3] = R.drawable.three;
        iArr[4] = R.drawable.four;
        iArr[9] = R.drawable.ten;
        iArr[8] = R.drawable.nine;
        iArr[6] = R.drawable.eight;
        iArr[7] = R.drawable.five;
        iArr[1] = R.drawable.six;
        iArr[5] = R.drawable.seven;
        this.rand = new Random();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_activity);
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        init();
        initshare();
    }

    private void saveimage() {
        this.relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        this.bitmap = getBitmapFromView(this.relativeLayout);
        Bitmap scaledBitmapFore = Bitmap.createScaledBitmap(this.bitmap, 900, 900, true);
        SaveImage(scaledBitmapFore);
        this.uri = getImageUri(this, scaledBitmapFore);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
        return Uri.parse(MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", (String) null));
    }

    public void SaveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        int n = new Random().nextInt(1);
        File file = new File(myDir, "Image-" + n + ".jpg");
        StringBuilder sb = new StringBuilder();
        sb.append("::");
        sb.append(file);
        Log.e("file", sb.toString());
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromView(RelativeLayout view) {
        try {
            view.setDrawingCacheEnabled(true);
            view.measure(View.MeasureSpec.makeMeasureSpec(900, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache(true);
            Bitmap returnedBitmap = setViewToBitmapImage(view);
            view.setDrawingCacheEnabled(false);
            return returnedBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap setViewToBitmapImage(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(-1);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    private void initshare() {
        this.share.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Share.this.saveimage();
                if (Share.this.bitmap == null && Share.this.bitmap == null) {
                    Toast.makeText(Share.this.getApplicationContext(), "image not saved", Toast.LENGTH_LONG).show();
                }
                Intent shareIntent = new Intent();
                shareIntent.setAction("android.intent.action.SEND");
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                shareIntent.setDataAndType(Share.this.uri, Share.this.getContentResolver().getType(Share.this.uri));
                shareIntent.putExtra("android.intent.extra.STREAM", Share.this.uri);
                Share.this.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
                Share.this.relativeLayout.bringToFront();
                Share.this.relativeLayout.invalidate();
                Share.this.share.bringToFront();
                Share.this.share.invalidate();
            }
        });
    }

    private void init() {
        showrandomimage = (ImageView) findViewById(R.id.showrandomimage);
        showuploadimage = (ImageView) findViewById(R.id.showuploadimage);
        this.mergeimage = (ImageView) findViewById(R.id.mergeimage);
        this.share = (FloatingActionButton) findViewById(R.id.shareit);
        showrandomimage.setImageResource(getRandArrayElement());
        showuploadimage.setImageBitmap(Upload_image.bitmap);
        this.bitmap = ((BitmapDrawable) showrandomimage.getDrawable()).getBitmap();
        this.bitmap1 = ((BitmapDrawable) showuploadimage.getDrawable()).getBitmap();
        this.mergedImages = createSingleImageFromMultipleImages(Bitmap.createScaledBitmap(this.bitmap, 700, 550, true), Bitmap.createScaledBitmap(this.bitmap1, 700, 550, true));
        this.mergeimage.setImageBitmap(this.mergedImages);
    }

    private Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage) {
        Bitmap result = Bitmap.createBitmap(firstImage.getWidth() + secondImage.getWidth(), firstImage.getHeight(), firstImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(secondImage, (float) firstImage.getWidth(), 0.0f, (Paint) null);
        return result;
    }

    public int getRandArrayElement() {
        int[] iArr = this.MyimageArray;
        return iArr[this.rand.nextInt(iArr.length)];
    }
}
