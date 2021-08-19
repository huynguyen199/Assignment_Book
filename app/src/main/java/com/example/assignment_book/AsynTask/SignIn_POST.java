package com.example.assignment_book.AsynTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class SignIn_POST extends AsyncTask<Void, Void, Void> {
    ProgressDialog qDialog;
    Context context;

    @Override
    protected Void doInBackground(Void... voids) {


        return null;
    }
    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if(qDialog.isShowing()){
            qDialog.dismiss();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        qDialog = new ProgressDialog(context);
        qDialog.setMessage("Calculating....");
        qDialog.setIndeterminate(false);
        qDialog.setCancelable(false);
        qDialog.show();
    }
}
