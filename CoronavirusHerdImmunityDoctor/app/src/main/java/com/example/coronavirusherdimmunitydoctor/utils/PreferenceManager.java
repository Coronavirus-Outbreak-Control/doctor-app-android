package com.example.coronavirusherdimmunitydoctor.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;


public class PreferenceManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file names
    private static final String PREF_NAME = "SharedData";


    /***** Key shared preferences "SharedData" *******/
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String AUTH_TOKEN = "authorization_token"; // authorization token used to get jwt token
    private static final String JWT_TOKEN = "jwt_token";            // jwt token used to send 'authorized' rest API
    private static final String PHONE_NUMBER = "phone_number";      // phone number of Doctor
    private static final String DOCTOR_ID = "doctor_id";            // doctor id received after activation


    private String TAG = "PreferenceManager";

    public PreferenceManager(Context context) {
        this._context = context;

        if(check_versions_update()) {  // if version is updated and is greater than 23

            backupFile(); //copy file from SharedPreferences to EncryptedSharedPreferences

        }else if (android.os.Build.VERSION.SDK_INT >= 23) { //if android api versions >= 23 and old version is not <23

            try {

                //create an encryption master key and store it in the Android KeyStore
                String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

                // Opens an instance of encrypted SharedPreferences
                pref = EncryptedSharedPreferences.create(
                        PREF_NAME,
                        masterKeyAlias,
                        _context,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );

                // use the shared preferences and editor as you normally would
                editor = pref.edit();

            } catch (Exception e){

                Log.d(TAG, "Error to open Encrypted Shared Preferences");
            }

        }else{ //if android api versions: 20-21-22

            pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
            editor = pref.edit();
        }
        setSdkVers(Build.VERSION.SDK_INT);   //save new sdk version
    }


    /**
     * check if version sdk (saved on file) is updated, less than 23 and current version is greater than 23
     * @return "true" if version sdk (saved on file) is updated, less than 23 and current version is greater or equal than 23,
     *          "false" otherwise
     */
    private Boolean check_versions_update (){
        pref_sdkvers = _context.getSharedPreferences(PREF_NAME_SDK_VERS,PRIVATE_MODE);
        editor_sdkvers = pref_sdkvers.edit();
        int old_sdkvers = getSdkVers();
        return (old_sdkvers >= 10 && old_sdkvers < 23 && Build.VERSION.SDK_INT >= 23);
    }

    /**
     *  copy Shared Preferences into Encrypted Shared Preferences in order to not loose shared data
     *  when sdk versions, less than 23 (<23), become greater or equal than 23 (>=23)
     */
    private void backupFile(){

        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();

        /* get shared data saved into shared preferences */
        boolean is_first_time_launch   = isFirstTimeLaunch();
        String auth_token              = getAuthorizationToken();
        String jwt_token               = getJwtToken();
        String phone_numb              = getPhoneNumber();
        Long doctor_id                 = getDoctorId();

        try {

            //create an encryption master key and store it in the Android KeyStore
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // Opens an instance of encrypted SharedPreferences
            pref = EncryptedSharedPreferences.create(
                    PREF_NAME,
                    masterKeyAlias,
                    _context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            // use the shared preferences and editor as you normally would
            editor = pref.edit();

          /* copy saved data into encrypted shared preferences */
          setFirstTimeLaunch(is_first_time_launch);
          setAuthorizationToken(auth_token);
          setJwtToken(jwt_token);
          setPhoneNumber(phone_numb);
          setDoctorId(doctor_id);

      } catch (Exception e){

          Log.d(TAG, "Error to open Encrypted Shared Preferences during backup");
      }
    }



    /******** functions on Shared Preference "SharedData" *************/

    /************ SET Functions *****************/

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setAuthorizationToken(String auth_token){
        editor.putString(AUTH_TOKEN, auth_token);
        editor.commit();
    }

    public void setJwtToken(String jwt_token){
        editor.putString(JWT_TOKEN, jwt_token);
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

    public String getJwtToken(){
        return pref.getString(JWT_TOKEN, null);
    }

    public String getPhoneNumber(){
        return pref.getString(PHONE_NUMBER, null);
    }

    public Long getDoctorId() {
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
    /******************************************************************/




    /******** functions on Shared Preference "SdkVersion" *************/


    /************ SET Functions *****************/

    /**
     * Set current SDK android version of mobile device
     * @param sdkVers
     */
     private void setSdkVers(int sdkVers){
         editor_sdkvers.putInt(SDK_VERS, sdkVers);
         editor_sdkvers.commit();
     }


    /************ GET Functions *****************/
    /**
     * Get current SDK android version of mobile device
     * @return current SDK android version of mobile device
     */
     private int getSdkVers(){
         return pref_sdkvers.getInt(SDK_VERS, -1);
     }


    /******************************************************************/

}