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
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class InviteContactsActivity extends Activity {

    private ArrayList<String> contacts_list;  //the list of all contacts
    private ArrayList<String> invited_contacts_list;  //the list of invited/selected contacts

    private Button bt_invite;
    private ListView lv_contacts;
    private ArrayAdapter<String> list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_contacts);
        lv_contacts = (ListView) findViewById(R.id.list_contacts);
        bt_invite = (Button) findViewById(R.id.bt_invite);

        contacts_list = new ArrayList<String>();
        invited_contacts_list = new ArrayList<String>();

        inviteContacts();

        //click on contacts of ListView so as to select and invite them
        lv_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String contact =  lv_contacts.getItemAtPosition(position).toString();

                //lv_contacts.setBackgroundColor(Color.GREEN);
                if (invited_contacts_list.contains(contact)){ // if contact is contained in invited contact list then it is removed by list

                    invited_contacts_list.remove(contact);

                } else{  // if contact has not been already inserted in invited contact list then it is added to list
                    String[] array = contact.split(":");
                    String name = array[0];
                    String number = array[1];

                    invited_contacts_list.add(contact);
                }

            }
        });

        bt_invite.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Invite" button, send the list of selected contacts to Server, then go back to main activity
             **/
            public void onClick(View view)
            {
                if (invited_contacts_list.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), R.string.toast_select_doc, Toast.LENGTH_SHORT).show();
                }
                else {

                    /**
                     *  open an Alert dialog which asks if user has selected  right numbers
                     *  if click on "yes" then send selected contacts to Server and change activity
                     *  else if click on "no" then the user can change contacts
                     */
                    new AlertDialog.Builder(InviteContactsActivity.this)
                            .setTitle(R.string.alertdial_invite_title)
                            .setMessage(R.string.alertdial_invite_msg)
                            .setPositiveButton(R.string.alertdial_yes, new DialogInterface.OnClickListener() { //invite new doctors
                                public void onClick(DialogInterface dialog, int which) {
                                    //send invited_contacts_list to Server

                                    Toast.makeText(getApplicationContext(), R.string.toast_new_doc_inv, Toast.LENGTH_SHORT).show();

                                    //go back to DoctorViewActivity
                                    Intent intent = new Intent(InviteContactsActivity.this, DoctorViewActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(R.string.alertdial_no, new DialogInterface.OnClickListener() { //change number selected list
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();


                }
            }
        });


    }


    /**
     * Create a contacts list (ListView) in order to be selected
     */
    @SuppressLint("StaticFieldLeak")
    private void inviteContacts(){
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
                        String contact = name + ":" + phone;
                        arraylist.add(contact);
                    }
                    pCur.close();
                }
            }
        }
        return arraylist;
    }
}
