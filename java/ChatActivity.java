package com.example.firebase_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatActivity extends Activity {

    private TextView TextChatroomName;
    private ListView ListChatroomContent;
    private EditText EditChatroomMassage;
    private Button BtnChatroomsend;

    private String UserName;
    private String ChatRoomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //取得MainActivity之Bundle
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();

        UserName = bundle.getString("UserName");
        ChatRoomName = bundle.getString("ChatRoomName");

        TextChatroomName = (TextView)findViewById(R.id.TextChatroomName);
        ListChatroomContent = (ListView)findViewById(R.id.ListChatroomContent);
        EditChatroomMassage = (EditText)findViewById(R.id.EditChatroomMassage);
        BtnChatroomsend = (Button)findViewById(R.id.BtnChatroomsend);

        TextChatroomName.setText(ChatRoomName);
        BtnChatroomsend.setOnClickListener(BtnChatroomsendListener);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        ListChatroomContent.setAdapter(adapter);

        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference(ChatRoomName);
        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    adapter.add(ds.getKey() + "\n" +  ds.getValue().toString());
                    //adapter.add(ds.getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private View.OnClickListener BtnChatroomsendListener = new View.OnClickListener() {

        public void onClick(View view) {
            String ChatroomMassage = EditChatroomMassage.getText().toString();


            SimpleDateFormat formatter = new SimpleDateFormat("(yyyy-MM-dd HH:mm:ss)");
            Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
            String getTime = formatter.format(curDate);


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(ChatRoomName + "/" + getTime + "        " +UserName);
            myRef.setValue(ChatroomMassage);

            EditChatroomMassage.setText("");
        }
    };
    /*刪除
    FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("CCCC");
            myRef.child("ABCD").removeValue();
     */
    /*新增
    final FirebaseDatabase database = FirebaseDatabase.getInstance();//取得資料庫連結
            DatabaseReference myRef= database.getReference("CCCC");
            myRef.child("ABCD").setValue("224466");
     */
    /*更新
    FirebaseDatabase database = FirebaseDatabase.getInstance();//取得資料庫連結
            DatabaseReference myRef = null;
            myRef= database.getReference("CCCC");
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("DCBA","123456777");//前面的字是child後面的字是要修改的value值
            myRef.updateChildren(childUpdates);
     */
}
