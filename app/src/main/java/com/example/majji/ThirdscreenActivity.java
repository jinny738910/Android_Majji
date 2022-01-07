package com.example.majji;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThirdscreenActivity extends AppCompatActivity {

    TextView tv_keyword1, tv_keyword2, tv_keyword3, tv_keyword4, tv_keyword5, tv_url;
    Button btn_back_to_Grid;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdscreen);

        btn_back_to_Grid = findViewById(R.id.btn_backtogrid);
        btn_back_to_Grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtogrid = new Intent(ThirdscreenActivity.this, FirstscreenActivity.class);
                startActivity(backtogrid);
            }
        });

        iv = (ImageView) findViewById(R.id.iv_detail);

        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        iv.setImageBitmap(image);

        //tv1 = (TextView) findViewById(R.id.tv_title);
        tv_keyword1 = (TextView) findViewById(R.id.tv_keyword_1);
        tv_keyword2 = (TextView) findViewById(R.id.tv_keyword_2);
        tv_keyword3 = (TextView) findViewById(R.id.tv_keyword_3);
        tv_keyword4 = (TextView) findViewById(R.id.tv_keyword_4);
        tv_keyword5 = (TextView) findViewById(R.id.tv_keyword_5);
        tv_url = (TextView) findViewById(R.id.tv_url);


        //tv1에 검색된 옷의 이름(상품명?)보여주기


        //tv_keyword1~5에 클릭된 이미지가 가진 키워드(feature) 보여주기
        //현재는 first screen에서 검색된 값들을 그대로 가져오도록 구현
        Intent intent = getIntent();
        String receiveStr1 = intent.getExtras().getString("sex_input");// 전달한 값을 받을 때
        String receiveStr2 = intent.getExtras().getString("top_pants_input");// 전달한 값을 받을 때
        String receiveStr3 = intent.getExtras().getString("top_pants_type_input");// 전달한 값을 받을 때
        String receiveStr4 = intent.getExtras().getString("detail_one");// 전달한 값을 받을 때
        String receiveStr5 = intent.getExtras().getString("detail_two");// 전달한 값을 받을 때

        tv_keyword1.setText(receiveStr1);
        tv_keyword2.setText(receiveStr2);
        tv_keyword3.setText(receiveStr3);
        tv_keyword4.setText(receiveStr4);
        tv_keyword5.setText(receiveStr5);



        //tv_url에 쇼핑몰 링크 걸기
        //현재는 이미지에 대해 링크를 직접 고정적인 링크로 설정. 후에 이미지 파일에 대한 링크를 저장할 수 있도록 수정해야 함
        String urlLink = "shopping mall link";
        tv_url.setText(urlLink);

        Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return "";
            }
        };

        Pattern pattern = Pattern.compile(urlLink);
        Linkify.addLinks(tv_url, pattern,
                "http://spao.com/product/%EC%95%84%EC%9A%B0%ED%84%B0%ED%98%95-%EB%8D%B0%EB%8B%98-%EC%85%94%EC%B8%A0spyjb12g91/3769/category/64/display/1/",
                null, mTransform);




    }
}

//인텐트로 넘기지 못하는 큰 이미지를 비트맵으로 넘기는 것은 성공
//사전에 정한 이미지만 출력되기 때문에, 인텐트 할 때, 고정 객체가 아닌 지정 객체가 전달될 수 있도록 수정해야됨
