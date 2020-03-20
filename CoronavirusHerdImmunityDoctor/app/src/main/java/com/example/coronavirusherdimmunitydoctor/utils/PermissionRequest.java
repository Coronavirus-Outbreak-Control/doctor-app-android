package com.example.coronavirusherdimmunitydoctor.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.coronavirusherdimmunitydoctor.R;
import com.example.coronavirusherdimmunitydoctor.introduction.CameraPermissionActivity;
import com.example.coronavirusherdimmunitydoctor.introduction.ContactsPermissionActivity;


public class PermissionRequest {

    private Context context;

    public PermissionRequest(Context packageContext){
        this.context = packageContext;
    }

    /**
     * Check permissions if they are granted else go to introduction activities in order to enable them (Camera, Read_Contacts)
     * @return: 'true' if all permissions are granted, 'false' if at least one permission is not granted
     */
    public boolean checkPermissions(){

        boolean ret_check_perm = true;

        //if camera permission is not granted then go to CameraPermissionActivity in order to enable it
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            final Intent intent_cam = new Intent(context, CameraPermissionActivity.class);
            intent_cam.putExtra("permission_request", true); // notify next activity that permission is required

            // show alert dialog "Please, enable camera"
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.cam_disabled);
            builder.setMessage(R.string.cam_please_en);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    //when you click "ok" then go to next activity in order to enable camera
                    context.startActivity(intent_cam);
                }
            });
            builder.show();

            ret_check_perm = false;

        }//if read_contact is not granted then go to ContactsPermissionActivity in order to enable it
        else if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            final Intent intent_cont = new Intent(context, ContactsPermissionActivity.class);
            intent_cont.putExtra("permission_request", true); // notify next activity that permission is required

            // show alert dialog "Please, enable read_contacts"
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.cont_disabled);
            builder.setMessage(R.string.cont_please_en);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    //when you click "ok" then go to ContactPermissionActivity in order to enable read_contact permission
                    context.startActivity(intent_cont);
                }
            });
            builder.show();

            ret_check_perm = false;

        }else{

        }

        return ret_check_perm;
    }


}
