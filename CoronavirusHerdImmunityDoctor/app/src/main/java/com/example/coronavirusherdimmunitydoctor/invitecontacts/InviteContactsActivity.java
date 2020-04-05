package com.example.coronavirusherdimmunitydoctor.invitecontacts;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coronavirusherdimmunitydoctor.DoctorViewActivity;
import com.example.coronavirusherdimmunitydoctor.R;
import com.example.coronavirusherdimmunitydoctor.models.Contact;
import com.example.coronavirusherdimmunitydoctor.utils.ApiManager;
import com.example.coronavirusherdimmunitydoctor.utils.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;
import okhttp3.Response;

public class InviteContactsActivity extends Activity implements ContactsAdapter.OnContactClick, SelectedContactsAdapter.RemoveContactListener {

    private ArrayList<Contact> contactsList;  //the list of all contacts

    private View btBack;
    private FloatingActionButton btInvite;
    private RecyclerView contactsRecyclerView;
    private RecyclerView selectedContactsRecyclerView;
    private ContactsAdapter contactsAdapter;
    private SelectedContactsAdapter selectedContactsAdapter;
    private TextView selectedCount;

    private Context _context;

    /* create static prefix_list*/
    private static final HashMap<String, String> prefix_list = new HashMap<String, String>();

    static {
        prefix_list.put("AC", "+247");
        prefix_list.put("AD", "+376");
        prefix_list.put("AE", "+971");
        prefix_list.put("AF", "+93");
        prefix_list.put("AG", "+1-268");
        prefix_list.put("AI", "+1-264");
        prefix_list.put("AL", "+355");
        prefix_list.put("AM", "+374");
        prefix_list.put("AN", "+599");
        prefix_list.put("AO", "+244");
        prefix_list.put("AR", "+54");
        prefix_list.put("AS", "+1-684");
        prefix_list.put("AT", "+43");
        prefix_list.put("AU", "+61");
        prefix_list.put("AW", "+297");
        prefix_list.put("AX", "+358-18");
        prefix_list.put("AZ", "+374-97");
        prefix_list.put("AZ", "+994");
        prefix_list.put("BA", "+387");
        prefix_list.put("BB", "+1-246");
        prefix_list.put("BD", "+880");
        prefix_list.put("BE", "+32");
        prefix_list.put("BF", "+226");
        prefix_list.put("BG", "+359");
        prefix_list.put("BH", "+973");
        prefix_list.put("BI", "+257");
        prefix_list.put("BJ", "+229");
        prefix_list.put("BM", "+1-441");
        prefix_list.put("BN", "+673");
        prefix_list.put("BO", "+591");
        prefix_list.put("BR", "+55");
        prefix_list.put("BS", "+1-242");
        prefix_list.put("BT", "+975");
        prefix_list.put("BW", "+267");
        prefix_list.put("BY", "+375");
        prefix_list.put("BZ", "+501");
        prefix_list.put("CA", "+1");
        prefix_list.put("CC", "+61");
        prefix_list.put("CD", "+243");
        prefix_list.put("CF", "+236");
        prefix_list.put("CG", "+242");
        prefix_list.put("CH", "+41");
        prefix_list.put("CI", "+225");
        prefix_list.put("CK", "+682");
        prefix_list.put("CL", "+56");
        prefix_list.put("CM", "+237");
        prefix_list.put("CN", "+86");
        prefix_list.put("CO", "+57");
        prefix_list.put("CR", "+506");
        prefix_list.put("CS", "+381");
        prefix_list.put("CU", "+53");
        prefix_list.put("CV", "+238");
        prefix_list.put("CX", "+61");
        prefix_list.put("CY", "+90-392");
        prefix_list.put("CY", "+357");
        prefix_list.put("CZ", "+420");
        prefix_list.put("DE", "+49");
        prefix_list.put("DJ", "+253");
        prefix_list.put("DK", "+45");
        prefix_list.put("DM", "+1-767");
        prefix_list.put("DO", "+1-809"); // and 1-829?
        prefix_list.put("DZ", "+213");
        prefix_list.put("EC", "+593");
        prefix_list.put("EE", "+372");
        prefix_list.put("EG", "+20");
        prefix_list.put("EH", "+212");
        prefix_list.put("ER", "+291");
        prefix_list.put("ES", "+34");
        prefix_list.put("ET", "+251");
        prefix_list.put("FI", "+358");
        prefix_list.put("FJ", "+679");
        prefix_list.put("FK", "+500");
        prefix_list.put("FM", "+691");
        prefix_list.put("FO", "+298");
        prefix_list.put("FR", "+33");
        prefix_list.put("GA", "+241");
        prefix_list.put("GB", "+44");
        prefix_list.put("GD", "+1-473");
        prefix_list.put("GE", "+995");
        prefix_list.put("GF", "+594");
        prefix_list.put("GG", "+44");
        prefix_list.put("GH", "+233");
        prefix_list.put("GI", "+350");
        prefix_list.put("GL", "+299");
        prefix_list.put("GM", "+220");
        prefix_list.put("GN", "+224");
        prefix_list.put("GP", "+590");
        prefix_list.put("GQ", "+240");
        prefix_list.put("GR", "+30");
        prefix_list.put("GT", "+502");
        prefix_list.put("GU", "+1-671");
        prefix_list.put("GW", "+245");
        prefix_list.put("GY", "+592");
        prefix_list.put("HK", "+852");
        prefix_list.put("HN", "+504");
        prefix_list.put("HR", "+385");
        prefix_list.put("HT", "+509");
        prefix_list.put("HU", "+36");
        prefix_list.put("ID", "+62");
        prefix_list.put("IE", "+353");
        prefix_list.put("IL", "+972");
        prefix_list.put("IM", "+44");
        prefix_list.put("IN", "+91");
        prefix_list.put("IO", "+246");
        prefix_list.put("IQ", "+964");
        prefix_list.put("IR", "+98");
        prefix_list.put("IS", "+354");
        prefix_list.put("IT", "+39");
        prefix_list.put("JE", "+44");
        prefix_list.put("JM", "+1-876");
        prefix_list.put("JO", "+962");
        prefix_list.put("JP", "+81");
        prefix_list.put("KE", "+254");
        prefix_list.put("KG", "+996");
        prefix_list.put("KH", "+855");
        prefix_list.put("KI", "+686");
        prefix_list.put("KM", "+269");
        prefix_list.put("KN", "+1-869");
        prefix_list.put("KP", "+850");
        prefix_list.put("KR", "+82");
        prefix_list.put("KW", "+965");
        prefix_list.put("KY", "+1-345");
        prefix_list.put("KZ", "+7");
        prefix_list.put("LA", "+856");
        prefix_list.put("LB", "+961");
        prefix_list.put("LC", "+1-758");
        prefix_list.put("LI", "+423");
        prefix_list.put("LK", "+94");
        prefix_list.put("LR", "+231");
        prefix_list.put("LS", "+266");
        prefix_list.put("LT", "+370");
        prefix_list.put("LU", "+352");
        prefix_list.put("LV", "+371");
        prefix_list.put("LY", "+218");
        prefix_list.put("MA", "+212");
        prefix_list.put("MC", "+377");
        prefix_list.put("MD", "+373-533");
        prefix_list.put("MD", "+373");
        prefix_list.put("ME", "+382");
        prefix_list.put("MG", "+261");
        prefix_list.put("MH", "+692");
        prefix_list.put("MK", "+389");
        prefix_list.put("ML", "+223");
        prefix_list.put("MM", "+95");
        prefix_list.put("MN", "+976");
        prefix_list.put("MO", "+853");
        prefix_list.put("MP", "+1-670");
        prefix_list.put("MQ", "+596");
        prefix_list.put("MR", "+222");
        prefix_list.put("MS", "+1-664");
        prefix_list.put("MT", "+356");
        prefix_list.put("MU", "+230");
        prefix_list.put("MV", "+960");
        prefix_list.put("MW", "+265");
        prefix_list.put("MX", "+52");
        prefix_list.put("MY", "+60");
        prefix_list.put("MZ", "+258");
        prefix_list.put("NA", "+264");
        prefix_list.put("NC", "+687");
        prefix_list.put("NE", "+227");
        prefix_list.put("NF", "+672");
        prefix_list.put("NG", "+234");
        prefix_list.put("NI", "+505");
        prefix_list.put("NL", "+31");
        prefix_list.put("NO", "+47");
        prefix_list.put("NP", "+977");
        prefix_list.put("NR", "+674");
        prefix_list.put("NU", "+683");
        prefix_list.put("NZ", "+64");
        prefix_list.put("OM", "+968");
        prefix_list.put("PA", "+507");
        prefix_list.put("PE", "+51");
        prefix_list.put("PF", "+689");
        prefix_list.put("PG", "+675");
        prefix_list.put("PH", "+63");
        prefix_list.put("PK", "+92");
        prefix_list.put("PL", "+48");
        prefix_list.put("PM", "+508");
        prefix_list.put("PR", "+1-787"); // and 1-939 ?
        prefix_list.put("PS", "+970");
        prefix_list.put("PT", "+351");
        prefix_list.put("PW", "+680");
        prefix_list.put("PY", "+595");
        prefix_list.put("QA", "+974");
        prefix_list.put("RE", "+262");
        prefix_list.put("RO", "+40");
        prefix_list.put("RS", "+381");
        prefix_list.put("RU", "+7");
        prefix_list.put("RW", "+250");
        prefix_list.put("SA", "+966");
        prefix_list.put("SB", "+677");
        prefix_list.put("SC", "+248");
        prefix_list.put("SD", "+249");
        prefix_list.put("SE", "+46");
        prefix_list.put("SG", "+65");
        prefix_list.put("SH", "+290");
        prefix_list.put("SI", "+386");
        prefix_list.put("SJ", "+47");
        prefix_list.put("SK", "+421");
        prefix_list.put("SL", "+232");
        prefix_list.put("SM", "+378");
        prefix_list.put("SN", "+221");
        prefix_list.put("SO", "+252");
        prefix_list.put("SO", "+252");
        prefix_list.put("SR", "+597");
        prefix_list.put("ST", "+239");
        prefix_list.put("SV", "+503");
        prefix_list.put("SY", "+963");
        prefix_list.put("SZ", "+268");
        prefix_list.put("TA", "+290");
        prefix_list.put("TC", "+1-649");
        prefix_list.put("TD", "+235");
        prefix_list.put("TG", "+228");
        prefix_list.put("TH", "+66");
        prefix_list.put("TJ", "+992");
        prefix_list.put("TK", "+690");
        prefix_list.put("TL", "+670");
        prefix_list.put("TM", "+993");
        prefix_list.put("TN", "+216");
        prefix_list.put("TO", "+676");
        prefix_list.put("TR", "+90");
        prefix_list.put("TT", "+1-868");
        prefix_list.put("TV", "+688");
        prefix_list.put("TW", "+886");
        prefix_list.put("TZ", "+255");
        prefix_list.put("UA", "+380");
        prefix_list.put("UG", "+256");
        prefix_list.put("US", "+1");
        prefix_list.put("UY", "+598");
        prefix_list.put("UZ", "+998");
        prefix_list.put("VA", "+379");
        prefix_list.put("VC", "+1-784");
        prefix_list.put("VE", "+58");
        prefix_list.put("VG", "+1-284");
        prefix_list.put("VI", "+1-340");
        prefix_list.put("VN", "+84");
        prefix_list.put("VU", "+678");
        prefix_list.put("WF", "+681");
        prefix_list.put("WS", "+685");
        prefix_list.put("YE", "+967");
        prefix_list.put("YT", "+262");
        prefix_list.put("ZA", "+27");
        prefix_list.put("ZM", "+260");
        prefix_list.put("ZW", "+263");
    }  //fill prefix list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_contacts);
        _context = this;

