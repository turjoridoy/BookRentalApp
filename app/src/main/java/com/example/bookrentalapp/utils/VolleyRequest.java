package com.example.bookrentalapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.example.bookrentalapp.utils.Utils.internetChecker;
import static com.example.bookrentalapp.utils.Utils.internetChecker;

public class VolleyRequest {


    public Context mContext;
    ProgressDialog progress;
    PreferenceSaver ps;
    RequestQueue queue;
    private MyServerListener listener;
    private MyServerListenerArray listenerarray;

    public VolleyRequest(Context context) {
        mContext = context;
        queue = VolleyController.getInstance(mContext).getRequestQueue();


        this.listener = null;
        this.listenerarray = null;
        ps = new PreferenceSaver(context);
        progress = new ProgressDialog(context);
        progress.setMessage("Loading. . ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);

        internetChecker(mContext);


    }

    public void setListenerarray(MyServerListenerArray listener) {
        this.listenerarray = listener;
    }

    public void setListener(MyServerListener listener) {
        this.listener = listener;
    }

    public void VolleyPost(String requestBody, String url) {
        Log.e("requestBody", requestBody);

        JsonObjectRequest JOPR = new JsonObjectRequest(Request.Method.POST,
                url, null, response -> {
            try {
                progress.cancel();
            } catch (Exception e) {
            }
            Log.e("resposeCodea", "" + response.toString());
            if (listener != null) {
                listener.onResponse(response);
            }

        }, volleyError -> {

            try {
                listener.responseCode(volleyError.networkResponse.statusCode);
            } catch (Exception e) {

            }

            try {
                progress.cancel();
            } catch (Exception e) {
            }
            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                volleyError = error;
                try {
                    String[] animals = volleyError.toString().split("\"");

                    listener.onError(animals[3]);
                } catch (Exception e) {
                    Log.e("eeeeee", e.toString());
                    listener.onError(volleyError.toString());
                }


            } else {
                listener.onError(volleyError.toString());


            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "jwt " + ps.getToken());
                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.e("mStatusCode", "" + mStatusCode);
                if (listener != null) {
                    listener.responseCode(mStatusCode);
                }
                return super.parseNetworkResponse(response);
            }

            @Override
            public byte[] getBody() {
                return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
            }


        };
        queue.add(JOPR);

    }

