package com.wakeup.zodiac.view;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    public ProgressDialog progressDialog;

    @Override // android.support.v4.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.progressDialog = new ProgressDialog(getContext());
    }

    public void progress() {
        ProgressDialog progressDialog1 = new ProgressDialog(getContext());
        progressDialog1.setTitle("please wait");
        progressDialog1.setMessage("please wait");
        progressDialog1.show();
    }

    public void dismiss() {
        new ProgressDialog(getContext()).dismiss();
    }
}