        contactsRecyclerView = findViewById(R.id.contacts_list);
        selectedContactsRecyclerView = findViewById(R.id.selected_contacts_list);
        btBack = findViewById(R.id.bt_back);
        btInvite = findViewById(R.id.invite);
        selectedCount = findViewById(R.id.selected_count);
        selectedContactsAdapter = new SelectedContactsAdapter(Glide.with(this), this);
        selectedContactsRecyclerView.setAdapter(selectedContactsAdapter);

        contactsList = new ArrayList<>();
        //invited_contacts_list = new ArrayList<String>();

        loadingContacts();

        btBack.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Other" button
             */
            public void onClick(View view) {
                Intent intent = new Intent(InviteContactsActivity.this, DoctorViewActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    /**
     * Create a contacts list (ListView) in order to be selected
     */
    @SuppressLint("StaticFieldLeak")
    private void loadingContacts() {
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
                    contactsAdapter = new ContactsAdapter(contactsList, Glide.with(InviteContactsActivity.this), InviteContactsActivity.this);
                    contactsRecyclerView.setAdapter(contactsAdapter);
                    updateSelectedContacts();
                }
            }

            @Override
            protected Void doInBackground(Void... params) {
                contactsList = readContacts();
                return null;
            }

        }.execute((Void[]) null);
    }

    /**
     * when a contact is clicked then is invited
     * @param contact
     */
    @Override
    public void onContactClicked(Contact contact) {
        contact.toggleSelected();

        final String phone_number= contact.getPhone(); //get phone number from contact selected
        final String phone_number_with_prefix = addPrefix(phone_number); // add prefix if missing

        /**
         *  open an Alert dialog which asks if user has selected  right number
         *  if click on "yes" then send selected contacts to Server
         *  else if click on "no" then the user can change contacts
         */
        new AlertDialog.Builder(InviteContactsActivity.this)
                .setTitle(R.string.alertdial_invite_title)
                .setMessage(contact.getName() + ":" + phone_number_with_prefix)
                .setPositiveButton(R.string.alertdial_yes, new DialogInterface.OnClickListener() { //invite new doctor
                    public void onClick(DialogInterface dialog, int which) {
                        //send contact to Server
                        task_inviteDoctor(phone_number_with_prefix);                     //call inviteDoctor Api
                    }
                })
                .setNegativeButton(R.string.alertdial_no, new DialogInterface.OnClickListener() { //change number selected list
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

    }

    /* DA RIMUOVERE*/

    @Override
    public void onRemoveContact(Contact contact) {
        contact.toggleSelected();
        updateSelectedContacts();
        contactsAdapter.notifyDataSetChanged();
    }

    private void updateSelectedContacts() {
        final ArrayList<Contact> selectedContacts = new ArrayList<>();
        for (final Contact contact : contactsList) {
            if (contact.isSelected()) selectedContacts.add(contact);
        }
        selectedContactsAdapter.setContacts(selectedContacts);
        selectedCount.setText(getString(R.string.invites_count, selectedContacts.size(), contactsList.size()));
    }


    /**
     * Read list of Contacts on  smartphone
     *
     * @return the array list of contact
     */
    private ArrayList<Contact> readContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String photo = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    if (pCur.moveToFirst()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String contact = name + ":" + phone.replaceAll("\\s+", "").replaceAll("-", "");   //replaced white spaces and "-" from number
                        contacts.add(new Contact(name, phone, photo));
                    }
                    pCur.close();
                }
            }
        }
        return contacts;
    }


    /******************* Task Function **************************/

    /**
     * Run task in order to call inviteDoctor API and manage the response
     *
     * @param phone_number: phone number of Doctor to invite
     */
    private void task_inviteDoctor(final String phone_number) {

        Toast.makeText(getApplicationContext(), R.string.toast_click_invite_doc, Toast.LENGTH_SHORT).show();

        Task.callInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {

                PreferenceManager pm = new PreferenceManager(getApplicationContext());
                Boolean updated = false;
                String ret_value = "";

                while (updated == false) {

                    Response response_inviteDoctor = ApiManager.inviteDoctor(phone_number, pm.getJwtToken());  //call inviteDoctor
                    pm.getDoctorId();
                    if (response_inviteDoctor != null) {
                        switch (response_inviteDoctor.code()) { //check response status(code)
                            case 200:     // if response is 'ok' -> new contact(doctor) has been sent
                                Log.d("task_inviteDoctor", "new contact has been sent");
                                ret_value = "newdoc";
                                updated = true;
                                break;

                            case 403:    // if jwt token is not sent -> call refreshJwtToken and recall task_inviteDoctor
                            case 401:    // if jwt token is expired -> call refreshJwtToken and recall task_inviteDoctor
                                Log.d("task_inviteDoctor", "Jwt Token expired");

                                Response response_refreshJwtToken = ApiManager.refreshJwtToken(pm.getAuthorizationToken());  //call refreshJwtToken

                                if (response_refreshJwtToken != null &&
                                        response_refreshJwtToken.code() == 200) { //check response status(code)

                                    try {
                                        String strResponse_body = response_refreshJwtToken.body().string(); //get body of Response
                                        JSONObject response_body = new JSONObject(strResponse_body);
                                        pm.setJwtToken(response_body.getString("token"));             //save new Jwt Token in shared preferences

                                    } catch (Exception e) {
                                        Log.d("task_inviteDoctor", "Error to read jwt token received");
                                    }

                                }
                                break;
                            default:
                                Log.d("task_inviteDoctor", "Code not recognized:" + response_inviteDoctor.code());
                                updated = true;
                                break;
                        }
                    } else { // no response from Backend (like: internet disabled)
                        Log.d("task_inviteDoctor", "No response by task_inviteDoctor");
                        ret_value = "no_resp";
                        updated = true;
                    }
                }
                return ret_value;

            }
        }).onSuccess(new Continuation<String, Object>() {
            @Override
            public String then(Task<String> task) throws Exception {

                switch (task.getResult()) {
                    case "newdoc": //if new doctor has been invited
                        Toast.makeText(getApplicationContext(), R.string.toast_num_doc_invited, Toast.LENGTH_SHORT).show();
                        break;
                    case "no_resp": // no response from Backend (like: internet disabled)
                        Toast.makeText(getApplicationContext(), R.string.toast_no_resp_inv_doc, Toast.LENGTH_LONG).show();
                        break;
                    default: //some errors
                        Toast.makeText(getApplicationContext(), R.string.toast_err_doc_inv, Toast.LENGTH_LONG).show();
                        break;
                }
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }


    /**
     * Take phone number, if prefix is missing then add it based on country of mobile device
     *
     * @param number has to be invited
     * @return number with prefix (if missing)
     */
    private String addPrefix(String number) {
        String number_with_pref = number;

        String iso_country = "";
        if (!number.contains("+")) { //if number not contain prefix then add it with locale prefix

            TelephonyManager manager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            iso_country = manager.getNetworkCountryIso();  //get country from ISO of SIM

            String prefix = prefix_list.get(iso_country.toUpperCase());
            number_with_pref = prefix + number;  //add prefix to number
        }

        return number_with_pref;
    }


    /* Manage back button when is pressed in order to go to DoctorViewActivity */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DoctorViewActivity.class);
        startActivity(intent);
        finish();
    }
}
