package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main);



        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String url = "http://52.91.172.94:3000/auth/create";
        final JSONObject object = new JSONObject();

        final EditText nameField = findViewById(R.id.signup_nameField);
        final EditText languageField = findViewById(R.id.signup_Language);
        final EditText classField = findViewById(R.id.signup_ClassField);
        final EditText hobbyField = findViewById(R.id.signup_Hobbies);
        final Spinner availabilitySpinner = findViewById(R.id.signup_AvailabilitySpinner);

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // send post request with fields
                try {
                    object.put("Name", nameField.getText().toString());
                    object.put("Language", languageField.getText().toString());
                    object.put("Class", classField.getText().toString());
                    object.put("Availability", availabilitySpinner.getSelectedItem().toString());
                    object.put("Hobbies", hobbyField.getText().toString());
                    object.put("Email", getIntent().getStringExtra("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if((Boolean) response.get("success")){
                                        // successful signup with get us to the main activity
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(SignUpActivity.this, "Sign up not complete, please make sure fields are not empty or your internet connection", Toast.LENGTH_SHORT).show();
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
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}
