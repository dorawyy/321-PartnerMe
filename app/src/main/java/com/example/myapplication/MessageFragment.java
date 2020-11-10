package com.example.myapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
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


public class MessageFragment extends Fragment {

    public JsonObjectRequest currentUserObject(final EditText nameField, final EditText languageField, final EditText classField, final EditText hobbyField, final Spinner availabilitySpinner){
        final JSONObject objectGet = new JSONObject();
        final Gson g = new Gson();
        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            try {
                objectGet.put("email", acct.getEmail());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String urlGet = "http://52.91.172.94:3000/user/current-user";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlGet, objectGet,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            User currUser = new User();
                            currUser = g.fromJson(response.get("user").toString(), User.class);
                            nameField.setText(currUser.getName());
                            languageField.setText(currUser.getLanguage());
                            classField.setText(currUser.getUserClass());
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
                                default:
                                    availabilitySpinner.setSelection(0);
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
        return jsonObjectRequest;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);

        final EditText nameField = view.findViewById(R.id.signup_nameField);
        final EditText languageField = view.findViewById(R.id.signup_Language);
        final EditText classField = view.findViewById(R.id.signup_ClassField);
        final EditText hobbyField = view.findViewById(R.id.signup_Hobbies);
        final Spinner availabilitySpinner = view.findViewById(R.id.signup_AvailabilitySpinner);

        final RequestQueue queue = Volley.newRequestQueue(getContext());

        final JSONObject objectGet = new JSONObject();
        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            try {
                objectGet.put("email", acct.getEmail());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        queue.add(currentUserObject(nameField, languageField, classField, hobbyField, availabilitySpinner));

        final String urlPost = "http://52.91.172.94:3000/user/update";
        final JSONObject objectPost = new JSONObject();
        Button signupButton = view.findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // send post request with fields
                try {
                    objectPost.put("name", nameField.getText().toString());
                    objectPost.put("language", languageField.getText().toString().replaceAll(" ", "").toUpperCase());
                    objectPost.put("class", classField.getText().toString().replaceAll(" ", "").toUpperCase());
                    objectPost.put("availability", availabilitySpinner.getSelectedItem().toString());
                    objectPost.put("hobbies", hobbyField.getText().toString());
                    objectPost.put("email", acct.getEmail());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final JsonObjectRequest updateObject = new JsonObjectRequest(Request.Method.POST, urlPost, objectPost,
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
                queue.add(updateObject);
            }
        });

        return view;
    }
}