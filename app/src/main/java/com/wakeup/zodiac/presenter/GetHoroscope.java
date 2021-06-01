package com.wakeup.zodiac.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.wakeup.zodiac.fragment.DailyFragment;
import com.wakeup.zodiac.fragment.MonthlyFragment;
import com.wakeup.zodiac.fragment.WeeklyFragment;
import com.wakeup.zodiac.fragment.YearlyFragment;
import com.wakeup.zodiac.model.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetHoroscope {
    public static String date = "";
    public static String horoscopetext = "";
    public static String month = "";
    public static String week = "";
    public static String year = "";

    public static void getdetail(String url, String horoscope, final Context context) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("please wait");
        progressDialog.show();
        Log.d("abcd","URL = " + url.toString());
        Log.d("abcd","horoscope = " + horoscope);

        if (url == "today/") {
            Api api = Retroclient.getapiservice();
            progressDialog.show();
            api.getmyjson(url + horoscope).enqueue(new Callback<com.wakeup.zodiac.model.Response>() {

                public void onResponse(Call<com.wakeup.zodiac.model.Response> call, Response<com.wakeup.zodiac.model.Response> response) {
                    if (response.isSuccessful()) {
                        GetHoroscope.date = response.body().getDate();
                        GetHoroscope.horoscopetext = response.body().getHoroscope();

                        Log.d("abcd","date = " + date);
                        Log.d("abcd","horoscopetext = " + horoscopetext);

                        GetHoroscope.setdailydata(date, horoscopetext);
                        DailyFragment.setdata1(GetHoroscope.horoscopetext);
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<com.wakeup.zodiac.model.Response> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "please turn on internet", Toast.LENGTH_SHORT).show();
                }

            });
        } else if (url == "week/") {
            Api api2 = Retroclient.getapiservice();
            Call<com.wakeup.zodiac.model.Response> dailycall = api2.getmyjson(url + horoscope);
            progressDialog.show();
            dailycall.enqueue(new Callback<com.wakeup.zodiac.model.Response>() {

                public void onResponse(Call<com.wakeup.zodiac.model.Response> call, Response<com.wakeup.zodiac.model.Response> response) {
                    if (response.isSuccessful()) {
                        GetHoroscope.week = response.body().getWeek();
                        GetHoroscope.horoscopetext = response.body().getHoroscope();

                        Log.d("abcd","week = " + date);
                        Log.d("abcd","horoscopetext = " + horoscopetext);

                        GetHoroscope.setweekdata(GetHoroscope.week, GetHoroscope.horoscopetext);
                        DailyFragment.setdata1(GetHoroscope.horoscopetext);
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<com.wakeup.zodiac.model.Response> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "please turn on internet", Toast.LENGTH_SHORT).show();
                }

            });
        } else if (url == "month/") {
            Api api3 = Retroclient.getapiservice();
            Call<com.wakeup.zodiac.model.Response> dailycall2 = api3.getmyjson(url + horoscope);
            progressDialog.show();
            dailycall2.enqueue(new Callback<com.wakeup.zodiac.model.Response>() {

                public void onResponse(Call<com.wakeup.zodiac.model.Response> call, Response<com.wakeup.zodiac.model.Response> response) {
                    if (response.isSuccessful()) {
                        GetHoroscope.month = response.body().getMonth();
                        GetHoroscope.horoscopetext = response.body().getHoroscope();

                        Log.d("abcd","month = " + month);
                        Log.d("abcd","horoscopetext = " + horoscopetext);

                        GetHoroscope.setmonthdata(GetHoroscope.month, GetHoroscope.horoscopetext);
                        DailyFragment.setdata1(GetHoroscope.horoscopetext);
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<com.wakeup.zodiac.model.Response> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "please turn on internet", Toast.LENGTH_SHORT).show();
                }

            });
        } else if (url == "year/") {
            Api api4 = Retroclient.getapiservice();
            Call<com.wakeup.zodiac.model.Response> dailycall3 = api4.getmyjson(url + horoscope);
            progressDialog.show();
            dailycall3.enqueue(new Callback<com.wakeup.zodiac.model.Response>() {

                public void onResponse(Call<com.wakeup.zodiac.model.Response> call, Response<com.wakeup.zodiac.model.Response> response) {
                    if (response.isSuccessful()) {
                        GetHoroscope.year = response.body().getYear();
                        GetHoroscope.horoscopetext = response.body().getHoroscope();

                        Log.d("abcd","year = " + year);
                        Log.d("abcd","horoscopetext = " + horoscopetext);

                        GetHoroscope.setyearlydata(GetHoroscope.year, GetHoroscope.horoscopetext);
                        DailyFragment.setdata1(GetHoroscope.horoscopetext);
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<com.wakeup.zodiac.model.Response> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "please turn on internet", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    public static void setyearlydata(String year2, String horoscopetext2) {
        YearlyFragment.yearlydate.setText(year2);
        String horoscopetext3 = horoscopetext2.replaceAll("\\[", "").replaceAll("\\]", "");
        YearlyFragment.yearlyhoroscopetext.setText(horoscopetext3);
        YearlyFragment.tvContentyearly.setText(horoscopetext3);
    }

    public static void setmonthdata(String month2, String horoscopetext2) {
        MonthlyFragment.monthlydate.setText(month2);
        String horoscopetext3 = horoscopetext2.replaceAll("\\[", "").replaceAll("\\]", "");
        MonthlyFragment.monthlyhoroscopetext.setText(horoscopetext3);
        MonthlyFragment.tvContentmonthly.setText(horoscopetext3);
    }

    public static void setweekdata(String week2, String horoscopetext2) {
        WeeklyFragment.weeklydate.setText(week2);
        if (horoscopetext2.charAt(1) == '[') {
            horoscopetext2.replaceFirst("[  ]", "");
        }
        String horoscopetext3 = horoscopetext2.replaceAll("\\[", "").replaceAll("\\]", "");
        WeeklyFragment.weeklyhoroscopetext.setText(horoscopetext3);
        WeeklyFragment.tvContentweekhly.setText(horoscopetext3);
    }

    public static void setdailydata(String date2, String horoscopetext2) {
        DailyFragment.dailyhoroscopetext.setText(horoscopetext2);
        String horoscopetext3 = horoscopetext2.replaceAll("\\[", "").replaceAll("\\]", "");
        DailyFragment.dailydate.setText(date2);
        DailyFragment.tvContent.setText(horoscopetext3);
    }
}
