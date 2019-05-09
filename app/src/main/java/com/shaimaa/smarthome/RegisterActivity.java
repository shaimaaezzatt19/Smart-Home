package com.shaimaa.smarthome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
    }

    public void register(View view) {
        EditText usernameET = (EditText) findViewById(R.id.register_username_edit);
        EditText passwordET = (EditText) findViewById(R.id.register_password_edit);
        EditText secPassET = (EditText) findViewById(R.id.register_sec_pass_edit);

        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String secPass = secPassET.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Enter Your Username", Toast.LENGTH_LONG).show();
        } else {
            if (password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Enter Your Password", Toast.LENGTH_LONG).show();
            } else {
                if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "The Password should be more than 6 characters", Toast.LENGTH_LONG).show();
                } else {
                    if (!password.equals(secPass)) {
                        Toast.makeText(RegisterActivity.this, "The Passwords should match", Toast.LENGTH_LONG).show();
                    } else {
                        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                        progressDialog.setMessage("Signing in...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        String registerUrl = Links.URL + Links.API + Links.REGISTER;

                        JSONObject postBody = new JSONObject();
                        try {
                            postBody.put("username", username);
                            postBody.put("password", password);
                            postBody.put("device_token", "device_token");
                            postBody.put("type", "android");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, registerUrl, postBody, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String message = response.getString("message");
                                    if (message.equals("success")) {
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Connection Error", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        System.out.println(response);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(RegisterActivity.this, "Connection Error", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                System.out.println(error);
                            }
                        });
                        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                        requestQueue.add(jsonObjectRequest);
                    }
                }
            }
        }
    }

}