    public void VolleyPostWithToken(String requestBody, String url) {
        Log.e("requestBody", requestBody);

        JsonObjectRequest JOPR = new JsonObjectRequest(Request.Method.POST,
                url, null, response -> {
            try {
                progress.cancel();
            } catch (Exception e) {
            }
            Log.e("resposeCodea", "" + response.toString());
            if (listener != null) {
                listener.onResponse(response);
            }

        }, volleyError -> {

            try {
                listener.responseCode(volleyError.networkResponse.statusCode);
            } catch (Exception e) {

            }

            try {
                progress.cancel();
            } catch (Exception e) {
            }
            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                volleyError = error;
                try {
                    String[] animals = volleyError.toString().split("\"");

                    listener.onError(animals[3]);
                } catch (Exception e) {
                    Log.e("eeeeee", e.toString());
                    listener.onError(volleyError.toString());
                }


            } else {
                listener.onError(volleyError.toString());


            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                 return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.e("mStatusCode", "" + mStatusCode);
                if (listener != null) {
                    listener.responseCode(mStatusCode);
                }
                return super.parseNetworkResponse(response);
            }

            @Override
            public byte[] getBody() {
                return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
            }


        };
        JOPR.setRetryPolicy(new DefaultRetryPolicy(
                2000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(JOPR);

    }




     public void VolleyGet(String url) {
        Log.e("asd",url);
        try {
            progress.show();
        } catch (Exception e) {
        }

        JsonObjectRequest JOPR = new JsonObjectRequest(Request.Method.GET,
                url, null, response -> {
            try {
                progress.cancel();
            } catch (Exception e) {
            }
            Log.e("OO9", "" + response.toString());
            if (listener != null) {
                listener.onResponse(response);
            }

        }, volleyError -> {

            try {
                progress.cancel();
            } catch (Exception e) {
            }
            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                volleyError = error;


                try {


                    String[] animals = volleyError.toString().split("\"");


                    listener.onError(animals[3]);
                } catch (Exception e) {
                    Log.e("eeeeee", e.toString());
                    listener.onError(volleyError.toString());
                }


            } else {
                listener.onError(volleyError.toString());


            }


        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "jwt " + ps.getToken());

                return headers;
            }


            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.e("mStatusCode", "" + mStatusCode);
                if (listener != null) {
                    listener.responseCode(mStatusCode);
                }
                return super.parseNetworkResponse(response);
            }


        };
        JOPR.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(JOPR);

    }

    public void VolleyGetWithOutToken(String url) {
        try {
            progress.show();
        } catch (Exception e) {
        }

        JsonObjectRequest JOPR = new JsonObjectRequest(Request.Method.GET,
                url, null, response -> {
            try {
                progress.cancel();
            } catch (Exception e) {
            }
            Log.e("OO9", "" + response.toString());
            if (listener != null) {
                listener.onResponse(response);
            }

        }, volleyError -> {

            try {
                progress.cancel();
            } catch (Exception e) {
            }
            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                volleyError = error;


                try {


                    String[] animals = volleyError.toString().split("\"");


                    listener.onError(animals[3]);
                } catch (Exception e) {
                    Log.e("eeeeee", e.toString());
                    listener.onError(volleyError.toString());
                }


            } else {
                listener.onError(volleyError.toString());


            }


        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }


            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.e("mStatusCode", "" + mStatusCode);
                if (listener != null) {
                    listener.responseCode(mStatusCode);
                }
                return super.parseNetworkResponse(response);
            }


        };
        JOPR.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(JOPR);

    }

    public void VolleyDelete(String url) {
        try {
            progress.show();
        } catch (Exception e) {
        }

        JsonObjectRequest JOPR = new JsonObjectRequest(Request.Method.DELETE,
                url, null, response -> {
            try {
                progress.cancel();
            } catch (Exception e) {
            }

            if (listener != null) {
                listener.onResponse(response);
            }

        }, volleyError -> {

            try {
                progress.cancel();
            } catch (Exception e) {
            }
            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                volleyError = error;


                try {


                    String[] animals = volleyError.toString().split("\"");


                    listener.onError(animals[3]);
                } catch (Exception e) {
                    Log.e("eeeeee", e.toString());
                    listener.onError(volleyError.toString());
                }


            } else {
                listener.onError(volleyError.toString());


            }


        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "jwt " + ps.getToken());

                return headers;
            }


            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.e("mStatusCode", "" + mStatusCode);
                if (listener != null) {
                    listener.responseCode(mStatusCode);
                }
                return super.parseNetworkResponse(response);
            }


        };

        queue.add(JOPR);

    }

    public void VolleyArraryGet(String url) {

        try {
            progress.show();
        } catch (Exception e) {
        }
        JsonArrayRequest JOPR = new JsonArrayRequest(Request.Method.GET,
                url, null, response -> {
            try {
                progress.cancel();
            } catch (Exception e) {
            }
            Log.e("OO9", "" + response.toString());
            if (listenerarray != null) {
                listenerarray.onResponse(response);
            }

        }, volleyError -> {
            try {
                progress.cancel();
            } catch (Exception e) {
            }
            Log.e("VolleyError", volleyError.toString());
            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                volleyError = error;
                try {
                    String[] animals = volleyError.toString().split("\"");

                    listener.onError(animals[3]);
                } catch (Exception e) {
                    //  Log.e("eeeeee",e.toString());
                    //                        listener.onError(volleyError.toString());
                }


            } else {
                //d listener.onError(volleyError.toString());


            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "jwt " + ps.getToken());

                return headers;
            }


            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.e("mStatusCode", "" + mStatusCode);
                if (listenerarray != null) {
                    listenerarray.responseCode(mStatusCode);
                }
                return super.parseNetworkResponse(response);
            }


        };
        JOPR.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(JOPR);

    }


    public void VolleyPatch(String requestBody, String url) {

        try {
            progress.show();
        } catch (Exception e) {
        }
        JsonObjectRequest JOPR = new JsonObjectRequest(Request.Method.PATCH,
                url, null, response -> {
            try {
                progress.cancel();
            } catch (Exception e) {
            }
            Log.e("OO9", "" + response.toString());
            if (listener != null) {
                listener.onResponse(response);
            }

        }, volleyError -> {
            try {
                progress.cancel();
            } catch (Exception e) {
            }
            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                volleyError = error;
                try {
                    String[] animals = volleyError.toString().split("\"");

                    listener.onError(animals[3]);
                } catch (Exception e) {
                    Log.e("eeeeee", e.toString());
                    listener.onError(volleyError.toString());
                }


            } else {
                listener.onError(volleyError.toString());


            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "jwt " + ps.getToken());

                return headers;
            }


            @Override
            public byte[] getBody() {
                return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.e("mStatusCode", "" + mStatusCode);
                if (listener != null) {
                    listener.responseCode(mStatusCode);
                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(JOPR);

    }

    public void VolleyPut(String requestBody, String url) {

        try {
            progress.show();
        } catch (Exception e) {
        }
        JsonObjectRequest JOPR = new JsonObjectRequest(Request.Method.PUT,
                url, null, response -> {
            try {
                progress.cancel();
            } catch (Exception e) {
            }
            Log.e("OO9", "" + response.toString());
            if (listener != null) {
                listener.onResponse(response);
            }

        }, volleyError -> {
            try {
                progress.cancel();
            } catch (Exception e) {
            }
            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                volleyError = error;
                try {
                    String[] animals = volleyError.toString().split("\"");

                    listener.onError(animals[3]);
                } catch (Exception e) {
                    Log.e("eeeeee", e.toString());
                    listener.onError(volleyError.toString());
                }


            } else {
                listener.onError(volleyError.toString());


            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "jwt " + ps.getToken());

                return headers;
            }


            @Override
            public byte[] getBody() {
                return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.e("mStatusCode", "" + mStatusCode);
                if (listener != null) {
                    listener.responseCode(mStatusCode);
                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(JOPR);

    }

    public void VolleySignIn(String requestBody, String url) {

        try {
            progress.show();
        } catch (Exception e) {
        }
        JsonObjectRequest JOPR = new JsonObjectRequest(Request.Method.POST,
                url, null, response -> {
            try {
                progress.cancel();
            } catch (Exception e) {
            }
            Log.e("OO9", "" + response.toString());
            if (listener != null) {
                listener.onResponse(response);
            }

        }, volleyError -> {
            try {
                progress.cancel();
            } catch (Exception e) {
            }
            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                volleyError = error;
                try {
                    String[] animals = volleyError.toString().split("\"");

                    listener.onError(animals[3]);
                } catch (Exception e) {
                    Log.e("eeeeee", e.toString());
                    listener.onError(volleyError.toString());
                }


            } else {
                listener.onError(volleyError.toString());


            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");

                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.e("mStatusCode", "" + mStatusCode);
                if (listener != null) {
                    listener.responseCode(mStatusCode);
                }
                return super.parseNetworkResponse(response);
            }

            @Override
            public byte[] getBody() {
                return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
            }


        };

        queue.add(JOPR);

    }


    public interface MyServerListener {
        void onResponse(JSONObject response);

        void onError(String error);

        void responseCode(int resposeCode);
    }

    public interface MyServerListenerArray {
        void onResponse(JSONArray response);

        void onError(String error);

        void responseCode(int resposeCode);
    }

}
