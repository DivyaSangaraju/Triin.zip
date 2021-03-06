package com.tripnetra.tnadmin.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;

import com.tripnetra.tnadmin.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Utils {

    private static AlertDialog myAlertDialog;
    private static AlertDialog.Builder alertDialogBuilder = null;

    public static boolean isDataConnectionAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager)
                Objects.requireNonNull(context.getSystemService(Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo();
        return activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting();
    }

    public static void setSingleBtnAlert(final Activity activity, String msg, String btnName, final boolean finshact) {
        if (myAlertDialog != null && myAlertDialog.isShowing()) {
            myAlertDialog.dismiss();
            myAlertDialog = null;
        }
        alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setCancelable(false).setMessage(msg).setPositiveButton(btnName, (dialog, which) -> {
            dialog.dismiss();
            if (finshact) {
                activity.finish();
            }
        });
        activity.runOnUiThread(() -> {
            if (!activity.isFinishing()) {
                Utils.myAlertDialog = Utils.alertDialogBuilder.create();
                Utils.myAlertDialog.show();
            }
        });
    }

    public static String ChangeDateFormat(String dates, int  format) {

        if(dates!=null) {
            String ss;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = sdf.parse(dates);

                switch (format) {
                    case 1: //20-SEP-2018
                        sdf.applyPattern("dd-MMM-yyyy");
                        break;
                    case 2: //WED, SEP 20, 2018
                        sdf.applyPattern("EEE, MMM dd, yyyy");
                        break;
                    case 3: //SEP 20
                        sdf.applyPattern("MMM dd");
                        break;
                    default://2018-09-22
                        sdf.applyPattern("yyyy-MM-dd");
                        break;
                }
                ss = sdf.format(date);
            } catch (ParseException e) {
                //e.printStackTrace();
                ss = dates;
            }

            return ss;
        }else{
            return "";
        }
    }

    public static Date StrtoDate (String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


       /* SimpleDateFormat sdf ;

        switch (format) {
            case 1:
                sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                break;
            case 2:
                sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault());
                break;
            default: // case 0
                sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                break;
        }
        try {
            return sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }*/


    }


    public static Date StrtoDate (int format,String string) {
        SimpleDateFormat sdf ;

        switch (format) {
            case 1:
                sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                break;
            case 2:
                sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault());
                break;
            default: // case 0
                sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                break;
        }
        try {
            return sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String DatetoStr (Object date,int  format) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String ss ;
        switch (format) {
            case 1: //20-SEP-2018
                sdf.applyPattern("dd-MMM-yyyy");
                break;
            case 2: //WED, SEP 20, 2018
                sdf.applyPattern("EEE, MMM dd, yyyy");
                break;
            case 3: //SEP 20
                sdf.applyPattern("MMM dd");
                break;
            default://2018-09-22
                sdf.applyPattern("yyyy-MM-dd");
                break;
        }
        ss = sdf.format(date);

        return ss;

    }


    /*public static void VolleyReqQueue(StringRequest strreq, final Activity activity){
        strreq.setShouldCache(false);
        strreq.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            HttpStack stack;
            try {
                stack = new HurlStack(null, new TLSSocketFactory());
            } catch (KeyManagementException | NoSuchAlgorithmException e) {
                stack = new HurlStack();
            }
            Volley.newRequestQueue(activity, stack).add(strreq);
        } else {
            Volley.newRequestQueue(activity).add(strreq);
        }
    }
*/



    public static int getscreenwidth(Activity activity){

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        return dm.widthPixels;

    }

    @SuppressLint("NewApi")
    public static void SendNotification(final Activity activity,String msg){
        NotificationManager notmgr = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notfctn = new Notification.Builder(activity)
                .setContentTitle("Tripnetra Booking Confirmation")
                .setContentText(msg)
                .setLargeIcon(BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo1))
                .setSmallIcon(R.drawable.load5)
                .build();

        notfctn.flags |= Notification.FLAG_AUTO_CANCEL;
        assert notmgr != null;
        notmgr.notify(0, notfctn);
    }


}
