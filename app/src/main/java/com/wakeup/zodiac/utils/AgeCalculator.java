package com.wakeup.zodiac.utils;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.Calendar;
import java.util.Locale;

public class AgeCalculator {
    public static int endDay;
    public static int endMonth;
    public static int endYear;
    private String currentDay;
    private String dayOfBirth;
    private Calendar endCalendar = Calendar.getInstance();
    private Calendar futureYear = Calendar.getInstance();
    private int resultDay;
    private int resultMonth;
    private int resultYear;
    private Calendar startCalendar = Calendar.getInstance();
    public int startDay;
    public int startMonth;
    public int startYear;

    public String getCurrentDay() {
        endYear = this.endCalendar.get(1);
        endMonth = this.endCalendar.get(2);
        endMonth++;
        endDay = this.endCalendar.get(5);
        this.currentDay = this.endCalendar.getDisplayName(7, 2, Locale.getDefault());
        return this.currentDay + ", " + endDay + "." + endMonth + "." + endYear;
    }

    public void getUserInputs(int yy, int mm, int dd) {
        this.startYear = yy;
        this.startMonth = mm;
        this.startDay = dd;
        this.startCalendar.set(1, this.startYear);
        this.startCalendar.set(2, this.startMonth);
        this.startCalendar.set(5, this.startDay);
        this.futureYear.set(1, endYear + 1);
        this.futureYear.set(2, this.startMonth);
        this.futureYear.set(5, this.startDay);
        this.dayOfBirth = this.startCalendar.getDisplayName(7, 2, Locale.getDefault());
    }

    public String getDayOfBirth() {
        return this.dayOfBirth;
    }

    public void calculateYear() {
        this.resultYear = endYear - this.startYear;
    }

    public void calculateMonth() {
        int i = endMonth;
        int i2 = this.startMonth;
        if (i >= i2) {
            this.resultMonth = i - i2;
            return;
        }
        this.resultMonth = i - i2;
        this.resultMonth += 12;
        this.resultYear--;
    }

    public void calculateDay() {
        int i = endDay;
        int i2 = this.startDay;
        if (i >= i2) {
            this.resultDay = i - i2;
            return;
        }
        this.resultDay = i - i2;
        this.resultDay += 30;
        int i3 = this.resultMonth;
        if (i3 == 0) {
            this.resultMonth = 11;
            this.resultYear--;
            return;
        }
        this.resultMonth = i3 - 1;
    }

    public int[] getResultYear() {
        Period period = new Period(this.startCalendar.getTimeInMillis(), this.endCalendar.getTimeInMillis(), PeriodType.yearMonthDayTime());
        return new int[]{period.getYears(), period.getMonths(), period.getDays()};
    }

    public int[] getRemainingTimeForNextBirthday() {
        Period period = new Period(this.endCalendar.getTimeInMillis(), this.futureYear.getTimeInMillis(), PeriodType.yearMonthDayTime());
        return new int[]{period.getYears(), period.getMonths(), period.getDays()};
    }

    public String getZodiacSign(int month, int day) {
        if (month == 12 && day >= 22 && day <= 31) {
            return "Capricorn";
        }
        if (month == 1 && day >= 1 && day <= 19) {
            return "Capricorn";
        }
        if (month == 1 && day >= 20 && day <= 31) {
            return "Aquarius";
        }
        if (month == 2 && day >= 1 && day <= 17) {
            return "Aquarius";
        }
        if (month == 2 && day >= 18 && day <= 29) {
            return "Pisces";
        }
        if (month == 3 && day >= 1 && day <= 19) {
            return "Pisces";
        }
        if (month == 3 && day >= 20 && day <= 31) {
            return "Aries";
        }
        if (month == 4 && day >= 1 && day <= 19) {
            return "Aries";
        }
        if (month == 4 && day >= 20 && day <= 30) {
            return "Taurus";
        }
        if (month == 5 && day >= 1 && day <= 20) {
            return "Taurus";
        }
        if (month == 5 && day >= 21 && day <= 31) {
            return "Gemini";
        }
        if (month == 6 && day >= 1 && day <= 20) {
            return "Gemini";
        }
        if (month == 6 && day >= 21 && day <= 30) {
            return "Cancer";
        }
        if (month == 7 && day >= 1 && day <= 22) {
            return "Cancer";
        }
        if (month == 7 && day >= 23 && day <= 31) {
            return "Leo";
        }
        if (month == 8 && day >= 1 && day <= 22) {
            return "Leo";
        }
        if (month == 8 && day >= 23 && day <= 31) {
            return "Virgo";
        }
        if (month == 9 && day >= 1 && day <= 22) {
            return "Virgo";
        }
        if (month == 9 && day >= 23 && day <= 30) {
            return "Libra";
        }
        if (month == 10 && day >= 1 && day <= 22) {
            return "Libra";
        }
        if (month == 10 && day >= 23 && day <= 31) {
            return "Scorpio";
        }
        if (month == 11 && day >= 1 && day <= 21) {
            return "Scorpio";
        }
        if (month == 11 && day >= 22 && day <= 30) {
            return "Sagittarius";
        }
        if (month != 12 || day < 1 || day > 21) {
            return "Illegal date";
        }
        return "Sagittarius";
    }
}
