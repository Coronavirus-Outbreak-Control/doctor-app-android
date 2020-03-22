package com.example.coronavirusherdimmunitydoctor.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "SharedData";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String AUTH_TOKEN = "authorization_token"; // authorization code used to send 'authorized' rest API
    private static final String PHONE_NUMBER = "phone_number";      // phone number of Doctor
    private static final String DOCTOR_ID = "doctor_id";            // doctor id received after activation


    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /************ SET Functions *****************/

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setAuthorizationToken(String auth_token){
        editor.putString(AUTH_TOKEN, auth_token);
        editor.commit();
    }

    public void setPhoneNumber(String phoneNumber){
        editor.putString(PHONE_NUMBER, phoneNumber);
        editor.commit();
    }

    public void setDoctorId(Long doctorId){
        editor.putLong(DOCTOR_ID, doctorId);
        editor.commit();
    }
    /*********************************************/


    /************ GET Functions *****************/

    public String getAuthorizationToken(){
        return pref.getString(AUTH_TOKEN, null);
    }

    public String getPhoneNumber(){
        return pref.getString(PHONE_NUMBER, null);
    }

    public Long getDoctorId(){
        return pref.getLong(DOCTOR_ID, -1);
    }
    /********************************************/


    /************ CHECK Functions ***************/

    /**
     * Check if it is the first time launch of App
     * @return "true" if it the first time launch, else "false"
     */
    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    /**
     * When Doctor Id and Authorization Token are not set then the Doctor is logged for the first time
     * @return "true" if Doctor is logged for the first time, else "false"
     */
    public boolean isFirstLogin(){
        return (getDoctorId() == -1) && (getAuthorizationToken() == null);
    }
    /********************************************/

}