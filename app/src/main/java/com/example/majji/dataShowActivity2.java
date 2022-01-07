package com.example.majji;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class dataShowActivity2 extends AppCompatActivity {
    private TextView textView1;
    String type;
    String msg;
    String name, number, major;
    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<MemberInfo> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_show2);
        findViewById(R.id.databutton).setOnClickListener(onClickListener);
        TextView textView1 = (TextView) findViewById(R.id.noticeText) ;
        getData();

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<MemberInfo>();
        noticeList.add(new MemberInfo("", "", ""));
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(adapter);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.databutton:
                    startToast(major);
                    startToast(name);
                    startToast(number);
                    noticeList = new ArrayList<MemberInfo>();
                    noticeList.add(new MemberInfo(major, name, number));
                    noticeList.add(new MemberInfo(major, name, number));
                    noticeList.add(new MemberInfo(major, name, number));
                    noticeList.add(new MemberInfo(major, name, number));
                    adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
                    noticeListView.setAdapter(adapter);
                    break;
            }
        }
    };

    private void getData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance(); //초기화
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //유저 구분 고유 키

        DocumentReference docRef = db.collection("student").document(user.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                MemberInfo member = documentSnapshot.toObject(MemberInfo.class);
                major = member.getMajor();
                name = member.getName();
                number = member.getNumber();
            }
        });
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}

