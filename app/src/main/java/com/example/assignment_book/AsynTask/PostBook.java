package com.example.assignment_book.AsynTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.assignment_book.Https.HttpHandler;
import com.example.assignment_book.MainActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostBook extends AsyncTask<String, Void, Void> {
    private String TAG = MainActivity.class.getSimpleName();



    private ProgressDialog pDialog;

    Context context;
    Map<String, String> params = new HashMap<String, String>();



    public static String url = "http://10.0.2.2:80/Assignment/insertProduct.php";


    public PostBook(Context context, Map<String, String> params) {
        this.context = context;
        this.params = params;
    }

    @Override
    protected Void doInBackground(String... strings) {
        Log.i(TAG, "doInBackground: " + url);
        HttpHandler handler = new HttpHandler();
        // making request to url and getting response
        String jsonStr = handler.makeServiceInsert(url,params);
        Log.i(TAG, "jsonStr "+jsonStr);

        if (jsonStr != null) {
            try {
                Log.i(TAG, "jsonStr "+jsonStr);
                JSONObject jsonObject = new JSONObject(jsonStr);
//
//                // Getting JSON Array node
////                JSONObject jsonArray = jsonObject;
////                // looping through all Contacts
////                String message = jsonArray.getString("message");
////                if(message.equals("success")){
////
////                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }


        return null;
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
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
