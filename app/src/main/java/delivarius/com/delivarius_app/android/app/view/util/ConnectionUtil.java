package delivarius.com.delivarius_app.android.app.view.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;

public class ConnectionUtil {

    public static final String DELIVARIUS_ADDRESS = "https://delivarius.herokuapp.com";

    private static boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isInternetAvailable(Activity activity) {
        return isAddressAvailable("google.com", activity);
    }

    public static boolean isDelivariusServerAvailable(Activity activity) {
        return isAddressAvailable(DELIVARIUS_ADDRESS, activity);
    }

    private static boolean isAddressAvailable(String address, Activity activity){
        try {
            if(isNetworkConnected(activity)) {
                InetAddress ipAddr = InetAddress.getByName(address);
                return !ipAddr.equals("");
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }



}
