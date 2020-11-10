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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link firstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class firstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    /*
    public firstFragment() {
        // Required empty public constructor
    }
    */

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment firstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static firstFragment newInstance(String param1, String param2) {
        firstFragment fragment = new firstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
         */
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
            String url = "http://52.91.172.94:3000/matching/getmatch";
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            MatchResult[] matchList = new MatchResult[0];
                            try {
                                matchList = g.fromJson(response.get("match result").toString(), MatchResult[].class);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String str = "";
                            for (MatchResult user : matchList) {
                                str = str.concat("\n\n\nName: " + user.getUser().getName() +
                                        "\nClass:  " + user.getUser().get_Class() +
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