package com.example.majji;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class FirstscreenActivity extends AppCompatActivity {
    static int now = 0;
    List<DocumentSnapshot> ListofFireStore;
    Button btn_back;
    TextView tv_result, tv_sex, tv_top_bottom, tv_top_bottom_length, tv_detail_one, tv_detail_two;
    GridView gv;
    EditText et;
    ExtractTemp ex;
    ArrayList<Bitmap> DownImg;
    String receiveStr1;
    String receiveStr2;
    String receiveStr3;
    String receiveStr4;
    String receiveStr5;
    // ImageView img_btn_1;
//    private AdapterView.OnItemClickListener mItemClickListener
//            = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            String tv=(String) parent.getAdapter().getItem(position);
//            Toast.makeText(getApplicationContext(), tv, Toast.LENGTH_SHORT).show();
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstscreen);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(),  CategorySelection.class);//???????????? ?????? ????????? ?????? ???????????? ??????
                ex.deleteTemp(receiveStr1 , "TSHIRT");
                startActivity(back);
            }
        });

        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_top_bottom = (TextView) findViewById(R.id.tv_top_pants);
        tv_top_bottom_length = (TextView) findViewById(R.id.tv_top_pants_type);
        tv_detail_one = (TextView) findViewById(R.id.tv_more_details_one);
        tv_detail_two = (TextView) findViewById(R.id.tv_more_details_two);

        Intent intent = getIntent();
        receiveStr1 = intent.getExtras().getString("sex_input");// ????????? ?????? ?????? ???
        receiveStr2 = intent.getExtras().getString("top_pants_input");// ????????? ?????? ?????? ???
        receiveStr3 = intent.getExtras().getString("top_pants_type_input");// ????????? ?????? ?????? ???
        receiveStr4 = intent.getExtras().getString("detail_one");// ????????? ?????? ?????? ???
        receiveStr5 = intent.getExtras().getString("detail_two");// ????????? ?????? ?????? ???

        tv_sex.setText(receiveStr1);
        tv_top_bottom.setText(receiveStr2);
        tv_top_bottom_length.setText(receiveStr3);
        tv_detail_one.setText(receiveStr4);
        tv_detail_two.setText(receiveStr5);

        ListofFireStore = new ArrayList<DocumentSnapshot>();
        DownImg = new ArrayList<Bitmap>();
        ex = new ExtractTemp();
        //ex.setTemp("MAN","HOODIE");//error WOMAN THSIRT ?????? ?????????
        ex.setTemp(receiveStr1,"HOODIE");
        ex.getTemp(ListofFireStore,receiveStr1,"HOODIE");//ListofFireStore??? ?????????????????????0
        Context context = getApplicationContext();
        gv = (GridView) findViewById(R.id.gv_itemlist);
        ImageAdapter adapter = new ImageAdapter();
        Handler handler = new Handler(){
            public void handleMessage(Message msg){
                Log.d("mytag","nowismy");
                adapter.setImageAdapter(context,DownImg.toArray(new Bitmap[DownImg.size()]));
                gv.setAdapter(adapter);
                tv_result = (TextView) findViewById(R.id.tv_resultAmount);
                tv_result.setText("Result : "+ adapter.getCount());
            }
        };
        new Thread(){
            //?????? ???????????? ??????
            //?????? ???????????? ????????? src??? ????????? ????????? ?????? 40??????
            //??????????????? ????????? imageview??? ??????
            public void run(){
                while (true) {
                    try {
                        sleep(10);
                        if(ListofFireStore.size() != 0){
                            //??????
                            while (true) {
                                sleep(100);
                                try {
                                    if(now >= ListofFireStore.size())
                                        break;
                                    Log.d("mytag",Integer.toString(now) + "===");
                                    FireStoreSrcArraytoBitmapArray(DownImg,ListofFireStore,10);//????????? ?????????????????? ?????? 10  //now = now + 10
                                    //?????? ????????? bitmap ???????????? ???????????? ????????????
                                    Log.d("mytag","noenoenoe");
                                    Message msg = handler.obtainMessage();
                                    handler.sendMessage(msg);
                                    Log.d("mytag","sosoosso");
                                } catch (Exception e) {
                                    Log.d("mytag","loading" + e.getMessage());
                                }
                            }
                            break;
                        }
                        else{
                            Log.d("mytag","notyet");
                        }
                    } catch (Exception e) {
                        Log.d("mytag", e.getMessage());
                    }
                }
            }
        }.start();
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(FirstscreenActivity.this, "result "+position, Toast.LENGTH_SHORT).show(); //?????? ????????? ????????? ????????? ??????

                Intent todetail = new Intent(getApplicationContext(), ThirdscreenActivity.class);

                //????????? ?????? ???????????? ?????????(?????????) ??????
                int ImageResId = (int) adapter.getItem(position);
                //????????? ???????????? ????????? ???????????? ???????????? ????????? ????????? ?????????????????? ?????? ????????? ???????????? ??????
                Bitmap sendBitmap = BitmapFactory.decodeResource(getResources(), ImageResId); //????????? ????????? ??? ???????????? ?????? ?????? ???????????? ???
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                todetail.putExtra("image",byteArray);

                todetail.putExtra("sex_input", tv_sex.getText().toString());
                todetail.putExtra("top_pants_input", tv_top_bottom.getText().toString());
                todetail.putExtra("top_pants_type_input", tv_top_bottom_length.getText().toString());
                todetail.putExtra("detail_one", tv_detail_one.getText().toString());
                todetail.putExtra("detail_two", tv_detail_two.getText().toString());

                startActivity(todetail);
            }
        });


