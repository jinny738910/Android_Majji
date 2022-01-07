
package com.example.majji;
/*
import android.app.Activity;
import android.util.Log;

public class Scrap_Baon extends Activity {
    private static String TAG = "TestActivity";
    public void collectProduct(){
//바온 수집
        //여자
        crawlingThread baon_hoodie= new crawlingThread(app.getRes().getString(R.string.baon_hoodie_url),app.getRes().getString(R.string.baon_hoodie_max), Type.HOODIE, Site.SPAO, Sex.WOMAN);
        crawlingThread baon_shirt = new crawlingThread(app.getRes().getString(R.string.spao_shortTshirt_url),app.getRes().getString(R.string.baon_shortTshirt_max), Type.TSHIRT,Site.SPAO,Sex.WOMAN );
        crawlingThread baon_shirt2 = new crawlingThread(app.getRes().getString(R.string.spao_longTshirt_url),app.getRes().getString(R.string.baon_longTshirt_max), Type.TSHIRT,Site.SPAO,Sex.WOMAN );
        crawlingThread baon_sweat = new crawlingThread(app.getRes().getString(R.string.spao_sweatShirt_url),app.getRes().getString(R.string.baon_sweatShirt_max),Type.SWEATSHIRT,Site.SPAO,Sex.WOMAN);

        baon_hoodie.start();
        baon_shirt.start();
        baon_shirt2.start();
        baon_sweat.start();

        try{
            baon_hoodie.join();
            baon_shirt.join();
            baon_shirt2.join();
            baon_sweat.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        Log.v(TAG, "바온 완료");

    }

}
*/