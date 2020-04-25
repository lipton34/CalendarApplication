package com.example.calendarapplication;

import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

public class DataManager {
    Calendar mCalendar;
    //コンストラクタでカレンダークラスを生成
    public DataManager(){
        mCalendar = Calendar.getInstance();
    }
    //メソッド定義　当月の要素を取得
    public List<Date> getDays(){
        //現在の年月日を変数に格納
        Date startDate = mCalendar.getTime();
        //カレンダーに表す日数をカウントに代入
        int count = getWeeks()*7;
        //月初に設定
        mCalendar.set(Calendar.DATE,1);
        //前月末の曜日を取得
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK)-1;
        mCalendar.add(Calendar.DATE,-dayOfWeek);

        List<Date> days = new ArrayList<>();

        for(int i = 0;i<count;i++){
            //リストに追加　設定されている年月日曜日を
            days.add(mCalendar.getTime());
            //一日進める
            mCalendar.add(Calendar.DATE,1);
        }
        //現在の年月日に復元
        mCalendar.setTime(startDate);
        return days;
    }
    //当日かどうかの判定するメソッド
    public boolean inCurrentMonth(Date date){
        //yyyy年mm月のフォーマットを設定　
        SimpleDateFormat format = new SimpleDateFormat("yyyy.mm", Locale.US);


        String currentMonth = format.format(mCalendar.getTime());
        if (currentMonth.equals(format.format(date))) {
            return true;
        }else{
            return false;
        }
    }
    public boolean todayJudge (Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        String today = format.format(Calendar.getInstance().getTime());
        return today.equals(format.format(date));
    }
    public int getWeeks(){
        //週の数を取得
        return mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }
    public int getDayOfWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    public void nextMonth(){
        mCalendar.add(Calendar.MONTH,1);
    }
    public void prevMonth(){
        mCalendar.add(Calendar.MONTH,-1);
    }
}
