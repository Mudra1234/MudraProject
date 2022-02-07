package com.cookandroid.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity2 extends AppCompatActivity {
    String etName = "Mudra";            // bot name
    CircleImageView ivProfile;
    //TextView myname = (TextView) findViewById(R.id.tv_name);


    Uri imgUri;     //프로필 이미지 경로 uri (for bot)

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button movieBtn = (Button) findViewById(R.id.btn1);
        Button personBtn = (Button) findViewById(R.id.btn2);

        movieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        Chatting.class);
                startActivity(intent);      // 화면 넘어감
            }
        });

        personBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        Chatting.class);
                startActivity(intent);      // 화면 넘어감
            }
        });
    }

    void saveData(){
        //G.nickName = myname.toString();     // 사용자이름 or 봇이름

    }

}
