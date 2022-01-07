package com.example.majji;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

enum Type{
    TSHIRT,
    HOODIE,
    SWEATSHIRT
}

enum Site{
    SPAO,
    BAON
}

enum Sex{
    MAN,
    WOMAN
}

public class Scrap_Spao extends Activity{
    private static String TAG = "TestActivity";

    //여기도 case나눠서 type별로해야됨
    public void collectProduct(){

        /*
         * 상품 코드  : 사이트=>성별=>타입=>상품순서4자리
         * 사이트 : 1 - 스파오, 2 - 타사이트
         * 성별 : 1 - 여자, 2 - 남자
         * 타입 : 1 - 후드, 2 - 짧셫, 3 - 긴셫, 4 - 맨투맨 (셔츠는 한곳에 저장됨)
         * 상품번호 : 0000~1000
         */


//스파오 수집
        //여자

        //각 타입별로 쓰레드를 만들어 크롤링 => 데이터 수집 => 데이터베이스 & 스토리지 등록
        crawlingThread spao_hoodie= new crawlingThread(app.getRes().getString(R.string.spao_hoodie_url),app.getRes().getString(R.string.spao_hoodie_max), Type.HOODIE, Site.SPAO, Sex.WOMAN,1110000);
        crawlingThread spao_shirt = new crawlingThread(app.getRes().getString(R.string.spao_shortTshirt_url),app.getRes().getString(R.string.spao_shortTshirt_max), Type.TSHIRT,Site.SPAO,Sex.WOMAN,1121000 );
        crawlingThread spao_shirt2 = new crawlingThread(app.getRes().getString(R.string.spao_longTshirt_url),app.getRes().getString(R.string.spao_longTshirt_max), Type.TSHIRT,Site.SPAO,Sex.WOMAN,1132000 );
        crawlingThread spao_sweat = new crawlingThread(app.getRes().getString(R.string.spao_sweatShirt_url),app.getRes().getString(R.string.spao_sweatShirt_max),Type.SWEATSHIRT,Site.SPAO,Sex.WOMAN,1143000);

        spao_hoodie.start();
        spao_shirt.start();
        spao_shirt2.start();
        spao_sweat.start();

        try{
            spao_hoodie.join();
            spao_shirt.join();
            spao_shirt2.join();
            spao_sweat.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        Log.v(TAG, "스파오 여자 완료");

    //남자
        crawlingThread spao_hoodie_m= new crawlingThread(app.getRes().getString(R.string.spao_hoodie_url_m),app.getRes().getString(R.string.spao_hoodie_max_m), Type.HOODIE, Site.SPAO, Sex.MAN,1210000);
        crawlingThread spao_shirt_m = new crawlingThread(app.getRes().getString(R.string.spao_shirtTshirt_url_m),app.getRes().getString(R.string.spao_shirtTshirt_max_m), Type.TSHIRT,Site.SPAO,Sex.MAN ,1221000);
        crawlingThread spao_shirt2_m = new crawlingThread(app.getRes().getString(R.string.spao_longTshirt_url_m),app.getRes().getString(R.string.spao_longTshirt_max_m), Type.TSHIRT,Site.SPAO,Sex.MAN,1232000 );
        crawlingThread spao_sweat_m = new crawlingThread(app.getRes().getString(R.string.spao_sweatShirt_url_m),app.getRes().getString(R.string.spao_sweatShirt_max_m),Type.SWEATSHIRT,Site.SPAO,Sex.MAN,1243000);


        spao_hoodie_m.start();
        spao_shirt_m.start();
        spao_shirt2_m.start();
        spao_sweat_m.start();
        try{
            spao_hoodie_m.join();
            spao_shirt_m.join();
            spao_shirt2_m.join();
            spao_sweat_m.join();
            Log.v(TAG,"join end");
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        Log.v(TAG, "스파오 남자 완료");
    }

}

class crawlingThread extends Thread{
    private static final String TAG = "thread) ";
    String currentPage; //현재 방문중인 페이지
    String baseUrl; //기본 url (여기뒤에 페이지번호 결합)
    String maxPage; //최대 페이지
    Type type;
    Site site;
    Sex sex;

    FirebaseStorage storage;


    int productKey; //상품코드

    public crawlingThread(String baseUrl, String maxPage, Type type, Site site, Sex sex,int startKey){
        this.currentPage="1";
        this.baseUrl=baseUrl;
        this.maxPage = maxPage;
        this.type = type;
        this.site = site;
        this.sex = sex;
        this.productKey = startKey;
        storage = FirebaseStorage.getInstance();
    }

    public void run() {
        try{
            while (Integer.parseInt(currentPage)<=Integer.parseInt(maxPage)){ //최대페이지 방문할때까지

                String href,src; int price;

                //url 조합
                String url = this.baseUrl + this.currentPage;

                //url 읽어오기
                Document doc = Jsoup.connect(url).get();
                Elements part;

                //site별로 크롤링
                switch (site){
                    case SPAO:
                        //selector 설정
                        part = doc.select("ul.prdList.grid3list li div.box");
                        for(Element e : part) {
                            Elements imgSrc = e.select("div.thumbnail div.prdImg a");
                            Elements priceSrc = e.select("div.description div.price_box span.price");

                            //price뽑아오기
                            try {
                                price = Integer.parseInt(priceSrc.text().replace(",", ""));
                            }catch(Exception ex) {
                                continue;
                            }

                            //상품코드, 크롤링한이미지의 href, src (!=path yet)
                            ++this.productKey;

                            href = imgSrc.attr("abs:href");

                            src = imgSrc.select("img").attr("src");
                            src = "http:"+src;

                            //스토리지에 사진 올리기
                            uploadStorageDatabase(getBitmapFromUrl(src),this.productKey,this.type,this.sex,href,price);
                        }
                        break;
                }
                currentPage = Integer.toString(Integer.parseInt(currentPage)+1); //다음페이지로
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    } //run

    //사진 src에서 이미지 뽑아오기
    public Bitmap getBitmapFromUrl(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return  BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //스토리지, 데이터베이스에 등록하기
    public void uploadStorageDatabase(Bitmap img, final int key, final Type type, final Sex sex, final String href, final int price) {

        //스토리지 등록
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        StorageReference storageRef = storage.getReference();
        final StorageReference mountainsRef = storageRef.child(key + ".jpg");
        // Get the data from an ImageView as bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);

        //성공 시 데이터베이스 등록

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return mountainsRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    @SuppressWarnings("VisibleForTests")
                    String path = task.getResult().toString();
                    ProductInfo pr = new ProductInfo(key, type, sex, href, path, price);
                    Log.i("KEY " + pr.getKey(), "on success at " + pr.getSrc());

                    switch (type) {
                        case HOODIE:
                            db.collection("hoodie").document(Integer.toString(pr.getKey())).set(pr).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Success on product registration");
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "Failed to register product", e);
                                        }
                                    });
                            break;
                        case TSHIRT:
                            db.collection("tshirt").document(Integer.toString(pr.getKey())).set(pr).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Success on product registration");
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "Failed to register product", e);
                                        }
                                    });
                            break;
                        case SWEATSHIRT:
                            db.collection("sweatshirt").document(Integer.toString(pr.getKey())).set(pr).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Success on product registration");
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "Failed to register product", e);
                                        }
                                    });
                            break;
                    }
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
}