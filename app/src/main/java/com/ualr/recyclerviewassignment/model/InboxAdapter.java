package com.ualr.recyclerviewassignment.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;

import java.util.ArrayList;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder>{

    private ArrayList<Inbox> inboxList;
    public int selected_position = -1;
    private OnItemClickListener mOnItemClickListener;

    public InboxAdapter(ArrayList<Inbox> inboxList) {
        this.inboxList = inboxList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected InboxAdapter adapter;
        private TextView fromText;
        private TextView emailText;
        private TextView messageText;
        private TextView dateText;
        private TextView initialText;
        public View lyt_parent;

        public ViewHolder(final View view) {
            super(view);
            fromText = view.findViewById(R.id.from);
            emailText = view.findViewById(R.id.email);
            messageText = view.findViewById(R.id.message);
            dateText = view.findViewById(R.id.date);
            initialText = view.findViewById(R.id.from_initial);
            lyt_parent = view.findViewById(R.id.lyt_parent);
            lyt_parent.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClick(view, inboxList.get(getLayoutPosition()), getLayoutPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Inbox obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public InboxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxAdapter.ViewHolder holder, int position) {
        String name = inboxList.get(position).getFrom();
        holder.adapter = this;
        holder.fromText.setText(name);
        holder.emailText.setText(inboxList.get(position).getEmail());
        holder.messageText.setText(inboxList.get(position).getMessage());
        holder.dateText.setText(inboxList.get(position).getDate());
        holder.initialText.setText("" + name.charAt(0));
        if (position != selected_position) {
            holder.itemView.setActivated(false);
        } else {
            holder.itemView.setActivated(true);
        }
        if (holder.itemView.isActivated()) {
            holder.itemView.findViewById(R.id.from_initial).setVisibility(View.GONE);
            holder.itemView.findViewById(R.id.delete_button).setVisibility(View.VISIBLE);
        } else {
            holder.itemView.findViewById(R.id.from_initial).setVisibility(View.VISIBLE);
            holder.itemView.findViewById(R.id.delete_button).setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return inboxList.size();
    }

    public boolean deleteItem(int position) {
        if (position >= 0 && position < inboxList.size()) {
            inboxList.remove(position);
            notifyItemRemoved(position);
            if (position == selected_position)
                selected_position = -1;
            return true;
        }
        return false;
    }

    public void addItem(Inbox inbox) {
        inboxList.add(0, inbox);
        notifyItemInserted(0);
        selected_position++;
    }

}
