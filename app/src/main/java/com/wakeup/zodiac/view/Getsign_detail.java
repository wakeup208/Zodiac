package com.wakeup.zodiac.view;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.wakeup.zodiac.R;
import com.wakeup.zodiac.utils.AgeCalculator;


public class Getsign_detail extends AppCompatActivity {
    public static String zodiacsign = "";
    TextView Birthday;
    TextView agedays;
    TextView agemonth;
    TextView ageyear;
    TextView nextagedays;
    TextView nextagemonth;
    TextView yourzodiacsign;

    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getsign_detail);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        init();
        Bundle bundle = getIntent().getExtras();
        int year = bundle.getInt("year");
        int month = bundle.getInt("month");
        int day = bundle.getInt("day");
        showAgeAndZodiac(year, month, day);
        setAgeAndZodiac(year, month, day);
    }

    private void init() {
        this.agemonth = (TextView) findViewById(R.id.ageMonth);
        this.agedays = (TextView) findViewById(R.id.ageDays);
        this.ageyear = (TextView) findViewById(R.id.ageYear);
        this.nextagemonth = (TextView) findViewById(R.id.nextageMonth);
        this.nextagedays = (TextView) findViewById(R.id.nextageDays);
        this.Birthday = (TextView) findViewById(R.id.birth_Day);
        this.yourzodiacsign = (TextView) findViewById(R.id.yourzodiacsign);
    }

    public void setAgeAndZodiac(int year, int month, int day) {
        AgeCalculator age = new AgeCalculator();
        age.getCurrentDay();
        age.getUserInputs(year, month, day);
        int[] ageInDays = age.getResultYear();
        age.getRemainingTimeForNextBirthday();
        int[] nextBd = age.getRemainingTimeForNextBirthday();
        String dayofbirth = age.getDayOfBirth();
        showAgeAndZodiac(year, month, day);
        TextView textView = this.agemonth;
        textView.setText("" + ageInDays[1]);
        TextView textView2 = this.agedays;
        textView2.setText("" + ageInDays[2]);
        TextView textView3 = this.ageyear;
        textView3.setText("" + ageInDays[0]);
        TextView textView4 = this.nextagemonth;
        textView4.setText("" + nextBd[1]);
        TextView textView5 = this.nextagedays;
        textView5.setText("" + nextBd[2]);
        TextView textView6 = this.Birthday;
        textView6.setText("" + dayofbirth);
    }

    public void showAgeAndZodiac(int year, int month, int day) {
        AgeCalculator ageCalculator = new AgeCalculator();
        ageCalculator.getCurrentDay();
        ageCalculator.getUserInputs(year, month, day);
        zodiacsign = ageCalculator.getZodiacSign(month + 1, day);
        this.yourzodiacsign.setText(zodiacsign);
    }
}
