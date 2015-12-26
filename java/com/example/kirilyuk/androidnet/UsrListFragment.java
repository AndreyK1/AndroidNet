package com.example.kirilyuk.androidnet;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
//import android.support.v4.app.ListFragment;

/**
 * Created by Kirilyuk on 24.12.2015.
 */
public class UsrListFragment extends ListFragment implements AbsListView.OnScrollListener {
   // boolean mDualPane;
    int mCurCheckPosition = 0;
/*
    public static final UsrListFragment newInstance(String name)
    {
        UsrListFragment f = new UsrListFragment();
        Bundle bdl = new Bundle(1);

        //bdl.putInt(EXTRA_TITLE, title);

        bdl.putString("name", name);
       // bdl.pu
        f.setArguments(bdl);

        return f;
    }
*/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnScrollListener(this);
/*

        useLadapter = new UserListAdapter((MainActivity) context, R.layout.list_users, UsersArrays);
        //          UsersList1.setAdapter(useLadapter);
        */

        ArrayList<UserListModel> UsersArrays = ((MainActivity)getActivity()).UsersArrays;
       UserListAdapter useLadapter = ((MainActivity)getActivity()).useLadapter;
        // Populate list with our static array of titles.
       /*
      setListAdapter(new ArrayAdapter<String>(getActivity(),
              android.R.layout.simple_list_item_activated_1, Shakespeare.TITLES));
*/
        useLadapter =new UserListAdapter(((MainActivity)getActivity()), R.layout.list_users, UsersArrays);
        setListAdapter(useLadapter);

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        //View detailsFrame = getActivity().findViewById(R.id.details);
        //mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
            //savedInstanceState.get <ArrayList<String>>
        }

       /* if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        }*/
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        //                       UsersList.removeFooterView((LinearLayout) mLoadingFooter);
        if (visibleItemCount > 0 && firstVisibleItem + visibleItemCount == totalItemCount) {

            ((MainActivity)getActivity()).loadNextPageUsers();
        }
    }


}
