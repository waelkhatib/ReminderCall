package com.waelalk.remindercall.View;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.waelalk.remindercall.Adapter.ContactRecyclerViewAdapter;
import com.waelalk.remindercall.Adapter.TimeRecycleViewAdapter;
import com.waelalk.remindercall.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ContactsActivity extends AppCompatActivity {
    public static final int PICK_CONTACT = 99;
    ContactRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.help:
                startActivity(new Intent(this,About_us.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initViews() {
        ArrayList<String> times =new ArrayList<>();
        times.add("12:34");
        times.add("03:56");
        times.add("14-06-2019 11:05");
        times.add("16-11-2019 10:00");
        times.add("14:20");
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.contactRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactRecyclerViewAdapter(this, times);
        recyclerView.setAdapter(adapter);
        Button save_btn=findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=  new AlertDialog.Builder(ContactsActivity.this).setTitle("Save changes")
                        .setMessage("Are you sure you want to save these changes?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)

                        .show();
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor( getResources(). getColor( R.color.colorAccent));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources(). getColor( R.color.colorAccent));

         /*Intent intent=new Intent(TimesActivity.this, ContactPickerActivity.class)
                 .putExtra(ContactPickerActivity.EXTRA_THEME,R.style.ContactPicker)
                 .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                 .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                 .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                 .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                 .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
                startActivityForResult(intent, PICK_CONTACT);*/
            }
        });
        Button next_btn=findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactsActivity.this,ConfigurationActivity.class));
            }
        });

    }

    public Map<String,String> getContactList() {
        Map<String,String> contact=new HashMap<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = ((ContentResolver) cr).query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                       contact.put(name,phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
        return  contact;
    }
    }
