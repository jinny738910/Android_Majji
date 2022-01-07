package com.example.majji;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText et_search;
    Button btn_gallery, btn_keyword, btn_drawing;
    final int GET_GALLERY_IMAGE = 200;
    androidx.appcompat.widget.Toolbar toolbar;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    MenuItem mSearch;

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.options_menu, menu);
//
//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//        return true;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.bringToFront();
        btn_keyword = (Button) findViewById(R.id.btn_keyword);
        imageView1 = (ImageView)findViewById(R.id.imageView3);
        imageView2 = (ImageView)findViewById(R.id.imageView4);
        imageView3 = (ImageView)findViewById(R.id.imageView7);
        imageView3 = (ImageView)findViewById(R.id.imageView6);
        imageView5 = (ImageView)findViewById(R.id.imageView5);
        /*imageView4.setImageResource(R.drawable.banner_image1);
        imageView5.setImageResource(R.drawable.login_btn_baner2);
        imageView1.setImageResource(R.drawable.magnifying);
        imageView2.setImageResource(R.drawable.camera);
        imageView3.setImageResource(R.drawable.hanger);*/
        /*imageView5.setImageResource(R.drawable.d);
        imageView1.setImageResource(R.drawable.e);
        imageView2.setImageResource(R.drawable.f);
        imageView3.setImageResource(R.drawable.g);*/
        imageView1.bringToFront();
        imageView2.bringToFront();
        imageView3.bringToFront();
        btn_keyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategorySelection.class); // search 버튼 클릭시 Category Selection으로 이동
                //intent.putExtra("keyword_input", et_search.getText().toString());
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://spao.com/")); // search 버튼 클릭시 설정한 웹페이지로 이동(크롬으로 이동)
                startActivity(intent);
            }
        });


        btn_gallery = (Button) findViewById(R.id.btn_gallery);
        btn_gallery.setOnClickListener(new View.OnClickListener() { //버튼 클릭시 갤러리 이동

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        btn_drawing = (Button) findViewById(R.id.btn_drawing);
        btn_drawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todraw = new Intent(getApplicationContext(), DressActivity.class);
                startActivity(todraw);
            }
        });
    }

}