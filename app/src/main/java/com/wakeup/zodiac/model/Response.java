package com.wakeup.zodiac.model;


import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("date")
    private String date;
    @SerializedName("horoscope")
    private String horoscope;
    @SerializedName("month")
    private String month;
    @SerializedName("sunsign")
    private String sunsign;
    @SerializedName("week")
    private String week;
    @SerializedName("year")
    private String year;

    public void setDate(String date2) {
        this.date = date2;
    }

    public String getDate() {
        return this.date;
    }

    public void setHoroscope(String horoscope2) {
        this.horoscope = horoscope2;
    }

    public String getHoroscope() {
        return this.horoscope;
    }

    public void setSunsign(String sunsign2) {
        this.sunsign = sunsign2;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year2) {
        this.year = year2;
    }

    public void setMonth(String month2) {
        this.month = month2;
    }

    public String getMonth() {
        return this.month;
    }

    public String getSunsign() {
        return this.sunsign;
    }

    public void setWeek(String week2) {
        this.week = week2;
    }

    public String getWeek() {
        return this.week;
    }

    public String toString() {
        return null;
    }
}
