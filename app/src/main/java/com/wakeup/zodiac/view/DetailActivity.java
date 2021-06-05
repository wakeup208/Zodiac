package com.wakeup.zodiac.view;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wakeup.zodiac.R;
import com.wakeup.zodiac.fragment.DailyFragment;
import com.wakeup.zodiac.fragment.MonthlyFragment;
import com.wakeup.zodiac.fragment.WeeklyFragment;
import com.wakeup.zodiac.fragment.YearlyFragment;
import com.wakeup.zodiac.model.Pager;

public class DetailActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    public static String horoscope;
    public static int image;
    TextView dailysign;
    ViewPager horoscrope_Viewpager;
    int position;
    ImageView selected_image;
    TabLayout tabLayout;

    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        initView();
        this.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                DetailActivity.this.position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        this.dailysign = (TextView) findViewById(R.id.dailysign);
        this.selected_image = (ImageView) findViewById(R.id.selectedImage);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            image = bundle.getInt("image", 0);
            horoscope = bundle.getString("horoscope", "data");
            Log.d("image" + image, "het" + horoscope);
            this.dailysign.setText(horoscope + "");
            this.selected_image.setImageResource(image);
        }
        Bundle bundle1 = new Bundle();
        bundle1.putInt("image", image);
        bundle1.putString("horoscope", horoscope);
        initTab();
    }

    public void initTab() {
        this.tabLayout = (TabLayout) findViewById(R.id.tabLayout1);
        this.horoscrope_Viewpager = (ViewPager) findViewById(R.id.horoscrope_Viewpager);
        this.tabLayout.setupWithViewPager(this.horoscrope_Viewpager);
        Pager pageradapter = new Pager(getSupportFragmentManager());
        pageradapter.addFragment(new DailyFragment(), "daily");
        pageradapter.addFragment(new WeeklyFragment(), "weekly");
        pageradapter.addFragment(new MonthlyFragment(), "monthly");
        pageradapter.addFragment(new YearlyFragment(), "yearly");
        this.horoscrope_Viewpager.setAdapter(pageradapter);
        this.horoscrope_Viewpager.setOffscreenPageLimit(5);
    }

    public void onInit(int status) {
    }
}
