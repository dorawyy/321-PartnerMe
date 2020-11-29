package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {

    private EditText editText;
    private MessageAdapter messageAdapter;
    private ListView messagesView;
    private User currUser;
    private User otherUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        editText = (EditText) findViewById(R.id.editText);

        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        this.currUser = (User) getIntent().getSerializableExtra("currUser");
        this.otherUser = (User) getIntent().getSerializableExtra("otherUser");

        ImageButton sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final JSONObject object = new JSONObject();
                final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://52.91.172.94:3000//messages/getchat";
                try {
                    object.put("currUser", currUser.getEmail());
                    object.put("otherUser", otherUser.getEmail());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                onMessage(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { }
                });
                queue.add(jsonObjectRequest);
            }
        }, 0, 1000);
    }

    public void sendMessage() {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            editText.getText().clear();

            final JSONObject object = new JSONObject();
            final RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
            String url = "http://52.91.172.94:3000//messages/sendmessage";
            try {
                object.put("currUser", currUser.getEmail());
                object.put("otherUser", otherUser.getEmail());
                object.put("message", message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onMessage(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { }
                });
            queue.add(jsonObjectRequest);
        }
    }

    public void onMessage(final JSONObject list) {
        final Gson g = new Gson();
        MessageResult chatList;
        chatList = g.fromJson(list.toString(), MessageResult.class);
        final List<Message> messageList = new ArrayList<>(Arrays.asList(chatList.getChat()));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageAdapter.update(messageList);
                messagesView.setSelection(messagesView.getCount() - 1);
            }
        });
    }
}
