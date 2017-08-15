package com.example.truongle.rss;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by TruongLe on 08/08/2017.
 */

//tokenrefresh
//fUbJlB7MtgY:APA91bGRSA1fQ2IvfK5kmGgjoWit0FGqE35VKFA8lUMymKS5sLpU22LuATKXf97Xsd-3pNfengqk8K3WKjunQpC4-R6wengyZrdxWGP0f9HoEiTsrXbZHAV6P1gwi__y32E6R_y0HLCM
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        try {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Refreshed token: " + refreshedToken);
            sendRegistrationToServer(refreshedToken);
        }catch (Exception e){
            e.printStackTrace();
        }
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

    }
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
