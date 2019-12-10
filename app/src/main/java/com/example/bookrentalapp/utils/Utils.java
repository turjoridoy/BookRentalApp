package com.example.bookrentalapp.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.example.bookrentalapp.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import id.zelory.compressor.Compressor;

public class Utils {

    public static String taskFilter = "";
    public static String taskFilterName = "";
    public static void askLocationPermission(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {
            // Write you code here if permission already given.
        }
    }


    public static void hideKeyboardFrom(Activity context) {
        View view = context.findViewById(android.R.id.content).getRootView();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    public static void AllChecker(Context c) {

        internetChecker(c);
    }


    public static boolean isEmpty(String a) {


        return a.equalsIgnoreCase("") || a.length() < 1;
    }

    public static String isoToDate(String Iso) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(Iso);

            String year = value.getYear() + "";
            return value.getDate() + "-" + (value.getMonth() + 1) + "-20" + year.substring(year.length() - 2);

        } catch (Exception e) {
            return "--";
        }


    }

    public static String getCurrentDateInIso() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());

        return nowAsISO;

    }


    public static String getDuration(String start, String End) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(start);
            SimpleDateFormat formatterx = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            formatterx.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date valuex = formatterx.parse(End);


            return printDifference(value, valuex);


        } catch (Exception e) {
            return "--";
        }
    }

    public static String printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();


        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        return elapsedHours + "";
    }


    public static String getMyTimeDateInIso(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String nowAsISO = df.format(date);

        return nowAsISO;

    }

    public static String isoToTime(String iso) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(iso);

            String _24HourTime = value.getHours() + ":" + value.getMinutes();

            try {

                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                Date _24HourDt = _24HourSDF.parse(_24HourTime);
                System.out.println(_24HourDt);
                _24HourTime = _12HourSDF.format(_24HourDt);
                return _24HourTime;
            } catch (final ParseException e) {
                return value.getHours() + ":" + value.getMinutes() + ":" + value.getYear();
            }


        } catch (Exception e) {
            return "--";
        }
    }

    public static String chatDate(String iso) {
        Date todaysDate = Calendar.getInstance().getTime();

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = formatter.parse(iso);
            String _24HourTime = date.getHours() + ":" + date.getMinutes();

            try {

                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                Date _24HourDt = _24HourSDF.parse(_24HourTime);
                System.out.println(_24HourDt);
                _24HourTime = _12HourSDF.format(_24HourDt);
            } catch (final ParseException e) {
                e.printStackTrace();
            }

            if (date.getDate() == todaysDate.getDate()) {


                return "Today " + _24HourTime;

            } else {
                String year = date.getYear() + "";
                return " " + date.getDate() + "-" + (date.getMonth() + 1) + "-20" + year.substring(year.length() - 2) + " " + _24HourTime;
            }


        } catch (Exception e) {
            return "--";
        }


    }

    public static String billDate(String iso) {
        Date todaysDate = Calendar.getInstance().getTime();

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = formatter.parse(iso);
            String monthname = (String) android.text.format.DateFormat.format("MMMM", date);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);


            if (date.getDate() == todaysDate.getDate()) {
                return "Today ";

            } else {
                String year = date.getYear() + "";
                return " " + date.getDate() + " " + monthname.substring(0, 3) + " " + cal.get(Calendar.YEAR);
            }


        } catch (Exception e) {
            return "--";
        }


    }

    public static String mapDate(String iso) {
        Date todaysDate = Calendar.getInstance().getTime();

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = formatter.parse(iso);
            String _24HourTime = date.getHours() + ":" + date.getMinutes();

            try {

                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                Date _24HourDt = _24HourSDF.parse(_24HourTime);
                System.out.println(_24HourDt);
                _24HourTime = _12HourSDF.format(_24HourDt);
            } catch (final ParseException e) {
                e.printStackTrace();
            }

            if (date.getDate() == todaysDate.getDate()) {


                return "" + _24HourTime;

            } else {

                return "" + _24HourTime;
            }


        } catch (Exception e) {
            return "--";
        }


    }


    public static void gpsChecker(final Context c) {
        if (!isGPS(c)) {
            new LovelyStandardDialog(c, LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTopColorRes(R.color.colorPrimaryDark)
                    .setButtonsColorRes(R.color.colorPrimary)
                    .setIcon(c.getResources().getDrawable(R.drawable.icon_maps_place))
                    .setTitle("GPS Off")
                    .setMessage("Please turn on device !")
                    .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gpsChecker(c);
                        }
                    })
                    .show();


        }
    }


    public static void internetChecker(Context c) {
        if (!isNetworkAvailable(c)) {
            new LovelyStandardDialog(c, LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTopColorRes(R.color.colorPrimaryDark)
                    .setButtonsColorRes(R.color.colorPrimary)
                    .setIcon(c.getResources().getDrawable(R.drawable.icon_action_language))
                    .setTitle("Internet Not Available !")
                    .setMessage("Please turn on internet.")
                    .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();


        }
    }

    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            activeNetworkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isGPS(Context c) {
        LocationManager manager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            statusOfGPS = Objects.requireNonNull(manager).isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return statusOfGPS;
    }

    public static Map<String, String> convertJSONObj(JSONObject jSONObject) {
        Map<String, String> map = null;
        String mString = null;
        if (jSONObject == null) {
            return null;
        }
        Map<String, String> hashMap = new HashMap();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            try {
                mString = jSONObject.getString(str);
            } catch (JSONException e) {
            }
            hashMap.put(str, mString);
        }
        return hashMap;
    }



    public static String get12(String put24) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(put24);
            System.out.println(dateObj);
            String get12 = new SimpleDateFormat("hh:mm aa").format(dateObj);

            return get12;
        } catch (final ParseException e) {
            e.printStackTrace();
            return put24;
        }
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();

        String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        String day = days[(calendar.get(Calendar.DAY_OF_WEEK) - 1)];

        String date = day + ", " + calendar.get(Calendar.DAY_OF_MONTH) + "-" + (new DateFormatSymbols().getMonths()[calendar.get(Calendar.MONTH)]) + "-" + calendar.get(Calendar.YEAR);
        return date;
    }



    public boolean containsOnlyID(final JSONArray list, final int id) {
        try {
            for (int i = 0; i < list.length(); i++) {
                if (list.getInt(i) == id) {
                    return true;
                }
            }

        } catch (Exception e) {

        }
        return false;
    }


    public void uploadAddimage(Context context, String url, File file) {


        ProgressDialog progress = new ProgressDialog(context);
        //Toast.makeText(FeedbackActivity.this, "Data is being sent !", Toast.LENGTH_SHORT).show();
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
        //progress.setMessage("Uploading Data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();


        VolleyMultipartRequest feedbackSubmitRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                response -> {

                    progress.dismiss();


                    try {
                        String imageURL = new String(response.data, HttpHeaderParser.parseCharset(response.headers));


                    } catch (Exception e) {

                    }
                },
                error -> {
                    progress.dismiss();

                    Log.e("errrrr", "" + error.getMessage());
//                                   Log.e("errrrrLL", "E: " + error.toString() + "," + error.networkResponse != null && error.networkResponse.data != null ? new String(error.networkResponse.data) : "All null");
                }) {


            @Override
            public Map<String, String> getHeaders() {
                PreferenceSaver ps = new PreferenceSaver(context);
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT " + ps.getToken());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                try {

                    File files = new Compressor(context).compressToFile(file);


                    params.put("image_", new DataPart("name" + ".png", getFileDataFromDrawable(files)));
                } catch (Exception e) {
                    Log.e("!!!", e.toString());
                }


                return params;
            }
        };

        Volley.newRequestQueue(context).add(feedbackSubmitRequest);


    }

    public byte[] getFileDataFromDrawable(File f) {


        int size = (int) f.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(f));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bytes;
    }



}
