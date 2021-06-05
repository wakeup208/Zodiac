package com.wakeup.zodiac.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.wakeup.zodiac.R;
import com.wakeup.zodiac.database.DatabaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NameMeaningActivity extends AppCompatActivity {
    int INDEXn;
    Bitmap bitmap;
    Cursor cursor;
    public DatabaseActivity databaseActivity;
    int i;
    TextView name;
    String[] nameStringArray;
    String[] namechar;
    String[] namemeanchar;
    TextView namemeantext;
    TextView nametext;
    Random random;
    RelativeLayout relativelayoutShare;
    Button sharebutton;
    Uri uri;
    ArrayList<String> word = new ArrayList<>();
    ArrayList<String> wordList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_meaning);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        init();
        this.name.setText(getApplicationContext().getSharedPreferences("MyPref", 0).getString("yourname", null).toUpperCase());
        this.databaseActivity = new DatabaseActivity(this);
        this.random = new Random();
        final String tv1 = getIntent().getExtras().getString("Name");
        setNameMeaning(tv1);
        findViewById(R.id.nameChange).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NameMeaningActivity.this.setNameMeaning(tv1);
            }
        });
        initshre();
    }

    private void init() {
        this.nametext = (TextView) findViewById(R.id.nametext);
        this.namemeantext = (TextView) findViewById(R.id.namemeantext);
        this.name = (TextView) findViewById(R.id.textViewname);
        this.sharebutton = (Button) findViewById(R.id.sharecard);
    }

    private void saveimage() {
        this.relativelayoutShare = (RelativeLayout) findViewById(R.id.ll_container);
        this.bitmap = getBitmapFromView(this.relativelayoutShare);
        SaveImage(this.bitmap);
        this.uri = getImageUri(this, this.bitmap);
    }

    private void initshre() {
        this.sharebutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                NameMeaningActivity.this.saveimage();
                Log.e("Tag", "::" + NameMeaningActivity.this.uri);
                if (NameMeaningActivity.this.bitmap == null) {
                    Toast.makeText(NameMeaningActivity.this.getApplicationContext(), "image not saved", Toast.LENGTH_LONG).show();
                }
                Intent shareIntent = new Intent();
                shareIntent.setAction("android.intent.action.SEND");
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                shareIntent.setDataAndType(NameMeaningActivity.this.uri, NameMeaningActivity.this.getContentResolver().getType(NameMeaningActivity.this.uri));
                shareIntent.putExtra("android.intent.extra.STREAM", NameMeaningActivity.this.uri);
                NameMeaningActivity.this.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
                NameMeaningActivity.this.relativelayoutShare.bringToFront();
                NameMeaningActivity.this.relativelayoutShare.invalidate();
            }
        });
    }

    public void SaveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        int n = new Random().nextInt(10000);
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
        return Uri.parse(MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", (String) null));
    }

    private Bitmap getBitmapFromView(RelativeLayout view) {
        try {
            view.setDrawingCacheEnabled(true);
            view.measure(View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.UNSPECIFIED));
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

    public void setNameMeaning(String name2) {
        this.nametext.setText("");
        this.namemeantext.setText("");
        this.nameStringArray = name2.split("");
        this.wordList = new ArrayList<>(Arrays.asList(this.nameStringArray));
        this.wordList.remove(0);
        AssetManager assetManager = getAssets();
        try {
            this.namemeanchar = assetManager.list("fonts");
            this.namechar = assetManager.list("fonts");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        this.i = 0;
        while (true) {
            int i2 = this.i;
            String[] strArr = this.namemeanchar;
            if (i2 >= strArr.length) {
                break;
            }
            strArr[i2] = strArr[i2].toString();
            String[] strArr2 = this.namechar;
            int i3 = this.i;
            strArr2[i3] = strArr2[i3].replaceAll(".ttf", "");
            this.i++;
        }
        this.i = 0;
        while (this.i < this.wordList.size()) {
            if (!this.wordList.get(this.i).equalsIgnoreCase(" ")) {
                this.nametext.append(this.wordList.get(this.i).concat(" -"));
            }
            this.cursor = this.databaseActivity.getList(this.wordList.get(this.i));
            this.word.clear();
            if (this.cursor.moveToFirst()) {
                do {
                    ArrayList<String> arrayList = this.word;
                    Cursor cursor2 = this.cursor;
                    arrayList.add(cursor2.getString(cursor2.getColumnIndex(DatabaseActivity.KEY_DB_QUOTE)));
                } while (this.cursor.moveToNext());
                this.INDEXn = this.random.nextInt(this.word.size());
                this.namemeantext.append(this.word.get(this.INDEXn));
            }
            this.nametext.append("\n");
            this.namemeantext.append("\n");
            this.i++;
        }
    }
}
