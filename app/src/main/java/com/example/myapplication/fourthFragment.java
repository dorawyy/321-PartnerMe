package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fourthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fourthFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fourthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fourthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static fourthFragment newInstance(String param1, String param2) {
        fourthFragment fragment = new fourthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        final EditText nameField = view.findViewById(R.id.signup_nameField);
        final EditText languageField = view.findViewById(R.id.signup_Language);
        final EditText classField = view.findViewById(R.id.signup_ClassField);
        final EditText hobbyField = view.findViewById(R.id.signup_Hobbies);
        final Spinner availabilitySpinner = view.findViewById(R.id.signup_AvailabilitySpinner);

        final RequestQueue queue = Volley.newRequestQueue(getContext());

        final JSONObject objectGet = new JSONObject();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            try {
                objectGet.put("email", acct.getEmail());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        final Gson g = new Gson();
        String urlGet = "http://52.91.172.94:3000/user/current-user";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlGet, objectGet,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            User currUser = new User();
                            currUser = g.fromJson(response.get("match result").toString(), User.class);
                            nameField.setText(currUser.getName());
                            languageField.setText(currUser.getLanguage());
                            classField.setText(currUser.get_Class());
                            hobbyField.setText(currUser.getHobbies());
                            switch (currUser.getAvailability()) {
                                case "Morning":
                                    availabilitySpinner.setSelection(0);
                                    break;
                                case "Afternoon":
                                    availabilitySpinner.setSelection(1);
                                    break;
                                case "Evening":
                                    availabilitySpinner.setSelection(2);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        queue.add(jsonObjectRequest);

        final String urlPost = "http://52.91.172.94:3000/user/update";
        final JSONObject objectPost = new JSONObject();
        Button signupButton = view.findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // send post request with fields
                try {
                    objectPost.put("Name", nameField.getText().toString());
                    objectPost.put("Language", languageField.getText().toString());
                    objectPost.put("Class", classField.getText().toString());
                    objectPost.put("Availability", availabilitySpinner.getSelectedItem().toString());
                    objectPost.put("Hobbies", hobbyField.getText().toString());
                    objectPost.put("Email", getActivity().getIntent().getStringExtra("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlPost, objectPost,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if((Boolean) response.get("success")){
                                        Toast.makeText(getActivity(), "Editing complete", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "Editing not complete, please check your internet connection", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
                queue.add(jsonObjectRequest);
            }
        });

        return view;
    }
}