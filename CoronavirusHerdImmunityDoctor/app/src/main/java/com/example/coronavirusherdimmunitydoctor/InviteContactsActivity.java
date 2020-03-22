package com.example.coronavirusherdimmunitydoctor;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.coronavirusherdimmunitydoctor.utils.ApiManager;
import com.example.coronavirusherdimmunitydoctor.utils.PreferenceManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

public class InviteContactsActivity extends Activity {

    private ArrayList<String> contacts_list;  //the list of all contacts

    private ImageButton bt_back;
    private ListView lv_contacts;
    private ArrayAdapter<String> list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_contacts);
        lv_contacts = (ListView) findViewById(R.id.list_contacts);
        bt_back = (ImageButton) findViewById(R.id.bt_back);

        contacts_list = new ArrayList<String>();
        //invited_contacts_list = new ArrayList<String>();

        loadingContacts();

        //click on contacts of ListView so as to select and invite them
        lv_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                final String contact =  lv_contacts.getItemAtPosition(position).toString();  //phone number

                /**
                 *  open an Alert dialog which asks if user has selected  right number
                 *  if click on "yes" then send selected contacts to Server
                 *  else if click on "no" then the user can change contacts
                 */
                new AlertDialog.Builder(InviteContactsActivity.this)
                        .setTitle(R.string.alertdial_invite_title)
                        .setMessage(R.string.alertdial_invite_msg)
                        .setPositiveButton(R.string.alertdial_yes, new DialogInterface.OnClickListener() { //invite new doctor
                            public void onClick(DialogInterface dialog, int which) {

                                //send contact to Server
                                PreferenceManager pm = new PreferenceManager(InviteContactsActivity.this);
                                task_inviteDoctor(contact.split(":")[1], pm.getAuthorizationToken());                     //call inviteDoctor Api

                            }
                        })
                        .setNegativeButton(R.string.alertdial_no, new DialogInterface.OnClickListener() { //change number selected list
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });

        bt_back = findViewById(R.id.bt_back);
        bt_back.setImageResource(R.drawable.left);
        bt_back.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Other" button
             */
            public void onClick(View view)
            {
                Intent intent=new Intent(InviteContactsActivity.this, DoctorViewActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    /**
     * Create a contacts list (ListView) in order to be selected
     */
    @SuppressLint("StaticFieldLeak")
    private void loadingContacts(){
        new AsyncTask<Void, Void, Void>() {
            private ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(InviteContactsActivity.this,
                        getString(R.string.progrdial_loading_con), getString(R.string.progrdial_waiting), true, false);
            }

            @Override
            protected void onPostExecute(Void result) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    list_adapter = new ArrayAdapter<String>(InviteContactsActivity.this,R.layout.list_item, R.id.tvContact, contacts_list);
                    lv_contacts.setAdapter(list_adapter);
                }
            }

            @Override
            protected Void doInBackground(Void... params) {
                contacts_list = readContacts();
                return null;
            }

        }.execute((Void[]) null);
    }

    /**
     * Read list of Contacts on  smartphone
     * @return the array list of contact
     */
    private ArrayList<String> readContacts() {

        ArrayList<String> arraylist = new ArrayList<String>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    if (pCur.moveToFirst()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String contact = name + ":" + phone.replaceAll("\\s+","").replaceAll("-","");   //replaced white spaces and "-" from number
                        arraylist.add(contact);
                    }
                    pCur.close();
                }
            }
        }
        return arraylist;
    }




    /******************* Task Function **************************/

    /**
     * Run task in order to call inviteDoctor API and manage the response
     *
     * @param phone_number: phone number of Doctor to invite
     * @param token: authorization token
     */
    private void task_inviteDoctor(final String phone_number, final String token){

        Task.callInBackground(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                return ApiManager.inviteDoctor(phone_number, token);  //call inviteDoctor
            }
        }).onSuccess(new Continuation<JSONObject, Object>() {
            @Override
            public String then(Task<JSONObject> task) throws Exception {

                JSONObject object = task.getResult();;             //get response of inviteDoctor
                if (object != null) {
                    if (object.getInt("code") == 202){      // if response is 'ok'
                        Toast.makeText(getApplicationContext(), R.string.toast_num_doc_invited, Toast.LENGTH_SHORT).show();
                    }
                } else{
                    //PER DEBUG
                    Toast.makeText(getApplicationContext(), "DEBUG CALL INVITE DOCTOR", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        },  Task.UI_THREAD_EXECUTOR);
    }

}
