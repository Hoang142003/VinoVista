package com.example.vinovista.Messaging;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Notification {
    private static Context context;
    private static String keyServer= "AAAAxxxHzHs:APA91bFlXInlYVshsp49atk6VkEstGf3gJ1_10WSvtMrdOJoePHu91edwFvog8PuUy9DU7ACMH3IHlT5iCt9YBV0qB-mnOd5MguCVSf9-PTmg4VsAbnXR_zCKuQAzQLoR_HD6F14e6C_";
    private static String url=" https://fcm.googleapis.com/fcm/send";
    public static void setContext(Context context1){
        context=context1;
    }
    public static void getSendNotificationOrderSuccessFull(String fcmToken,String title,String content,String tag){
        JSONObject jsonNotificationData = new JSONObject();
        try {
            jsonNotificationData.put("title",title);
            jsonNotificationData.put("body",content);
            jsonNotificationData.put("tag",tag);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonRequestData = new JSONObject();
        try {
            jsonRequestData.put("to",fcmToken);
            jsonRequestData.put("notification",jsonNotificationData);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
             @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Yêu cầu gửi thông báo thất bại: "+ error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "key=" + keyServer);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
