package com.waelalk.remindercall.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tooltip.Tooltip;
import com.waelalk.remindercall.Model.Contact_Info;
import com.waelalk.remindercall.R;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class ContactModelAdapter<T extends Searchable> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LAYOUT_ADD = 1;
    private static final int LAYOUT_VIEW = 2;
    private List<T> mData;
    private List<T> selected_contacts;
    private LayoutInflater mInflater;
    private Context context;
    private SearchResultListener mSearchResultListener;
    private String mSearchTag;
    private boolean mHighlightPartsInCommon = true;
    private String mHighlightColor = "#FFED2E47";
    private BaseSearchDialogCompat mSearchDialog;



    // data is passed into the constructor
    public ContactModelAdapter(Context context, List<T> data,List<T> selectedItems) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.selected_contacts=selectedItems;
    }

    @Override
    public int getItemViewType(int position) {
        if (((Contact_Info)mData.get(position)).getName() == "")
            return LAYOUT_ADD;
        else
            return LAYOUT_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == LAYOUT_ADD) {
            view = mInflater.inflate(R.layout.add_contact, parent, false);
            viewHolder = new ViewHolderAdd(view);
        } else {
            view = mInflater.inflate(R.layout.contact_row, parent, false);
            viewHolder = new ViewHolderShow(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType()== LAYOUT_ADD)
        {
            ((ViewHolderAdd)holder).addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text=((ViewHolderAdd)holder).cont_val.getText().toString();
                    Tooltip.Builder tooltip = new Tooltip.Builder(((ViewHolderAdd)holder).cont_val)
                            .setCancelable(true)
                            .setDismissOnClick(true)
                            .setMaxWidth((int)(((ViewHolderAdd)holder).cont_val.getWidth()*1.5))
                            .setCornerRadius(20f)
                            .setBackgroundColor(context.getResources().getColor(R.color.colorPrimary))
                            .setTextColor(context.getResources().getColor(android.R.color.white));

                    if(text.trim().equals("")){
                        tooltip.setText(context.getString(R.string.phone_not_empty))
                                .show();
                      }else
                        if(mData.contains(new Contact_Info(text,text))){
                            tooltip.setText(context.getString(R.string.phone_not_duplicated))
                                    .show();
                        }else {
                            mData.add(1,(T)new Contact_Info(text,text));
                            ((ViewHolderAdd)holder).cont_val.setFocusable(false);
                            ((ViewHolderAdd)holder).cont_val.setText("");

                            notifyDataSetChanged();
                        }
                }
            });
        }else {
            ((ViewHolderShow)holder).checkBox.setChecked(selected_contacts.contains(mData.get(position)));
            ((ViewHolderShow)holder).textView.setText(((Contact_Info)mData.get(position)).getName());
            ((ViewHolderShow)holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        selected_contacts.add((mData.get(position)));
                    }else {
                        selected_contacts.remove((mData.get(position)));
                    }
                }
            });

        }
    }

    public List<T> getSelectedItems(){
        return selected_contacts;
    }
    // total number of rows
    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }
    public SearchResultListener getSearchResultListener() {
        return mSearchResultListener;
    }

    public void setSearchResultListener(SearchResultListener searchResultListener) {
        this.mSearchResultListener = searchResultListener;
    }

    public String getSearchTag() {
        return mSearchTag;
    }

    public ContactModelAdapter setSearchTag(String searchTag) {
        mSearchTag = searchTag;
        return this;
    }

    public boolean isHighlightPartsInCommon() {
        return mHighlightPartsInCommon;
    }

    public ContactModelAdapter setHighlightPartsInCommon(boolean highlightPartsInCommon) {
        mHighlightPartsInCommon = highlightPartsInCommon;
        return this;
    }

    public ContactModelAdapter setHighlightColor(String highlightColor) {
        mHighlightColor = highlightColor;
        return this;
    }

    public ContactModelAdapter setSearchDialog(BaseSearchDialogCompat searchDialog) {
        mSearchDialog = searchDialog;
        return this;
    }


    // stores and recycles views as they are scrolled off screen

    public List<T> getItems() {
        return mData;
    }

    public void setItems(List<T> objects) {
        this.mData = objects;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mData.get(position);
    }



    public class ViewHolderAdd extends RecyclerView.ViewHolder {
        EditText cont_val;
        ImageButton addItem;
        LinearLayout mRootLayout;
        public ViewHolderAdd(View itemview) {
            super(itemview);
            cont_val=itemview.findViewById(R.id.cont_val);
            addItem=itemview.findViewById(R.id.addItem);
            mRootLayout=itemview.findViewById(R.id.root_layout);

        }
    }

    public class ViewHolderShow extends RecyclerView.ViewHolder {
        androidx.appcompat.widget.AppCompatCheckBox checkBox;
        TextView textView;
        public ViewHolderShow(View itemview) {
            super(itemview);
            checkBox=itemview.findViewById(R.id.chkbox);
            textView=itemview.findViewById(R.id.contactVal);

        }
    }
}