//        img_btn_1 = (ImageButton) findViewById(R.id.img_btn1);
//        img_btn_1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), ThirdscreenActivity.class);
//                startActivity(intent);
//            }
//        });
    }
    public String Pattern_check(Bitmap bitmap){
        ImageProcessor imageProcessor = new ImageProcessor.Builder().add(new ResizeOp(64,64,
                ResizeOp.ResizeMethod.BILINEAR)).build();
        TensorImage tImage = new TensorImage(DataType.FLOAT32);
        tImage.load(bitmap);
        tImage = imageProcessor.process(tImage);
        TensorBuffer probabilitybuffer = TensorBuffer.createFixedSize(new int[]{1,2},DataType.FLOAT32);
        Interpreter tflite = getTfliteInterpreter("patern.tflite");
        try {
            tflite.run(tImage.getBuffer(),probabilitybuffer.getBuffer());
        }catch(Exception e){
            Log.d("mytag","Pattern_check : " + e.getMessage());
        }
        float wow[] = probabilitybuffer.getFloatArray();
        if(wow[0] >= wow[1]){
            return "Man";//dang =
        }
        else{
            return "Woman";//line
        }
        //wow[0] == dang wow[1] == line 60% correct
    }
    public String Neck_check(Bitmap bitmap){
        ImageProcessor imageProcessor = new ImageProcessor.Builder().add(new ResizeOp(64,64,
                ResizeOp.ResizeMethod.BILINEAR)).build();
        TensorImage tImage = new TensorImage(DataType.FLOAT32);
        tImage.load(bitmap);
        tImage = imageProcessor.process(tImage);
        TensorBuffer probabilitybuffer = TensorBuffer.createFixedSize(new int[]{1,2},DataType.FLOAT32);
        Interpreter tflite = getTfliteInterpreter("neck.tflite");
        try {
            tflite.run(tImage.getBuffer(),probabilitybuffer.getBuffer());
        }catch(Exception e){
            Log.d("mytag","Neck_check :" + e.getMessage());
        }
        float wow[] = probabilitybuffer.getFloatArray();
        if(wow[0] >= wow[1]){
            return "round";//round
        }
        else{
            return "vneck";//v
        }
        //wow[0] == round wow[1] == vcheck 60% correct
    }
    public String Length_check(Bitmap bitmap){
        ImageProcessor imageProcessor = new ImageProcessor.Builder().add(new ResizeOp(64,64,
                ResizeOp.ResizeMethod.BILINEAR)).build();
        TensorImage tImage = new TensorImage(DataType.FLOAT32);
        tImage.load(bitmap);
        tImage = imageProcessor.process(tImage);
        TensorBuffer probabilitybuffer = TensorBuffer.createFixedSize(new int[]{1,2},DataType.FLOAT32);
        Interpreter tflite = getTfliteInterpreter("neck.tflite");
        try {
            tflite.run(tImage.getBuffer(),probabilitybuffer.getBuffer());
        }catch(Exception e){
            Log.d("mytag","Length_check : " + e.getMessage());
        }
        float wow[] = probabilitybuffer.getFloatArray();
        if(wow[0] >= wow[1]){
            return "Top";
        }
        else{
            return "Pants";
        }
        //wow[0] == long wow[1] == short  90% correct
    }
    private static MappedByteBuffer loadModelFile(Activity activity, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
    private Interpreter getTfliteInterpreter(String modelPath) {
        try {
            return new Interpreter(loadModelFile(FirstscreenActivity.this, modelPath));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void FireStoreSrcArraytoBitmapArray(ArrayList<Bitmap> DownImg,List<DocumentSnapshot> ListofFireStore,int size){
        int temp = now;
        try {
            for (; (now < temp + size) || (now < ListofFireStore.size()); now++) {
                DownImg.add(SrcDowntoBitmap(ListofFireStore.get(now).get("src").toString()));
            }
        }catch (Exception e){
            Log.d("mytag","FireStoreSrcArraytoBitmapArray : " + e.getMessage());
        }
    }
    private Bitmap SrcDowntoBitmap(String url){
        URL imgUrl = null;
        HttpURLConnection connection = null;
        InputStream is = null;
        Bitmap result = null;
        while(true) {
            try {
                imgUrl = new URL(url);
                connection = (HttpURLConnection) imgUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                is = connection.getInputStream();
                Log.d("what",is.toString());
                result = BitmapFactory.decodeStream(is);
                if(result != null)
                    break;
            } catch (Exception e) {
                Log.d("mytag", "SrcDowntoBitmap :" + e.getMessage());
            } finally {
                if (connection != null)
                    connection.disconnect();
            }
        }
        return result;
    }
}