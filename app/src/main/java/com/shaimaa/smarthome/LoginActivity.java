package com.shaimaa.smarthome;

   import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

    public class MainActivity extends AppCompatActivity {

    private RecyclerView notificationRV;
    private List<NotificationModel> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationList = new ArrayList<>();
        notificationRV = (RecyclerView) findViewById(R.id.temperature_recycler);
        notificationRV.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        getNotifications();
    }

    public void getNotifications() {
        String apiToken = ProfileInformation.getApiToken(MainActivity.this);

        String getNotificationsURL = Links.URL + Links.API + Links.Notifications + "?api_token=" + apiToken;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getNotificationsURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    if (message.equals("success")) {
                        JSONArray notifications = response.getJSONArray("notifications");
                        for (int i = 0; i < notifications.length(); i++) {
                            JSONObject notification = notifications.getJSONObject(i);
                            String temp = notification.getString("temp");
                            String date = notification.getString("date");

                            notificationList.add(new NotificationModel(temp, date));
                        }

                        notificationRV.setAdapter(new NotificationsAdapter(MainActivity.this, notificationList));

                    } else {
                        Toast.makeText(MainActivity.this, "Connection Error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Connection Error", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);

    }

}
