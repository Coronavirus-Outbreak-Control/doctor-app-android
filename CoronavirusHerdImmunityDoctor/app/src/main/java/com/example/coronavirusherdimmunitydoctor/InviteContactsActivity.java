package com.example.coronavirusherdimmunitydoctor;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
                String[] array = contact.split(":");
                String name = array[0];
                String number = array[1];

                invited_contacts_list.add(contact);
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
                    Toast.makeText(getApplicationContext(), "Select at least a contact", Toast.LENGTH_SHORT).show();
                }
                else {
                    //send invited_contacts_list to Server

                    Toast.makeText(getApplicationContext(), "New contacts are invited", Toast.LENGTH_SHORT).show();

                    //go back to DoctorViewActivity
                    Intent intent = new Intent(InviteContactsActivity.this, DoctorViewActivity.class);
                    startActivity(intent);
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
                        "Loading Contacts...", "Waiting, please", true, false);
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
                    //System.out.println("name : " + name + ", ID : " + id);

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
