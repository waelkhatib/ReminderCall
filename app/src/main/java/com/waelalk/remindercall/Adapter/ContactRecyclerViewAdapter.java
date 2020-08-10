package com.waelalk.remindercall.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;

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

import com.waelalk.remindercall.Helper.Application;
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

    private List<String> mData;
    private LayoutInflater mInflater;
    private Context context;


    // data is passed into the constructor
    public ContactRecyclerViewAdapter(Context context, List<String> data) {
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
    private PopupWindow initiatePopupWindow(View pop) {

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
            set_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDropdown.dismiss();

// Set up the input
                    View viewInflated = LayoutInflater.from(context).inflate(R.layout.input_dialog, null);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    AlertDialog dialog = new AlertDialog.Builder(context)

                    .setView(viewInflated)

// Set up the buttons
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                    //        m_Text = input.getText().toString();
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
        String animal = mData.get(position);
        holder.myTextView.setText(animal);
        holder.popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               initiatePopupWindow(v);
            }
        });
    /*    holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=  new AlertDialog.Builder(context).setTitle("Reset contact list")
                        .setMessage("Are you sure you want to reset contact list for this time?")

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
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context. getResources(). getColor( R.color.colorAccent));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources(). getColor( R.color.colorAccent));
            }
        });*/
        holder.spinner.setFocusable(false);
        holder.spinner.setFocusableInTouchMode(false);
        //holder.spinner.setFocusedByDefault(false);
        holder.spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"xz",Toast.LENGTH_SHORT).show();
                ContactSearchDialogCompat dialog=   new ContactSearchDialogCompat<>(context, context.getString(R.string.search),
                        context.getString(R.string.what_look_for), null, createSampleContacts(),new SearchResultListener<Contact_Info>() {
                    @Override
                    public void onSelected(
                            BaseSearchDialogCompat dialog,
                            Contact_Info item, int position
                    ) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                dialog.getPositiveButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((EditText)view).setText (((ContactModelAdapter)(dialog.getAdapter())).getSelectedItems());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private ArrayList<Contact_Info> createSampleContacts() {
        ArrayList<Contact_Info> contact_infos=new ArrayList<>();
        contact_infos.add(new Contact_Info("",""));
        for(Map.Entry<String,String> entry : ((ContactsActivity)context).getContactList().entrySet()){
            contact_infos.add(new Contact_Info(entry.getKey(),entry.getValue()));
        }
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
    String getItem(int id) {
        return mData.get(id);
    }

}
