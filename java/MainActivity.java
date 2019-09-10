package com.example.firebase_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    private EditText EditUserName;
    private EditText EditChatRoomName;
    private Button BtnLogin;

    private String tempUserName;
    private String tempChatRoomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditUserName = (EditText)findViewById(R.id.EditUserName);
        EditChatRoomName = (EditText)findViewById(R.id.EditChatRoomName);
        BtnLogin = (Button) findViewById(R.id.BtnLogin);

        BtnLogin.setOnClickListener(BtnLoginLister);
    }

    private View.OnClickListener BtnLoginLister = new View.OnClickListener() {

        public void onClick(View view) {


            if(EditUserName.getText().toString().length() == 0) tempUserName = "User";
                else tempUserName = EditUserName.getText().toString();
            if(EditChatRoomName.getText().toString().length() == 0) tempChatRoomName = "General";
                else tempChatRoomName = EditChatRoomName.getText().toString();

            //導向ChatActivity
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,ChatActivity.class);

            Bundle bundle=new Bundle();
            bundle.putString("UserName", tempUserName);
            bundle.putString("ChatRoomName", tempChatRoomName);

            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
}
