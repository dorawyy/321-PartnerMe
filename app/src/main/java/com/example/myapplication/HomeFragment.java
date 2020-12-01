package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        final JSONObject object = new JSONObject();
        if (GoogleSignIn.getLastSignedInAccount(getContext()) != null) {
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
            try {
                object.put("email", acct.getEmail());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            View view = inflater.inflate(R.layout.fragment_first, container, false);
            // Inflate the layout for this fragment
            final TextView textView = (TextView) view.findViewById(R.id.firstFragmentTextView);
            final Gson g = new Gson();

// ...
// Instantiate the RequestQueue.
            final RequestQueue queue = Volley.newRequestQueue(this.getContext());
            String url = getResources().getString(R.string.matchurl);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JsonResults.MatchResult[] matchList = new JsonResults.MatchResult[0];
                            try {
                                matchList = g.fromJson(response.get("match result").toString(), JsonResults.MatchResult[].class);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String str = "";
                            for (JsonResults.MatchResult user : matchList) {
                                str = str.concat("\n\n\nName: " + user.getUser().getName() +
                                        "\nClass:  " + user.getUser().getUserClass() +
                                        "\nLanguage:  " + user.getUser().getLanguage() +
                                        "\nAvailability:  " + user.getUser().getAvailability() +
                                        "\nHobbies:  " + user.getUser().getHobbies() +
                                        "\nSimilarity:  " + user.getSimilarity()
                                );
                            }
                            textView.setText(str);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);
                }
            });

// Add the request to the RequestQueue.
            view.findViewById(R.id.callDBButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    queue.add(jsonObjectRequest);
                }
            });
            return view;
    }
}