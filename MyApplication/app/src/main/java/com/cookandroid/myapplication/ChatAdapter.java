package com.cookandroid.myapplication;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends BaseAdapter {

    ArrayList<MessageItem> messageItems;
    LayoutInflater layoutInflater;
    String myname = "me";

    public ChatAdapter(ArrayList<MessageItem> messageItems, LayoutInflater layoutInflater){
        this.messageItems = messageItems;
        this.layoutInflater = layoutInflater;
    }

    public int getCount() {         //전체 데이터 개수
        return messageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        MessageItem item = messageItems.get(position);

        View itemView = null;

        if (item.getName().equals(myname)) {
            itemView = layoutInflater.inflate(R.layout.my_msgbox, viewGroup, false);
        } else {
            itemView = layoutInflater.inflate(R.layout.other_msgbox, viewGroup, false);
        }
//만들어진 itemView에 값들 설정
        CircleImageView iv = itemView.findViewById(R.id.iv);
        TextView tvName = itemView.findViewById(R.id.tv_name);
        TextView tvMsg = itemView.findViewById(R.id.tv_msg);
        TextView tvTime = itemView.findViewById(R.id.tv_time);


        iv.setImageResource(R.drawable.img);
        tvName.setText(item.getName());
        tvMsg.setText(item.getMessage());
        tvTime.setText(item.getTime());

        Glide.with(itemView).load(item.getPofileUrl()).into(iv);

        return itemView;
    }
}


//    private Context context;
//    private int layout;
//    private ArrayList<ChatVO> chatData;
//    private LayoutInflater inflater;
//
//
//    public ChatAdapter(Context applicationContext, int talklist, ArrayList<ChatVO> list) {
//        this.context = applicationContext;
//        this.layout = talklist;
//        this.chatData = list;
//        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//}




