package com.ualr.recyclerviewassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.ualr.recyclerviewassignment.model.Inbox;

public class MainActivity extends AppCompatActivity {
    public EmailViewModel emailViewModel;
    private static final String FRAGMENT_TAG = "ForwardDialog";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_action:
                if (emailViewModel.deleteSelectedInbox())
                    Snackbar.make(findViewById(R.id.drawer_layout_main), "Email deleted", Snackbar.LENGTH_LONG).show();
                return true;
            case R.id.forward_action:
                showForwardDialog();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailViewModel = ViewModelProviders.of(this).get(EmailViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.email_fragment_container, InboxListFragment.class, null);
            transaction.commit();
        }
    }

    public void showForwardDialog() {
        if (!emailViewModel.isItemSelected()) return;
        Inbox inbox = emailViewModel.getSelectedItem();
        if (inbox == null) return;
        FragmentManager fm = getSupportFragmentManager();
        ForwardDialogFragment forwardDialogFragment = ForwardDialogFragment.newInstance(inbox.getEmail(), inbox.getFrom(), inbox.getMessage(), inbox.getDate());
        forwardDialogFragment.show(fm, "forward_fragment");
    }



}