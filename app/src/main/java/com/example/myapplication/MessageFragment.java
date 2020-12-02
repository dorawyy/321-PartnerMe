package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
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
import java.util.List;
import java.util.Objects;


public class MessageFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_chat_list, container, false);

        final Gson g = new Gson();
        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        final JSONObject object = new JSONObject();
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://52.91.172.94:3000/messages/messagelist";
        try {
            object.put("currentUser", acct.getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final List<String> email = new ArrayList<>();
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JsonResults.MessageListResult[] emailList = new JsonResults.MessageListResult[0];
                        try {
                            emailList = g.fromJson(response.get("listofusers").toString(), JsonResults.MessageListResult[].class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (JsonResults.MessageListResult user : emailList) {
                            email.add(user.getEmail());
                        }
                        setView(email, acct);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { }
        });
        queue.add(jsonObjectRequest);

        return view;
    }

    private void setView (final List<String> email, final GoogleSignInAccount acct) {
        CustomList adapter = new CustomList(getActivity(), email.toArray(new String[0]));
        ListView list = (ListView) requireView().findViewById(R.id.chat_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("currentUser", acct.getEmail());
                intent.putExtra("otherUser", email.get(i));
            }
        });
    }
}