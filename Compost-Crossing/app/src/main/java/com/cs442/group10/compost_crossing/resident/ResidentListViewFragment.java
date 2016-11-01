package com.cs442.group10.compost_crossing.resident;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cs442.group10.compost_crossing.R;

import static android.R.id.list;

public class ResidentListViewFragment extends Fragment {


    public ResidentListViewFragment() {
    }

    public static ResidentListViewFragment newInstance(int columnCount) {
        ResidentListViewFragment fragment = new ResidentListViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resident_item, container, false);
        ListView listView = (ListView) view.findViewById(R.id.residentItemListView);

        ResidentListViewAdapter residentListViewAdapter = new ResidentListViewAdapter(getActivity());
        listView.setAdapter(residentListViewAdapter);

        return view;
        /*setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,ads));
        return super.onCreateView(inflater, container, savedInstanceState);*/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
