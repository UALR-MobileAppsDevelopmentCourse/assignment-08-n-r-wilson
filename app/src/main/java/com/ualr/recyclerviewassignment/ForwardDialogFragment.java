package com.ualr.recyclerviewassignment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.ualr.recyclerviewassignment.model.Inbox;

public class ForwardDialogFragment extends DialogFragment {
    private static final String STRING_EMAIL_KEY   = "email_key";
    private static final String STRING_FROM_KEY    = "from_key";
    private static final String STRING_MESSAGE_KEY = "message_key";
    private static final String STRING_DATE_KEY    = "date_key";

    // TODO: Rename and change types of parameters
    private String email_value;
    private String from_value;
    private String message_value;
    private String date_value;
    private EmailViewModel emailViewModel;

    public ForwardDialogFragment() {
        // Required empty public constructor
    }

    public static ForwardDialogFragment newInstance(String email, String from, String message, String date) {
        ForwardDialogFragment fragment = new ForwardDialogFragment();
        Bundle args = new Bundle();
        args.putString(STRING_EMAIL_KEY, email);
        args.putString(STRING_FROM_KEY, from);
        args.putString(STRING_MESSAGE_KEY, message);
        args.putString(STRING_DATE_KEY, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email_value   = getArguments().getString(STRING_EMAIL_KEY);
            from_value    = getArguments().getString(STRING_FROM_KEY);
            message_value = getArguments().getString(STRING_MESSAGE_KEY);
            date_value    = getArguments().getString(STRING_DATE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forward_dialog, container, false);
        emailViewModel = new ViewModelProvider(requireActivity()).get(EmailViewModel.class);

        EditText email_field   = view.findViewById(R.id.email_field);
        EditText from_field    = view.findViewById(R.id.from_field);
        EditText message_field = view.findViewById(R.id.message_field);

        email_field.setText(email_value);
        from_field.setText(from_value);
        message_field.setText(message_value);

        Button send_button = (Button)view.findViewById(R.id.send_button);
        send_button.setOnClickListener((e) -> {
            Inbox ret = new Inbox();
            ret.setEmail(email_field.getText().toString());
            ret.setFrom(from_field.getText().toString());
            ret.setMessage(message_field.getText().toString());
            ret.setDate(date_value);
            emailViewModel.addInbox(ret);
            Snackbar.make(((MainActivity)this.getContext()).findViewById(R.id.drawer_layout_main), "Message forwarded", Snackbar.LENGTH_LONG).show();
            this.dismiss();
        });

        return view;
    }
}