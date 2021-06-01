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

public class YearlyFragment extends Fragment {
    public static ImageView cardimageyearly;
    public static TextView cardsignyearly;
    static String data1;
    public static TextView tvContentyearly;
    public static TextView yearlydate;
    public static TextView yearlyhoroscopetext;
    Bitmap bitmap;
    Context context;
    private FloatingActionButton fabCopyyear;
    private FloatingActionButton fabTTSyear;
    private FloatingActionButton fabWPyear;
    private FloatingActionButton fabshareyear;
    private FloatingActionMenu famyear;
    String horoscopesign = DetailActivity.horoscope;
    ProgressDialog progressDialog;
    RelativeLayout relativeLayout;
    View rootview;
    Bitmap scaledBitmapFore;
    private TextToSpeech tts;
    Uri uri;
    String url = "year/";

    public static void setdata1(String horoscopetext) {
        data1 = horoscopetext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootview = inflater.inflate(R.layout.fragment_yearly, container, false);
        this.context = getActivity();
        initView();
        GetHoroscope.getdetail(this.url, this.horoscopesign, getActivity());
        initts();
        initwp();
        initshare();
        initcopy();
        return this.rootview;
    }

    @Override
    public void onDestroy() {
        TextToSpeech textToSpeech = this.tts;
        if (textToSpeech != null) {
            textToSpeech.stop();
            this.tts.shutdown();
        }
        super.onDestroy();
    }

    private void initView() {
        yearlydate = (TextView) this.rootview.findViewById(R.id.yearlydate);
        yearlyhoroscopetext = (TextView) this.rootview.findViewById(R.id.yearlyhoroscopeText);
        yearlyhoroscopetext.setMovementMethod(new ScrollingMovementMethod());
        cardimageyearly = (ImageView) this.rootview.findViewById(R.id.img_signyearly);
        cardsignyearly = (TextView) this.rootview.findViewById(R.id.cardsignyearly);
        tvContentyearly = (TextView) this.rootview.findViewById(R.id.tv_contentyearly);
        cardimageyearly.setImageResource(DetailActivity.image);
        cardsignyearly.setText(DetailActivity.horoscope);
        this.famyear = (FloatingActionMenu) this.rootview.findViewById(R.id.fab_menuyear);
        this.fabTTSyear = (FloatingActionButton) this.rootview.findViewById(R.id.fab1year);
        this.fabWPyear = (FloatingActionButton) this.rootview.findViewById(R.id.fab2year);
        this.fabshareyear = (FloatingActionButton) this.rootview.findViewById(R.id.fab3year);
        this.fabCopyyear = (FloatingActionButton) this.rootview.findViewById(R.id.fab4year);
    }

    private void initfab() {
        this.famyear.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {

            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    YearlyFragment.this.showToast("Menu is opened");
                } else {
                    YearlyFragment.this.showToast("Menu is closed");
                }
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show();
    }

    private void initshare() {
        this.fabshareyear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myIntent = new Intent("android.intent.action.SEND");
                myIntent.setType("text/plain");
                myIntent.putExtra("android.intent.extra.SUBJECT", "Your body is here");
                myIntent.putExtra("android.intent.extra.TEXT", YearlyFragment.data1);
                YearlyFragment.this.startActivity(Intent.createChooser(myIntent, "Share using"));
            }
        });
    }

    private void initcopy() {
        this.fabCopyyear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Context context = YearlyFragment.this.context;
                YearlyFragment.this.getContext();
                ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("textdata", YearlyFragment.data1));
                Toast.makeText(YearlyFragment.this.getContext(), "Copied successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initts() {
        this.tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {

            public void onInit(int status) {
                if (status == 0) {
                    int ttsLang = YearlyFragment.this.tts.setLanguage(Locale.US);
                    if (ttsLang == -1 || ttsLang == -2) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                    return;
                }
                Toast.makeText(YearlyFragment.this.getContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
            }
        });
        this.fabTTSyear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.i("TTS", "button clicked: " + YearlyFragment.data1);
                if (YearlyFragment.this.tts.speak(YearlyFragment.data1, 0, null) == -1) {
                    Log.e("TTS", "Error in converting Text to Speech!");
                }
            }
        });
    }

    private void initwp() {
        this.fabWPyear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                YearlyFragment.this.saveimage();
                Bitmap bitmap = YearlyFragment.this.scaledBitmapFore;
                Intent whatsappIntent = new Intent("android.intent.action.SEND");
                whatsappIntent.setType("image/*");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra("android.intent.extra.STREAM", YearlyFragment.this.uri);
                try {
                    YearlyFragment.this.startActivity(whatsappIntent);
                } catch (ActivityNotFoundException e) {
                }
                YearlyFragment.this.relativeLayout.bringToFront();
                YearlyFragment.this.relativeLayout.invalidate();
            }
        });
    }

    private void saveimage() {
        this.relativeLayout = (RelativeLayout) this.rootview.findViewById(R.id.rl_fshareyearly);
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

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        Log.e("mmmmm", "onrusumeyear");
    }

    @Override // android.support.v4.app.Fragment
    public void onDetach() {
        super.onDetach();
    }
}
