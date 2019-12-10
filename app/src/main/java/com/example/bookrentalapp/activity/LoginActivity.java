package com.example.bookrentalapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.bookrentalapp.utils.VolleyRequest;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.example.bookrentalapp.utils.PreferenceSaver;
import com.example.bookrentalapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.view.View.GONE;

public class LoginActivity extends AppCompatActivity {

    private Button btn;
    public static int APP_REQUEST_CODE = 99;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(LoginActivity.this);
        btn = findViewById(R.id.fbbtn);
        PreferenceSaver ps = new PreferenceSaver(LoginActivity.this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ps.getPhone().equalsIgnoreCase("-1")) {
                    //  startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    phoneLogin();
                } else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }


            }
        });

        if (ps.getPhone().equalsIgnoreCase("-1")) {

        } else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

    }


    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);


            if (loginResult.getError() != null) {

            } else if (loginResult.wasCancelled()) {

            } else {
                if (loginResult.getAccessToken() != null) {
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(Account account) {
                            PhoneNumber phoneNumber = account.getPhoneNumber();
                            createPostRequest(phoneNumber.toString().replace("+88", ""), loginResult.getAccessToken().toString());
                            Log.e("myNumber", phoneNumber.toString());

                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {
                            Log.e("123123", accountKitError.getErrorType().toString());
                        }
                    });

                } else {

                }

            }

        }
    }

    private void createPostRequest(String token, String fbAcc) {
        findViewById(R.id.fbbtn).setVisibility(GONE);
        try {
            PreferenceSaver ps = new PreferenceSaver(LoginActivity.this);


            VolleyRequest vr = new VolleyRequest(LoginActivity.this);

            JSONObject js = new JSONObject();
            js.accumulate("phone", token);
            js.accumulate("fbAccess", fbAcc);
            js.accumulate("fireAccess", FirebaseInstanceId.getInstance().getToken());
            Log.e("TTT", FirebaseInstanceId.getInstance().getToken());
            vr.VolleyPostWithToken(js.toString(), "http://monisho.com/deiliw80hp/client/");

            vr.setListener(new VolleyRequest.MyServerListener() {

                @Override
                public void responseCode(int resposeCode) {
                    if (resposeCode == 201) {
                        ps.setPhone(token);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                wishListPostReq(token);
                            }
                        });
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();

                        //  Toast.makeText(LoginActivity.this, "Successful !", Toast.LENGTH_SHORT).show();
                    } else if (resposeCode == 400) {
                        ps.setPhone(token);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        findViewById(R.id.fbbtn).setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Refresh 123", response.toString());
                }

                @Override
                public void onError(String error) {

                    Log.e("Unsuccesful", error);
                    //  Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                    //   Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                }

            });
        } catch (Exception e) {
            Log.e("Refresh 123222", e.toString());
        }
    }

    private void wishListPostReq(String token) {
        try {
            VolleyRequest vr = new VolleyRequest(LoginActivity.this);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", token);
            JSONArray jsonArray = new JSONArray();
            jsonObject.put("catalogList", jsonArray);
            vr.VolleyPost(jsonObject.toString(), "http://monisho.com/deiliw80hp/addwish/");
            Log.e("errrr", jsonObject.toString());
            vr.setListener(new VolleyRequest.MyServerListener() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("adding new", response.toString());
                }

                @Override
                public void onError(String error) {
                    Log.e("adding new", error);
                }

                @Override
                public void responseCode(int resposeCode) {

                }
            });
        } catch (Exception e) {
            Log.e("eeeex", e.toString());
        }
    }


    public void phoneLogin() {
        intent = new Intent(LoginActivity.this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }


}
