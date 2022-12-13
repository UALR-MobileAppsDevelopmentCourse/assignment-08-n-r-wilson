package com.ualr.recyclerviewassignment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.model.InboxAdapter;


import java.util.ArrayList;

public class InboxListFragment extends Fragment {

    private FloatingActionButton mFAB;
    private ArrayList<Inbox> inboxList;
    private RecyclerView recyclerView;
    private InboxAdapter inboxAdapter;
    private EmailViewModel emailViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox_list, container, false);

        MainActivity mainActivity = (MainActivity)view.getContext();
        emailViewModel = new ViewModelProvider(requireActivity()).get(EmailViewModel.class);
        inboxList = emailViewModel.inboxList;
        inboxAdapter = emailViewModel.inboxAdapter;

        // init data in inbox list
        if (inboxList.size() == 0) {
            for (int i = 0; i < 5; i++) {
                inboxList.add(DataGenerator.getRandomInboxItem(getContext()));
            }
        }

        // create recyclerview for emails
        recyclerView = view.findViewById(R.id.inbox);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(inboxAdapter);

        // add item to list when button is clicked
        mFAB = view.findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inboxList.add(0, DataGenerator.getRandomInboxItem(view.getContext()));
                recyclerView.getAdapter().notifyItemInserted(0);
            }
        });

        return view;
    }
}