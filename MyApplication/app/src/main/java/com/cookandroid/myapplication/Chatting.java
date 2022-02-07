package com.cookandroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Chatting extends AppCompatActivity {


    String answer = "umm";              // 답변의 초기값은 "모르겠습니다" 로 설정
    //ArrayList<String> array = new ArrayList<>();
    EditText et;
    ListView listView;

    ArrayList<MessageItem> messageItems = new ArrayList<>();
    ChatAdapter adapter;

    //Firebase Database 관리 객체참조변수
    FirebaseDatabase firebaseDatabase;

    // chat 노드의 참조객체 참조변수
    DatabaseReference chatRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        et = findViewById(R.id.editText);
        listView = findViewById(R.id.listview);
        adapter = new ChatAdapter(messageItems, getLayoutInflater());
        listView.setAdapter(adapter);

        //Firebase DB관리 객체와 'chat'노드 참조객체 얻어오기
        firebaseDatabase = FirebaseDatabase.getInstance();
        chatRef = firebaseDatabase.getReference("chat");

        //firebaseDB에서 채팅 메세지들 실시간 읽어오기..
        //'chat'노드에 저장되어 있는 데이터들을 읽어오기
        //chatRef에 데이터가 변경되는 것으 듣는 리스너 추가
        chatRef.addChildEventListener(new ChildEventListener() {
            //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //새로 추가된 데이터(값 : MessageItem객체) 가져오기
                MessageItem messageItem = dataSnapshot.getValue(MessageItem.class);

                //새로운 메세지를 리스뷰에 추가하기 위해 ArrayList에 추가
                messageItems.add(messageItem);

                //리스트뷰를 갱신
                adapter.notifyDataSetChanged();
                listView.setSelection(messageItems.size() - 1); //리스트뷰의 마지막 위치로 스크롤 위치 이동
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button yesBtn = (Button) findViewById(R.id.yesBtn);
        Button noBtn = (Button) findViewById(R.id.noBtn);
        Button ummBtn = (Button) findViewById(R.id.neutBtn);      // 모르겠습니다 버튼
        Button sendBtn = (Button) findViewById(R.id.sendBtn);
        Button chatBtn = (Button) findViewById(R.id.chatBtn);
        EditText edtext = (EditText) findViewById(R.id.editText);

        sendBtn.setEnabled(false);          //  사용자가 텍스트 보내는 버튼 비활성화 (초기상태)

        yesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                answer = "yes";             // 데이터 탐색 때 넘겨줄 답변정보 (for firebase)
            }
        });


        noBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                answer = "no";
            }
        });


        ummBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                answer = "umm";
            }
        });


        chatBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendBtn.setEnabled(true);           // 답변 버튼 클릭시, 사용자의 텍스트 보내기 버튼 활성화
            }
        });


//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String text = edtext.getText().toString();
//                answer = text;              // 사용자의 답변 문구 보내기 (to Firebase && For 자연어처리)
//            }
//        });

        System.out.println(answer);

    }

    public void clickSend(View view) {
        // firebase DB에 저장할 닉네임, 메세지 값
        String nickName = "me";
        String message = et.getText().toString();
        String img = "@drawble/img.png";

        // 메세지 작성 시간 문자열로
        Calendar calendar = Calendar.getInstance();     // 현재시간 객체
        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);      // 13:12

        // firebase DB에 저장할 값 (MessageItem 객체) 설정
        MessageItem messageItem = new MessageItem(nickName, message, time);
        //'char'노드에 MessageItem객체를 통해
        chatRef.push().setValue(messageItem);

        //EditText에 있는 글씨 지우기
        et.setText("");

        //소프트키패드를 안보이도록..
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        //처음 시작할때 EditText가 다른 뷰들보다 우선시 되어 포커스를 받아 버림.
        //즉, 시작부터 소프트 키패드가 올라와 있음.

    }
}