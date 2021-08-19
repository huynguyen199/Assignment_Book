package com.example.assignment_book.AsynTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Spinner;

import com.example.assignment_book.Https.HttpHandler;
import com.example.assignment_book.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class UpdateBook extends AsyncTask<String, Void, Void> {
    public static String url = "http://10.0.2.2:80/Assignment/updateProduct.php";

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    Context context;
    Map<String, String> params;


    public UpdateBook(Context context, Map<String, String> params) {
        this.context = context;
        this.params = params;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected Void doInBackground(String... strings) {

        Log.i(TAG, "doInBackground: " + url);
        HttpHandler handler = new HttpHandler();
        // making request to url and getting response
        String jsonStr = handler.makeServiceUpdate(url,params);
        Log.i(TAG, "jsonStr "+jsonStr);



        return null;
    }


    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
