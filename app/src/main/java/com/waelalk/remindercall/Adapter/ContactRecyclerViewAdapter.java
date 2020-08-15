package com.waelalk.remindercall.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.paris.Paris;
import com.waelalk.remindercall.Helper.Application;
import com.waelalk.remindercall.Model.Appointment;
import com.waelalk.remindercall.Model.Contact_Info;
import com.waelalk.remindercall.R;
import com.waelalk.remindercall.View.ContactSearchDialogCompat;
import com.waelalk.remindercall.View.ContactsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder> {

    private List<Appointment> mData;
    private LayoutInflater mInflater;
    private Context context;


    // data is passed into the constructor
    public ContactRecyclerViewAdapter(Context context, List<Appointment> data) {
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.time_row, parent, false);
        return new ViewHolder(view);
    }
    private PopupWindow initiatePopupWindow(View pop,int position) {

        try {

            LayoutInflater mInflater1 = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = mInflater1.inflate(R.layout.popup_menu, null);

            //If you want to add any listeners to your textviews, these are two //textviews.
            final TextView reset_contact = (TextView) layout.findViewById(R.id.reset_contact);
           final PopupWindow mDropdown = new PopupWindow(layout,FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,true);
            reset_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDropdown.dismiss();
                    AlertDialog dialog=  new AlertDialog.Builder(context).setTitle(R.string.reset_contact_list)
                            .setMessage(R.string.reset_contact_list_question)

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    mData.get(position).getContact_infoList().clear();
                                    notifyDataSetChanged();
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)

                            .show();
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context. getResources(). getColor( R.color.colorAccent));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources(). getColor( R.color.colorAccent));
                }
            });


            final TextView set_msg = (TextView) layout.findViewById(R.id.set_msg);
            set_msg.setText(mData.get(position).getMessage_text().equals("")?R.string.set_message:R.string.view_message);
            set_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDropdown.dismiss();

// Set up the input
                    View viewInflated = LayoutInflater.from(context).inflate(R.layout.input_dialog, null);
                    EditText input=viewInflated.findViewById(R.id.msgText);
                    input.setText(mData.get(position).getMessage_text());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    AlertDialog dialog = new AlertDialog.Builder(context)

                    .setView(viewInflated)

// Set up the buttons
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mData.get(position).setMessage_text( input.getText().toString().trim());
                        }
                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context. getResources(). getColor( R.color.colorAccent));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources(). getColor( R.color.colorAccent));

                }
            });



            layout.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);

            Drawable background = context.getResources().getDrawable(android.R.drawable.editbox_dropdown_light_frame);
            mDropdown.setBackgroundDrawable(background);
            int[] a = new int[2]; //getLocationInWindow required array of size 2
            pop.getLocationInWindow(a);
            mDropdown.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.NO_GRAVITY, a[0]+pop.getWidth()/2-(Application.getDisplayFactor()>0?layout.getMeasuredWidth():0) , a[1]+pop.getHeight());
            mDropdown.update();
            return mDropdown;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String time = mData.get(position).getTime();
        holder.myTextView.setText(time);
        String contacts_name=getContactNamesOnly(mData.get(position).getContact_infoList());
        if(contacts_name.length()>20){
            contacts_name=contacts_name.substring(0,20)+"...";
        }
        holder.spinner.setText (contacts_name);
        Paris.style(holder.spinner).apply(contacts_name.length()==0?R.style.SpinnerTheme:R.style.EditTextTheme);
        holder.popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               initiatePopupWindow(v,position);
            }
        });

        holder.spinner.setFocusable(false);
        holder.spinner.setFocusableInTouchMode(false);
        //holder.spinner.setFocusedByDefault(false);
        holder.spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setOnClickListener(null);
                final View.OnClickListener event=this;
//                Toast.makeText(context,"xz",Toast.LENGTH_SHORT).show();
                ContactSearchDialogCompat dialog=   new ContactSearchDialogCompat<Contact_Info>(context, context.getString(R.string.search),
                        context.getString(R.string.what_look_for), null, createSampleContacts(mData.get(position).getContact_infoList()),mData.get(position).getContact_infoList(),new SearchResultListener<Contact_Info>() {
                    @Override
                    public void onSelected(
                            BaseSearchDialogCompat dialog,
                            Contact_Info item, int position
                    ) {
                        view.setOnClickListener(event);
                        dialog.dismiss();
                    }
                });

                dialog.show();
                dialog.getPositiveButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mData.get(position).setContact_infoList(((ContactModelAdapter)(dialog.getAdapter())).getSelectedItems());

                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                });
                dialog.getNegativeButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.setOnClickListener(event);
                       dialog. dismiss();
                    }
                });
            }
        });
    }

    private String getContactNamesOnly(List<Contact_Info> contact_infoList) {
        List<String> names=new ArrayList<>();
        for(Contact_Info contact_info:contact_infoList){
            names.add(contact_info.getName());
        }
        return names.isEmpty()?"":TextUtils.join(",",names);
    }

    private ArrayList<Contact_Info> createSampleContacts(List<Contact_Info> current_contacts) {
        ArrayList<Contact_Info> contact_infos=new ArrayList<Contact_Info> ();
        ArrayList<Contact_Info> temp=new ArrayList<Contact_Info> ();
        temp.addAll(current_contacts);
        contact_infos.addAll(((ContactsActivity)context).getContactList());
        temp.removeAll(contact_infos);
        contact_infos.addAll(1,temp);
        return contact_infos;
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView myTextView;
        ImageButton popup_menu;
        EditText spinner;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.timeVal);
            popup_menu=itemView.findViewById(R.id.popup_menu);
            spinner=itemView.findViewById(R.id.spinner);
        }


    }

    // convenience method for getting data at click position
    Appointment getItem(int id) {
        return mData.get(id);
    }

}
