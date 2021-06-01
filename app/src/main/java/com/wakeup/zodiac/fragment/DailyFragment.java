package com.wakeup.zodiac.fragment;

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

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.wakeup.zodiac.R;
import com.wakeup.zodiac.presenter.GetHoroscope;
import com.wakeup.zodiac.view.BaseFragment;
import com.wakeup.zodiac.view.DetailActivity;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.Random;

public class DailyFragment extends BaseFragment {
    public static ImageView cardimage;
    public static TextView cardsigndaily;
    public static TextView dailydate;
    public static TextView dailyhoroscopetext;
    static String data1;
    public static TextView tvContent;
    Bitmap bitmap;
    Context context;
    private FloatingActionButton fabCopy;
    private FloatingActionButton fabTTS;
    private FloatingActionButton fabWP;
    private FloatingActionButton fabshare;
    private FloatingActionMenu fam;
    String horoscopesign = DetailActivity.horoscope;
    RelativeLayout relativeLayout;
    View rootview;
    Bitmap scaledBitmapFore;
    private TextToSpeech tts;
    Uri uri;
    String url = "today/";

    public static void setdata1(String horoscopetext) {
        data1 = horoscopetext;
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootview = inflater.inflate(R.layout.fragment_daily, container, false);
        this.context = getActivity();
        initView();
        GetHoroscope.getdetail(this.url, this.horoscopesign, getActivity());
        initts();
        initwp();
        initshare();
        initcopy();
        return this.rootview;
    }

    private void initfab() {
        this.fam.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {

            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    DailyFragment.this.showToast("Menu is opened");
                } else {
                    DailyFragment.this.showToast("Menu is closed");
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
        this.fabshare.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myIntent = new Intent("android.intent.action.SEND");
                myIntent.setType("text/plain");
                myIntent.putExtra("android.intent.extra.SUBJECT", "Your body is here");
                myIntent.putExtra("android.intent.extra.TEXT", DailyFragment.data1);
                DailyFragment.this.startActivity(Intent.createChooser(myIntent, "Share using"));
            }
        });
    }

    private void initcopy() {
        this.fabCopy.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Context context = DailyFragment.this.context;
                DailyFragment.this.getContext();
                ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("textdata", DailyFragment.data1));
                Toast.makeText(DailyFragment.this.getContext(), "Copied successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initts() {
        this.tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {

            public void onInit(int status) {
                if (status == 0) {
                    int ttsLang = DailyFragment.this.tts.setLanguage(Locale.US);
                    if (ttsLang == -1 || ttsLang == -2) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                    return;
                }
                Toast.makeText(DailyFragment.this.getContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
            }
        });
        this.fabTTS.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.i("TTS", "button clicked: " + DailyFragment.data1);
                if (DailyFragment.this.tts.speak(DailyFragment.data1, 0, null) == -1) {
                    Log.e("TTS", "Error in converting Text to Speech!");
                }
            }
        });
    }

    private void initwp() {
        this.fabWP.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DailyFragment.this.saveimage();
                Bitmap bitmap = DailyFragment.this.scaledBitmapFore;
                Intent whatsappIntent = new Intent("android.intent.action.SEND");
                whatsappIntent.setType("image/*");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra("android.intent.extra.STREAM", DailyFragment.this.uri);
                try {
                    DailyFragment.this.startActivity(whatsappIntent);
                } catch (ActivityNotFoundException e) {
                }
                DailyFragment.this.relativeLayout.bringToFront();
                DailyFragment.this.relativeLayout.invalidate();
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void saveimage() {
        this.relativeLayout = (RelativeLayout) this.rootview.findViewById(R.id.rl_fshare);
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
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
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

    @Override // android.support.v4.app.Fragment
    public void onStart() {
        super.onStart();
    }

    public void initView() {
        dailydate = (TextView) this.rootview.findViewById(R.id.dailydate);
        dailyhoroscopetext = (TextView) this.rootview.findViewById(R.id.dailyhoroscopeText);
        dailyhoroscopetext.setMovementMethod(new ScrollingMovementMethod());
        cardimage = (ImageView) this.rootview.findViewById(R.id.img_signdaily);
        cardsigndaily = (TextView) this.rootview.findViewById(R.id.cardsigndaily);
        tvContent = (TextView) this.rootview.findViewById(R.id.tv_contentdaily);
        cardimage.setImageResource(DetailActivity.image);
        cardsigndaily.setText(DetailActivity.horoscope);
        this.fam = (FloatingActionMenu) this.rootview.findViewById(R.id.fab_menu);
        this.fabTTS = (FloatingActionButton) this.rootview.findViewById(R.id.fab1);
        this.fabWP = (FloatingActionButton) this.rootview.findViewById(R.id.fab2);
        this.fabshare = (FloatingActionButton) this.rootview.findViewById(R.id.fab3);
        this.fabCopy = (FloatingActionButton) this.rootview.findViewById(R.id.fab4);
    }
}
