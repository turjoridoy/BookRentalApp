package com.example.bookrentalapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.facebook.accountkit.AccountKit;
import com.monisho.supply.modal.Catalog;
import com.monisho.supply.modal.SharedCat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Tasfiqul Ghani on 6/5/2017.
 */

public class PreferenceSaver {
    private static final String PREF_NAME = "welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    public PreferenceSaver(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getDomainId() {
        return pref.getString("setDomainId", "");
    }

    public void setDomainId(String category) {
        editor.putString("setDomainId", category);
        editor.commit();
    }


    public boolean isLoggedIn() {
        return pref.getBoolean("isLoggedIn", false);
    }

    public void setLoggedIn(Boolean login) {
        editor.putBoolean("isLoggedIn", login);
        editor.commit();
    }

    public void setToPref(String s) {

        if (getFilters().equalsIgnoreCase("-1")) {
            setFilters("?" + s);
        } else {
            setFilters(getFilters() + "&" + s);
        }
    }

    public void removePref(String s) {

        String pref = getFilters();
        pref = pref.replace("?" + s, "");
        pref = pref.replace("&" + s, "");
        setFilters(pref);

    }

    public String getUid() {
        return pref.getString("getUid", "");
    }

    public void setUid(String name) {
        editor.putString("getUid", name);
        editor.commit();
    }

    public String getName() {
        return pref.getString("setName", "");
    }

    public void setName(String fullName) {
        editor.putString("setName", fullName);
        editor.commit();
    }


    public String getDemandCollection() {
        return pref.getString("setDemandCollection", "-1");
    }

    public void setDemandCollection(String token) {
        editor.putString("setDemandCollection", token);
        editor.commit();
    }

    public String getTeamId() {
        return pref.getString("team", "");
    }

    public String getTemplate() {
        return pref.getString("temp", "");
    }

    public void setTemplate(String temp) {
        editor.putString("temp", temp);
        editor.commit();
    }

    public String getOtherTemplate() {
        return pref.getString("otemp", "");
    }

    public void setOtherTemplate(String temp) {
        editor.putString("otemp", temp);
        editor.commit();
    }


    public void setCompany(String company) {
        editor.putString("setCompany", company);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString("setEmail", "");
    }

    public void setEmail(String email) {
        editor.putString("setEmail", email);
        editor.commit();
    }


    public String getPhone() {
        return pref.getString("setPhone", "-1");
    }

    public void setPhone(String phone) {
        editor.putString("setPhone", phone);
        editor.commit();
    }

    public String getMaritalStatus() {
        return pref.getString("setMS", "");
    }

    public void setMaritalStatus(String ms) {
        editor.putString("setMS", ms);
        editor.commit();
    }


    public String getDomainName() {
        return pref.getString("setdCompany", "");
    }

    public void setDomainName(String company) {
        editor.putString("setdCompany", company);
        editor.commit();
    }


    public String getLanguage() {
        return pref.getString("language", "ENG");
    }

    public void setLanguage(String language) {
        editor.putString("language", language);
        editor.commit();
    }

    public String getToken() {
        return pref.getString("token", "-1");
    }

    public void setToken(String token) {
        editor.putString("token", token);
        editor.commit();
    }

    public int getId() {
        return pref.getInt("id", -1);
    }

    public void setId(int id) {
        editor.putInt("id", id);
        editor.commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    public String getImage() {
        return pref.getString("pimg", "");
    }

    public void setImage(String token) {
        editor.putString("pimg", token);
        editor.commit();
    }


    public String getFrequent() {
        return pref.getString("", "-1");
    }

    public void setFrequent(String getFrequent) {
        editor.putString("getFrequent", getFrequent);
        editor.commit();
    }

    public String getORGId() {
        return pref.getString("getORGId", "-1");
    }

    public void setORGID(String getORG) {
        editor.putString("getORGId", getORG);
        editor.commit();
    }

    public String getORG() {
        return pref.getString("getORG", "-1");
    }

    public void setORG(String getORG) {
        editor.putString("getORG", getORG);
        editor.commit();
    }

    public String getORGAddress() {
        return pref.getString("getORGAddress", "-1");
    }

    public void setORGAddress(String getORGAddress) {
        editor.putString("getORGAddress", getORGAddress);
        editor.commit();
    }

    public void LogOut() {
        try {
            VolleyRequest vr = new VolleyRequest(_context);

            JSONObject js = new JSONObject();
            js.accumulate("fb_token", "empty_token");


            vr.setListener(new VolleyRequest.MyServerListener() {

                @Override
                public void responseCode(int resposeCode) {

                }

                @Override
                public void onResponse(JSONObject response) {

                    PreferenceSaver ps = new PreferenceSaver(_context);
                    setLoggedIn(false);
                    setCompany("");
                    setPhone("");
                    setToken("-1");
                    setCompany("");
                    setName("");
                    AccountKit.logOut();

                }

                @Override
                public void onError(String error) {


                }

            });
        } catch (Exception e) {


        }
    }


    public String getPackages() {
        return pref.getString("setPackages", "-1");
    }

    public void setPackages(String language) {
        editor.putString("setPackages", language);
        editor.commit();
    }


    public boolean hasPackage() {
        return pref.getBoolean("hasPackage", false);
    }

    public void hasPackage(boolean language) {
        editor.putBoolean("hasPackage", language);
        editor.commit();
    }


    public boolean isPackTask() {
        return pref.getBoolean("isPackTask", false);
    }

    public void setPackTask(Boolean login) {
        editor.putBoolean("isPackTask", login);
        editor.commit();
    }


    public boolean isPackMsg() {
        return pref.getBoolean("isPackMsg", false);
    }

    public void setPackMsg(Boolean login) {
        editor.putBoolean("isPackMsg", login);
        editor.commit();
    }

    public boolean isPackTracking() {
        return pref.getBoolean("isPackTracking", false);
    }

    public void setPackTracking(Boolean login) {
        editor.putBoolean("isPackTracking", login);
        editor.commit();
    }

    public boolean isPackAttendance() {
        return pref.getBoolean("isPackAttendance", false);
    }

    public void setPackAttendance(Boolean login) {
        editor.putBoolean("isPackAttendance", login);
        editor.commit();
    }


    public int getRole() {
        return pref.getInt("getRole", -1);
    }

    public void setRole(int login) {
        editor.putInt("getRole", login);
        editor.commit();
    }

    public boolean hasPassword() {
        return pref.getBoolean("hasPassword", false);
    }

    public void hasPassword(Boolean login) {
        editor.putBoolean("hasPassword", login);
        editor.commit();
    }

    public void saveUserInfo(JSONObject response) throws JSONException {
        try {
            setPackTracking(response.getJSONObject("user").getJSONObject("package_info").getBoolean("has_agent_tracking"));
            setPackMsg(response.getJSONObject("user").getJSONObject("package_info").getBoolean("has_messaging"));
            setPackAttendance(response.getJSONObject("user").getJSONObject("package_info").getBoolean("has_attendance"));
            setPackTask(response.getJSONObject("user").getJSONObject("package_info").getBoolean("has_task_management"));
        } catch (Exception e) {
        }
        setRole(response.getJSONObject("user").getInt("role"));
        setPackages(response.getJSONObject("user").getString("packages"));
        Log.e("setPackage", response.getJSONObject("user").getString("packages"));
        setToken(response.getString("jwt_token"));
        setPhone(response.getJSONObject("user").getString("phone"));
        setId(Integer.parseInt(response.getJSONObject("user").getString("id")));
        setName(response.getJSONObject("user").getString("full_name"));
        setORGID(response.getJSONObject("user").getString("org_id"));
        setDomainId(response.getJSONObject("user").getString("domain"));
        setImage(response.getJSONObject("user").getString("image"));
        setTemplate(response.getJSONObject("user").getString("task_templates"));
        setOtherTemplate(response.getJSONObject("user").getString("other_templates"));

    }

    public String getOfflineLocation() {
        return pref.getString("setOfflineLocation", "-1");
    }

    public int getInterval() {
        return pref.getInt("getInterval", 5);
    }

    public void setInterval(int token) {
        editor.putInt("getInterval", token);
        editor.commit();
    }

    public void setOfflineLocation(String token) {
        editor.putString("setOfflineLocation", token);
        editor.commit();
    }

    public void setOnTask(boolean b) {
        editor.putBoolean("ontask", b);
        editor.commit();
    }

    public Boolean isOnTask() {
        return pref.getBoolean("ontask", false);
    }

    public boolean isPresent() {
        return pref.getBoolean("present", false);
    }

    public void setPresent(boolean t) {
        editor.putBoolean("present", t);
        editor.commit();
    }


    public String getFilters() {
        return pref.getString("getFilters", "-1");
    }

    public void setFilters(String language) {
        editor.putString("getFilters", language);
        editor.commit();
    }


    public String getCarts() {
        return pref.getString("ca", "-1");
    }

    public void setCarts(String language) {
        editor.putString("ca", language);
        editor.commit();
    }

    public void addToCart(String id, String productName, String detail, String image, String price, String amount, String catid) {
        try {

            JSONArray jsonArray;
            if (getCarts().equalsIgnoreCase("-1")) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(getCarts());
            }


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("detail", detail);
            jsonObject.put("productname", productName);
            jsonObject.put("image", image);
            jsonObject.put("price", price);
            jsonObject.put("amount", amount);
            jsonObject.put("catid", catid);
            jsonArray.put(jsonObject);
            setCarts(jsonArray.toString());
            Toast.makeText(_context, "Added !", Toast.LENGTH_SHORT).show();
            Log.e("carts", jsonArray.toString());

        } catch (Exception e) {
            Log.e("Eee", e.toString());
        }


    }

    public void removeFromCart(String id) {
        try {

            JSONArray anotherArray = new JSONArray();
            JSONArray jsonArray;
            if (getCarts().equalsIgnoreCase("-1")) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(getCarts());
            }


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objs = jsonArray.getJSONObject(i);
                if (!objs.getString("id").equalsIgnoreCase(id)) {
                    anotherArray.put(objs);
                }
            }
            setCarts(anotherArray.toString());
            Log.e("carts", jsonArray.toString());

        } catch (Exception e) {
            Log.e("Eee", e.toString());
        }


    }

    public List<SharedCat> getSharedItems() {
        List<SharedCat> catList = new ArrayList<>();
        Log.e("asdasdget", getShared());
        try {
            if (!getShared().equalsIgnoreCase("-1")) {
                JSONArray jsonArray = new JSONArray(getShared());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    SharedCat catalog = new SharedCat();
                    Log.e("exxx", jsonObject.getString("getCatalogDescription"));
                    catalog.setId(jsonObject.getInt("getId"));
                    if (!jsonObject.isNull("getCatalogDescription")) {
                        catalog.setCatalogDescription(jsonObject.getString("getCatalogDescription"));
                    }

                    if (!jsonObject.isNull("getImages")) {
                        catalog.setImages(new JSONArray(jsonObject.getString("getImages")));
                    }
                    catalog.setDate(jsonObject.getString("date"));
                    catalog.setName(jsonObject.getString("getName"));
                    try{

                        catalog.setMin_price(jsonObject.getString("getMin_price"));
                    }catch (Exception e){

                    }
                    if (!jsonObject.isNull("getProductDiscountedPrice__min")) {
                        catalog.setProductDiscountedPrice__min((jsonObject.getString("getProductDiscountedPrice__min")));
                    }
                    if (!catList.contains(catalog)) {
                        catList.add(catalog);
                    }
                }
            }


        } catch (Exception e) {
            Log.e("exxx", e.toString());
        }

        return catList;
    }


    public void addToSharedList(Catalog catalog) {

        try {
            JSONArray jsonArray = new JSONArray();

            if (!getShared().equalsIgnoreCase("-1")) {
                jsonArray = new JSONArray(getShared());
            }

            JSONObject catalogObj = new JSONObject();
            catalogObj.accumulate("getId", catalog.getId());
            catalogObj.accumulate("getName", catalog.getName());
            catalogObj.accumulate("getCatalogDescription", catalog.getCatalogDescription());
            catalogObj.accumulate("getImages", catalog.getImages());
            catalogObj.accumulate("getMin_price", catalog.getMin_price());
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());
            catalogObj.accumulate("date", date);
            if (!catalogObj.isNull("getProductDiscountedPrice__min")) {

                catalogObj.accumulate("getProductDiscountedPrice__min", catalog.getProductDiscountedPrice__min());

            }
            Log.e("exxx", catalog.getName());
            jsonArray.put(catalogObj);
            setShared(jsonArray.toString());

        } catch (Exception e) {
            Log.e("exx", e.toString());
        }
    }

    public String getShared() {
        return pref.getString("getShared", "-1");
    }

    public void setShared(String language) {
        editor.putString("getShared", language);
        editor.commit();
    }


    public String gettotalPaid() {
        return pref.getString("totalPaid", "0");
    }

    public void settotalPaid(String language) {
        editor.putString("totalPaid", language);
        editor.commit();
    }

    public String getduePayment() {
        return pref.getString("duePayment", "0");
    }

    public void setduePayment(String language) {
        editor.putString("duePayment", language);
        editor.commit();
    }


    public String getlastMonthPaidPayment() {
        return pref.getString("setlastMonthPaidPayment", "0");
    }

    public void setlastMonthPaidPayment(String language) {
        editor.putString("setlastMonthPaidPayment", language);
        editor.commit();
    }


    public String gettotalBonusPaid() {
        return pref.getString("settotalBonusPaid", "0");
    }

    public void settotalBonusPaid(String language) {
        editor.putString("settotalBonusPaid", language);
        editor.commit();
    }


    public String getdueBonusPayment() {
        return pref.getString("dueBonusPayment", "0");
    }

    public void setdueBonusPaymentd(String language) {
        editor.putString("dueBonusPayment", language);
        editor.commit();
    }




}
