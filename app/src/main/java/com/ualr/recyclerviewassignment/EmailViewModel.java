package com.ualr.recyclerviewassignment;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.model.InboxAdapter;

import java.util.ArrayList;

public class EmailViewModel extends ViewModel {
    public InboxAdapter inboxAdapter;
    public ArrayList<Inbox> inboxList;

    public EmailViewModel() {
        // create inbox list
        inboxList = new ArrayList<Inbox>();

        // create and set adapter for recyclerview
        inboxAdapter = new InboxAdapter(inboxList);
        inboxAdapter.setOnItemClickListener(new InboxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Inbox obj, int position) {
                int prev_selected_position = inboxAdapter.selected_position;
                if (position == prev_selected_position)
                    inboxAdapter.selected_position = -1;
                else inboxAdapter.selected_position = position;
                inboxAdapter.notifyItemChanged(prev_selected_position);
                inboxAdapter.notifyItemChanged(inboxAdapter.selected_position);
            }
        });
    }

    boolean deleteSelectedInbox() {
        return inboxAdapter.deleteItem(inboxAdapter.selected_position);
    }

    void addInbox(Inbox inbox) {
        inboxList.add(0, inbox);
        inboxAdapter.notifyItemInserted(0);
        inboxAdapter.selected_position++;
    }

    Inbox getSelectedItem() {
        int position = inboxAdapter.selected_position;
        if (position >= 0 && position < inboxList.size())
            return inboxList.get(inboxAdapter.selected_position);
        return null;
    }

    boolean isItemSelected() {
        int position = inboxAdapter.selected_position;
        if (position < 0 || position >= inboxList.size())
            return false;
        return true;
    }
}
