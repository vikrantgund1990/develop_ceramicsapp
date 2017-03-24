package ceramics.com.ceramics.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by vikrantg on 22-06-2016.
 */
public class Utils {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public static String formateTime() {

        Calendar cal = Calendar.getInstance();
        String date = "";
        String time = "";
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");

        try {

            date = fromUser.format(cal.getTime());
            time = timeFormat.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String timeStamp = date+"T"+"00:00:00+"+time;
        return timeStamp;

    }

    public static boolean isNotBlank(String value) {

        if (value == null || TextUtils.isEmpty(value) || value.equalsIgnoreCase("null")) {
            return false;
        }
        return true;
    }
}
