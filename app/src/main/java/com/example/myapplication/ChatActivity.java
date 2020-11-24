package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class ChatActivity extends AppCompatActivity {

    private EditText editText;
    private MessageAdapter messageAdapter;
    private ListView messagesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);

        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        User data = new User();

        ImageButton sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    public void sendMessage() {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            editText.getText().clear();
            // TODO: send to server
        }
    }

    public void onMessage() {
        final User data = null; // TODO: get messages from server
        String receivedMessage = null;
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        boolean belongsToCurrentUser = data.getEmail().equals(acct.getEmail());
        final Message message = new Message(receivedMessage, data, belongsToCurrentUser);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageAdapter.add(message);
                messagesView.setSelection(messagesView.getCount() - 1);
            }
        });
    }
}
