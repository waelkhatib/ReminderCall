package com.waelalk.remindercall.View;

import android.content.Context;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.waelalk.remindercall.Adapter.ContactModelAdapter;
import com.waelalk.remindercall.R;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.FilterResultListener;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class ContactSearchDialogCompat<T extends Searchable> extends BaseSearchDialogCompat<T> {
    private String mTitle;
    private String mSearchHint;
    private SearchResultListener<T> mSearchResultListener;
    private Context mContext;

    public View getPositiveButton() {
        return positiveButton;
    }

    private View positiveButton;

    public ContactSearchDialogCompat(
            Context context, String title, String searchHint,
            @Nullable Filter filter, ArrayList<T> items,
            SearchResultListener<T> searchResultListener
    ) {
        super(context, items, filter, null, null);
        init(context, title, searchHint, searchResultListener);
    }

    private void init(
            Context context,
            String title, String searchHint,
            SearchResultListener<T> searchResultListener
    ) {
        mTitle = title;
        mSearchHint = searchHint;
        mSearchResultListener = searchResultListener;
        mContext=context;
    }

    @Override
    protected void getView(View view) {
        setContentView(view);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(true);
        TextView txtTitle = (TextView) view.findViewById(R.id.txt_title);
        final EditText searchBox = (EditText) view.findViewById(getSearchBoxId());
        txtTitle.setText(mTitle);
        searchBox.setHint(mSearchHint);
       /* view.findViewById(ir.mirrajabi.searchdialog.R.id.dummy_background)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });*/
       positiveButton=view.findViewById(R.id.ok);
//       view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               Toast.makeText(mContext,"Ok",Toast.LENGTH_SHORT).show();
//           }
//       });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        final ContactModelAdapter adapter = new ContactModelAdapter<>(getContext(),
                getItems()
        );
        adapter.setSearchResultListener(mSearchResultListener);
        adapter.setSearchDialog(this);
        setFilterResultListener(new FilterResultListener<T>() {
            @Override
            public void onFilter(ArrayList<T> items) {
                ((ContactModelAdapter) getAdapter())
                        .setSearchTag(searchBox.getText().toString())
                        .setItems(items);
            }
        });
        setAdapter(adapter);
    }

    public ContactSearchDialogCompat setTitle(String title) {
        mTitle = title;
        return this;
    }

    public ContactSearchDialogCompat setSearchHint(String searchHint) {
        mSearchHint = searchHint;
        return this;
    }

    public ContactSearchDialogCompat setSearchResultListener(
            SearchResultListener<T> searchResultListener
    ) {
        mSearchResultListener = searchResultListener;
        return this;
    }

    @LayoutRes
    @Override
    protected int getLayoutResId() {
        return R.layout.search_dialog_compact;
    }

    @IdRes
    @Override
    protected int getSearchBoxId() {
        return R.id.txt_search;
    }

    @IdRes
    @Override
    protected int getRecyclerViewId() {
        return R.id.rv_items;
    }
}
