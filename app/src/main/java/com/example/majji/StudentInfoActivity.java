package com.example.majji;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StudentInfoActivity extends AppCompatActivity {
    private static final String TAG = "InfoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        findViewById(R.id.sInfoButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.sInfoButton) {
                UpdateInfo();
            }
        }
    };

    private void UpdateInfo() {
        String number = ((EditText)findViewById(R.id.NumberEditText)).getText().toString();
        String name = ((EditText)findViewById(R.id.NameEditText)).getText().toString();
        String major = ((EditText)findViewById(R.id.MajorEditText)).getText().toString();

        if(name.length()>0 && number.length()>0 && major.length()>0) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //유저 구분 고유 키
            FirebaseFirestore db = FirebaseFirestore.getInstance(); //초기화

            MemberInfo memberInfo = new MemberInfo(number, name, major);
            db.collection("student").document(user.getUid()).set(memberInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startToast("회원 정보를 등록했습니다.");
                            startMainActivity();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            startToast("회원 정보를 등록에 실패했습니다.");
                            Log.w(TAG, "Error adding document", e);
                        }
                    });

            Map<String, Object> categorize= new HashMap<>();            //분류
            categorize.put("category", "student");

            db.collection("categorize").document(user.getUid())
                    .set(categorize)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });


        }else {
            startToast("회원 정보를 입력해 주세요.");


    }
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}


