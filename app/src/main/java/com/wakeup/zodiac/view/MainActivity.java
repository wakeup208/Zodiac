package com.wakeup.zodiac.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.wakeup.zodiac.R;
import com.wakeup.zodiac.model.Customgrid;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {

    public static String FACEBOOK_PAGE_ID = "YourPageName";
    public static String FACEBOOK_URL = "https://www.facebook.com/YourPageName";
    private static final int STORAGE_PERMISSION_CODE = 101;
    private int GetDay;
    private int GetMonth;
    private int GetYear;
    Calendar birthdayinstance;
    private Context context = this;
    Button dialogopener;
    Button findnamemeaning;
    GridView gridView;
    String[] horoscope = {"Aquarius", "Aries", "Cancer", "Capricornus", "Gemini", "Leo", "Libra", "Pisces", "Sagittarius", "Scorpio", "Taurus", "Virgo"};
    int[] intimageid = {R.drawable.aquarius, R.drawable.aries, R.drawable.cancer, R.drawable.capricornus, R.drawable.gemini, R.drawable.leo, R.drawable.libra, R.drawable.pisces, R.drawable.sagittarius, R.drawable.scorpio, R.drawable.taurus, R.drawable.virgo};
    ImageView rotateimage;
    TextView yourname;

    /* access modifiers changed from: protected */
    @Override
    // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", 101);
        initview();
        initgrid();
        initdialog();
        this.findnamemeaning.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String value = MainActivity.this.getApplicationContext().getSharedPreferences("MyPref", 0).getString("yourname", null);
                Intent i = new Intent(MainActivity.this, NameMeaningActivity.class);
                i.putExtra("Name", value.toString().toUpperCase());
                MainActivity.this.startActivity(i);
            }
        });
    }

    private void initdialog() {
        this.dialogopener.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                View dialogLayout = LayoutInflater.from(MainActivity.this.context).inflate(R.layout.customdialog_layout, (ViewGroup) null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this.context);
                builder.setView(dialogLayout);
                final AlertDialog customAlertDialog = builder.create();
                customAlertDialog.show();
                ((ImageView) dialogLayout.findViewById(R.id.getsignbtn)).setOnClickListener(new View.OnClickListener() {
                    /* class com.initio.Horoscope.Activity.MainActivity.AnonymousClass2.AnonymousClass1 */

                    public void onClick(View v) {
                        customAlertDialog.dismiss();
                        MainActivity.this.birthdayinstance = Calendar.getInstance();
                        MainActivity.this.GetYear =  MainActivity.this.birthdayinstance.get(1);
                        MainActivity.this.GetMonth = MainActivity.this.birthdayinstance.get(2);
                        MainActivity.this.GetDay = MainActivity.this.birthdayinstance.get(5);
                        new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Intent intent = new Intent(MainActivity.this, Getsign_detail.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("year", year);
                                bundle.putInt("month", month);
                                bundle.putInt("day", day);
                                intent.putExtras(bundle);
                                MainActivity.this.startActivity(intent);
                            }
                        }, MainActivity.this.GetYear, MainActivity.this.GetMonth, MainActivity.this.GetDay).show();
                    }
                });
                ((ImageView) dialogLayout.findViewById(R.id.uploadbtn)).setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        customAlertDialog.dismiss();
                        MainActivity.this.startActivity(new Intent(MainActivity.this, Upload_image.class));
                    }
                });
                ((ImageView) dialogLayout.findViewById(R.id.namechangebtn)).setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        customAlertDialog.dismiss();
                        MainActivity.this.startActivity(new Intent(MainActivity.this, InfoScreenActivity.class));
                        MainActivity.this.finish();
                    }
                });
                ((ImageView) dialogLayout.findViewById(R.id.facebookbtn)).setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        customAlertDialog.dismiss();
                        Intent facebookIntent = new Intent("android.intent.action.VIEW");
                        facebookIntent.setData(Uri.parse(MainActivity.this.getFacebookPageURL(MainActivity.this.getApplicationContext())));
                        MainActivity.this.startActivity(facebookIntent);
                    }
                });
            }
        });
    }

    public String getFacebookPageURL(Context context2) {
        try {
            if (context2.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            }
            return "fb://page/" + FACEBOOK_PAGE_ID;
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL;
        }
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) == -1) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 101) {
            return;
        }
        if (grantResults.length <= 0 || grantResults[0] != 0) {
            Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void initgrid() {
        this.gridView.setAdapter((ListAdapter) new Customgrid(this, this.horoscope, this.intimageid));
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("horoscope", MainActivity.this.horoscope[position]);
                bundle.putInt("image", MainActivity.this.intimageid[position]);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtras(bundle);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    private void initview() {
        this.gridView = (GridView) findViewById(R.id.gridview);
        this.yourname = (TextView) findViewById(R.id.helloyourname);
        this.findnamemeaning = (Button) findViewById(R.id.findnamemeaning);
        this.dialogopener = (Button) findViewById(R.id.menu_loader);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        pref.getString("yourbirthdate", null);
        String value = pref.getString("yourname", null);
        TextView textView = this.yourname;
        textView.setText("Hello " + value.toUpperCase());
        this.rotateimage = (ImageView) findViewById(R.id.rotateimage);
        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        aniRotate.setRepeatCount(2);
        this.rotateimage.startAnimation(aniRotate);
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.birthdayinstance.set(year, month, dayOfMonth);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    }

    public void onClick(View v) {
    }
}
