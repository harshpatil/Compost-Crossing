package com.cs442.group10.compost_crossing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cheth on 10/29/2016.
 */

public class CompostDetailViewFragment extends Fragment {

    public CompostDetailViewFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.compost_detail_view_fragment, container, false);
        return view;
    }
}
