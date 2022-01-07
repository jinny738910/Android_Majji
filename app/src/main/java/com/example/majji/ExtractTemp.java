package com.example.majji;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtractTemp {


    /*     *
     * ExtractTemp 클래스 사용시 parameter넘길때
     * sex => "MAN", "WOMAN"
     * type => "HOODIE", "TSHIRT", "SWEATSHIRT" 꼴로넘겨야함 (대소문자 중요)
     *
     * ex)
     *   ExtractTemp ex = new ExtractTemp("WOMAN", "HOODIE");
     *   ex.setTemp();
     *   ex.deleteTemp();
     *   ec.
     *   setTemp()하면 db collection 에서 사용자uid별로 쿼리결과가 저장됨.
     *   => 컬렉션이름이 사용자uid임 (여러사용자일경우 다른쿼리에 같은 데이터베이스를 쓰면 안되므로)
     *
     *   다음결과를 위해서는 setTemp();에서 결과를 가져오고 가공한 후에는 꼭
     *   ☆☆☆☆☆☆☆ deleteTemp(); 으로 컬렉션을 삭제해줘야함. (매우중요) ☆☆☆☆☆☆☆
     **/

    String TAG = "ExtractTemp";
    ProductInfo pr;
    FirebaseFirestore db;
    //String sex;
    //String type;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    ExtractTemp() {
        db = FirebaseFirestore.getInstance();
        pr = new ProductInfo();
    }

    public void setTemp(String sex, String type) {
        db.collection(sex)
                .whereEqualTo("type", type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                pr.setAll(document.get("key").toString(), document.get("type").toString(), document.get("sex").toString(), document.get("href").toString(), document.get("src").toString(), document.get("price").toString());
                                db.collection(user.getUid()).document(Integer.toString(pr.getKey())).set(pr).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void deleteTemp(String sex, String type) {
        db.collection(user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection(user.getUid()).document(document.getId()).delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("DELETED: ", "SUCCESS");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("DELETED: ", "FAIL", e);
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void getTemp(List<DocumentSnapshot> ListofFireStore,String sex, String type) {
        Task<QuerySnapshot> docRef = db.collection(user.getUid()).whereEqualTo("type", type).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            Log.d("mytag", "error==================");
                        } else {
                            //String now = queryDocumentSnapshots.getDocuments().get(0).getString("src");
                            int i = 0;
                            while(true) {
                                try {
                                    ListofFireStore.add(queryDocumentSnapshots.getDocuments().get(i++));
                                } catch (Exception e){
                                    Log.d("mytag",e.getMessage() + "endofdata");
                                    break;
                                }
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("mytag","===========error" + e.getMessage());
                    }
                });
    }
}
