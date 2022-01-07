package com.example.majji;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NoticeListAdapter extends BaseAdapter {
    private Context context;
    private List<MemberInfo> noticeList;

    public NoticeListAdapter(Context context, List<MemberInfo> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int i) {
        return noticeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.activity_notice, null);
        TextView numberText = (TextView)v.findViewById(R.id.noticeText);
        TextView nameText = (TextView)v.findViewById(R.id.nameText);
        TextView majorText = (TextView)v.findViewById(R.id.dateText);

        numberText.setText(noticeList.get(i).getNumber());
        nameText.setText(noticeList.get(i).getName());
        majorText.setText(noticeList.get(i).getMajor());

        v.setTag(noticeList.get(i).getNumber());
        return v;
    }

}
