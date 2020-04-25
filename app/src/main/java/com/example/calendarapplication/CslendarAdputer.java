package com.example.calendarapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CslendarAdputer extends BaseAdapter {

    private List<Date> dateArray = new ArrayList();
    private Context mContext;
    private DataManager mDataManager;
    private LayoutInflater mlayoutInflater;

    private static class ViewHolder {
        public TextView dateText;
    }

    public CslendarAdputer(Context context) {
        mContext = context;
        //カレンダー生成
        mDataManager = new DataManager();
        //レイアウトリソースを利用するための仕組み
        mlayoutInflater = LayoutInflater.from(mContext);
        dateArray = mDataManager.getDays();
    }

    @Override
    public int getCount() {
        return dateArray.size();
    }

    @Override
    public Object getItem(int position) {
        return dateArray.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = mlayoutInflater.inflate(R.layout.calendar_cell,null);
            holder = new ViewHolder();
            holder.dateText = convertView.findViewById(R.id.dateText);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        float dp = mContext.getResources().getDisplayMetrics().density;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(parent.getWidth()/7 - (int)dp, (parent.getHeight() - (int)dp * mDataManager.getWeeks() ) / mDataManager.getWeeks());
        convertView.setLayoutParams(params);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.US);
        holder.dateText.setText(dateFormat.format(dateArray.get(position)));

        if(mDataManager.inCurrentMonth(dateArray.get(position))){
            convertView.setBackgroundColor(Color.WHITE);
        }else{
            convertView.setBackgroundColor(Color.LTGRAY);
        }
        if(mDataManager.todayJudge(dateArray.get(position))){
            convertView.setBackgroundColor(R.color.holo_blue);
        }
        int colorId;
        switch (mDataManager.getDayOfWeek(dateArray.get(position))){
            case 1:
                colorId = Color.RED;
                break;
            case 7:
                colorId = Color.BLUE;
                break;
            default:
                colorId = Color.BLACK;
                break;
        }
        holder.dateText.setTextColor(colorId);
        return convertView;
    }

    public String getTitle(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM",Locale.US);
        return format.format(mDataManager.mCalendar.getTime());
    }
    public void nextMonth(){
        mDataManager.nextMonth();
        dateArray = mDataManager.getDays();
        this.notifyDataSetChanged();
    }
    public void prevMonth(){
        mDataManager.prevMonth();
        dateArray = mDataManager.getDays();
        this.notifyDataSetChanged();
    }
}
