package com.wakeup.zodiac.fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.wakeup.zodiac.R;
import com.wakeup.zodiac.presenter.GetHoroscope;
import com.wakeup.zodiac.view.DetailActivity;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.Random;

public class MonthlyFragment extends Fragment {
    public static ImageView cardimagemonthly;
    public static TextView cardsignmonthly;
    static String data1;
    public static TextView monthlydate;
    public static TextView monthlyhoroscopetext;
    public static TextView tvContentmonthly;
    Bitmap bitmap;
    Context context;
    private FloatingActionButton fabCopymonth;
    private FloatingActionButton fabTTSmonth;
    private FloatingActionButton fabWPmonth;
    private FloatingActionButton fabsharemonth;
    private FloatingActionMenu fammonth;
    String horoscopesign = DetailActivity.horoscope;
    public ProgressDialog progressDialog;
    RelativeLayout relativeLayout;
    View rootview;
    Bitmap scaledBitmapFore;
    private TextToSpeech tts;
    Uri uri;
    String url = "month/";

    public static void setdata1(String horoscopetext) {
        data1 = horoscopetext;
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootview = inflater.inflate(R.layout.fragment_monthly, container, false);
        this.context = getActivity();
        initView();
        GetHoroscope.getdetail(this.url, this.horoscopesign, getActivity());
        initts();
        initwp();
        initshare();
        initcopy();
        return this.rootview;
    }

    public void initView() {
        monthlydate = (TextView) this.rootview.findViewById(R.id.monthlydate);
        monthlyhoroscopetext = (TextView) this.rootview.findViewById(R.id.monthlyhoroscopeText);
        monthlyhoroscopetext.setMovementMethod(new ScrollingMovementMethod());
        cardimagemonthly = (ImageView) this.rootview.findViewById(R.id.img_signmonthly);
        cardsignmonthly = (TextView) this.rootview.findViewById(R.id.cardsignmonthly);
        tvContentmonthly = (TextView) this.rootview.findViewById(R.id.tv_contentmonthly);
        cardimagemonthly.setImageResource(DetailActivity.image);
        cardsignmonthly.setText(DetailActivity.horoscope);
        this.fammonth = (FloatingActionMenu) this.rootview.findViewById(R.id.fab_menumonth);
        this.fabTTSmonth = (FloatingActionButton) this.rootview.findViewById(R.id.fab1month);
        this.fabWPmonth = (FloatingActionButton) this.rootview.findViewById(R.id.fab2month);
        this.fabsharemonth = (FloatingActionButton) this.rootview.findViewById(R.id.fab3month);
        this.fabCopymonth = (FloatingActionButton) this.rootview.findViewById(R.id.fab4month);
    }

    private void initfab() {
        this.fammonth.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {

            @Override // com.github.clans.fab.FloatingActionMenu.OnMenuToggleListener
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    MonthlyFragment.this.showToast("Menu is opened");
                } else {
                    MonthlyFragment.this.showToast("Menu is closed");
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showToast(String msg) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show();
    }

    private void initshare() {
        this.fabsharemonth.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myIntent = new Intent("android.intent.action.SEND");
                myIntent.setType("text/plain");
                myIntent.putExtra("android.intent.extra.SUBJECT", "Your body is here");
                myIntent.putExtra("android.intent.extra.TEXT", MonthlyFragment.data1);
                MonthlyFragment.this.startActivity(Intent.createChooser(myIntent, "Share using"));
            }
        });
    }

    private void initcopy() {
        this.fabCopymonth.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Context context = MonthlyFragment.this.context;
                MonthlyFragment.this.getContext();
                ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("textdata", MonthlyFragment.data1));
                Toast.makeText(MonthlyFragment.this.getContext(), "Copied successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initts() {
        this.tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {

            public void onInit(int status) {
                if (status == 0) {
                    int ttsLang = MonthlyFragment.this.tts.setLanguage(Locale.US);
                    if (ttsLang == -1 || ttsLang == -2) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                    return;
                }
                Toast.makeText(MonthlyFragment.this.getContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
            }
        });
        this.fabTTSmonth.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.i("TTS", "button clicked: " + MonthlyFragment.data1);
                if (MonthlyFragment.this.tts.speak(MonthlyFragment.data1, 0, null) == -1) {
                    Log.e("TTS", "Error in converting Text to Speech!");
                }
            }
        });
    }

    private void initwp() {
        this.fabWPmonth.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                MonthlyFragment.this.saveimage();
                Bitmap bitmap = MonthlyFragment.this.scaledBitmapFore;
                Intent whatsappIntent = new Intent("android.intent.action.SEND");
                whatsappIntent.setType("image/*");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra("android.intent.extra.STREAM", MonthlyFragment.this.uri);
                try {
                    MonthlyFragment.this.startActivity(whatsappIntent);
                } catch (ActivityNotFoundException e) {
                }
                MonthlyFragment.this.relativeLayout.bringToFront();
                MonthlyFragment.this.relativeLayout.invalidate();
            }
        });
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // android.support.v4.app.Fragment
    public void onDetach() {
        super.onDetach();
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        TextToSpeech textToSpeech = this.tts;
        if (textToSpeech != null) {
            textToSpeech.stop();
            this.tts.shutdown();
        }
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void saveimage() {
        this.relativeLayout = (RelativeLayout) this.rootview.findViewById(R.id.rl_fshare1);
        this.bitmap = getBitmapFromView(this.relativeLayout);
        SaveImage(Bitmap.createScaledBitmap(this.bitmap, 350, 350, true));
        this.uri = getImageUri(getContext(), this.bitmap);
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
}
