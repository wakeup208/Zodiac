package com.wakeup.zodiac.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.wakeup.zodiac.R;
import com.wakeup.zodiac.utils.AgeCalculator;

import java.util.Calendar;

public class InfoScreenActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static String zodiacsign = "";
    int birthdate;
    Calendar birthdayinstance;
    int birthmonth;
    int birthyear;
    Button getstart;
    ImageView imgRotate;
    ImageView selectbirthdate;
    String showbirthdate;
    String showname;
    TextView yourbithdate;
    EditText yourname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_screen);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        initview();
        selectbirth();
        getstart.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(InfoScreenActivity.this.getApplicationContext(), "plz enter your name " + yourname.getText().toString() , Toast.LENGTH_SHORT).show();

                if (yourname.getText().toString().equals("") ||yourbithdate.getText().toString().equals("")) {
                    Toast.makeText(InfoScreenActivity.this.getApplicationContext(), "plz enter your name ", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences sharedPreferences1 = InfoScreenActivity.this.getSharedPreferences("MyPref", 0);
                InfoScreenActivity.this.shredpref();
                if (!sharedPreferences1.contains("yourbirthdate") || !sharedPreferences1.contains("yourname")) {
                    InfoScreenActivity.this.getstart.startAnimation(AnimationUtils.loadAnimation(InfoScreenActivity.this, R.anim.shake));
                    Toast.makeText(InfoScreenActivity.this.getApplicationContext(), "plz enter your name ", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences.Editor editor = InfoScreenActivity.this.getSharedPreferences("MyPrefs", 0).edit();
                editor.putString("Sign", InfoScreenActivity.zodiacsign);
                editor.commit();
                InfoScreenActivity.this.startActivity(new Intent(InfoScreenActivity.this, MainActivity.class));
                InfoScreenActivity.this.finish();
            }
        });
    }

    public void showAgeAndZodiac(int year, int month, int day) {
        AgeCalculator ageCalculator = new AgeCalculator();
        ageCalculator.getCurrentDay();
        ageCalculator.getUserInputs(year, month, day);
        zodiacsign = ageCalculator.getZodiacSign(month + 1, day);
    }

    private void shredpref() {
        this.showname = this.yourname.getText().toString();
        this.showbirthdate = this.yourbithdate.getText().toString();
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("MyPref", 0).edit();
        editor.putString("yourbirthdate", this.showbirthdate);
        editor.putString("yourname", this.showname);
        editor.commit();
    }

    private void initview() {
        this.yourname = (EditText) findViewById(R.id.yourname);
        this.yourbithdate = (TextView) findViewById(R.id.yourbdate);
        this.getstart = (Button) findViewById(R.id.getstart);
        this.selectbirthdate = (ImageView) findViewById(R.id.selectbdayimage);
        this.imgRotate = (ImageView) findViewById(R.id.img_rotate);
    }

    private void selectbirth() {
        this.yourbithdate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                InfoScreenActivity.this.birthdayinstance = Calendar.getInstance();
                InfoScreenActivity infoScreenActivity = InfoScreenActivity.this;
                infoScreenActivity.birthyear = infoScreenActivity.birthdayinstance.get(1);
                InfoScreenActivity infoScreenActivity2 = InfoScreenActivity.this;
                infoScreenActivity2.birthmonth = infoScreenActivity2.birthdayinstance.get(2);
                InfoScreenActivity infoScreenActivity3 = InfoScreenActivity.this;
                infoScreenActivity3.birthdate = infoScreenActivity3.birthdayinstance.get(5);
                new DatePickerDialog(InfoScreenActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        TextView textView = InfoScreenActivity.this.yourbithdate;
                        textView.setText("" + year + "-" + month + "-" + dayOfMonth);
                        InfoScreenActivity.this.showAgeAndZodiac(year, month, dayOfMonth);
                    }
                }, InfoScreenActivity.this.birthyear, InfoScreenActivity.this.birthmonth, InfoScreenActivity.this.birthdate).show();
            }
        });
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    }
}
