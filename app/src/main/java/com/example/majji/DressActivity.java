package com.example.majji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class DressActivity extends Activity implements OnClickListener {

    View line;
    Button search, back, one, two, three, four, rf;
    ImageView iv, mannequin;
    ImageButton palette, ss, sn, sc, tn, ls, hd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dress);

        back = (Button) findViewById(R.id.back);  //메인으로 가기
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_toMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_toMain);
            }});

        search = (Button) findViewById(R.id.search); //검색
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_toFirst = new Intent(getApplicationContext(), FirstscreenActivity.class);
                startActivity(intent_toFirst);
            }});

        mannequin = (ImageView) findViewById(R.id.mannequin);  //배경이 되는 마네킹 이미지
        line = (View) findViewById(R.id.line);  //구분선
        rf = (Button) findViewById(R.id.refresh); //초기화 버튼
        iv = (ImageView) findViewById(R.id.imageView1);  //마네킹 위에 얹을 옷 이미지뷰

        ss = (ImageButton) findViewById(R.id.short_sleeves);  //반팔 버튼
        sn = (ImageButton) findViewById(R.id.shirt_necktie);  //반팔넥타이셔츠 버튼
        sc = (ImageButton) findViewById(R.id.short_collar);  //반팔카라 버튼
        tn = (ImageButton) findViewById(R.id.turtle_neck);  //목티 버튼
        ls = (ImageButton) findViewById(R.id.long_shirt);  //긴 셔츠 버튼
        hd = (ImageButton) findViewById(R.id.hoodie);  //후드티 버튼


        rf.setOnClickListener(this);
        ss.setOnClickListener(this);
        sn.setOnClickListener(this);
        sc.setOnClickListener(this);
        tn.setOnClickListener(this);
        ls.setOnClickListener(this);
        hd.setOnClickListener(this);

        //여기는 당장 구현X ----------------------------------

        //검색
        one = (Button) findViewById(R.id.one);  //상의
        two = (Button) findViewById(R.id.two);  //하의
        three = (Button) findViewById(R.id.three);  //겉옷
        four = (Button) findViewById(R.id.four);  //그 외
        palette = (ImageButton) findViewById(R.id.palette);  //색상변경

        search.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        palette.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

        switch (arg0.getId()) {

            case R.id.refresh:

                iv.setImageResource(0);

            case R.id.short_sleeves:

                iv.setImageResource(R.drawable.pic_ss);

                break;

            case R.id.shirt_necktie:

                iv.setImageResource(R.drawable.pic_sn);

                break;

            case R.id.short_collar:

                iv.setImageResource(R.drawable.pic_sc);

                break;

            case R.id.turtle_neck:

                iv.setImageResource(R.drawable.pic_tn);

                break;

            case R.id.long_shirt:

                iv.setImageResource(R.drawable.pic_ls);

                break;

            case R.id.hoodie:

                iv.setImageResource(R.drawable.pic_hd);

                break;

        }

    }

}