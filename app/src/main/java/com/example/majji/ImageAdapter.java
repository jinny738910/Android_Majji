package com.example.majji;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    private Context context;

    //private Integer[] img;
    private Bitmap[] img;

    //Integer[] img
    public void setImageAdapter(Context context,Bitmap[] img) {
        //context를 화면에 출력하기 위한 메서드
        this.context = context;
        this.img = img;
    }

    public int getCount() {
        //표현할 아이템 수 설정 메서드
        return (null != img) ? img.length : 0;
        //return img.length; //img 배열 내의 원소 전부 출력
    }

    public Object getItem(int position) {
        //원하는 위치의 아이템 반환
        return (null != img) ? img[position] : 0;
    }

    public long getItemId(int position) {
        //아이템의 id 설정해주는 메서드(위에서 이미 배열로 설정했다면 필요 없음)
        Exception e = new Exception();
        return 0;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        //실제 아이템을 화면에 배치시 호출되는 메서드
        ImageView imageView = null;

        if(view == null) {
            imageView = new ImageView(context);
        }

        else {
            imageView = (ImageView) view;
        }

        //이미지 크기 조절
        imageView.setLayoutParams(new ViewGroup.LayoutParams(400, 400));
        //이미지 출력 형식 지정
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        //이미지 뷰 간 간격 조절
        imageView.setPadding(10, 10, 10, 10);

        //imageView.setImageResource(img[position]);
        imageView.setImageBitmap(img[position]);
        return imageView;
    }
}
