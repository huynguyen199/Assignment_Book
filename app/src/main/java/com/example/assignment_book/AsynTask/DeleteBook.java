package com.example.assignment_book.AsynTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.assignment_book.Https.HttpHandler;
import com.example.assignment_book.MainActivity;
import com.example.assignment_book.Model.Book;

import org.json.JSONArray;
import org.json.JSONObject;

public class DeleteBook extends AsyncTask<String, Void, Void> {
    private static String id;
    Context context;

    private String TAG = MainActivity.class.getSimpleName();



    private ProgressDialog pDialog;


    public static String url = "http://10.0.2.2:80/Assignment/deleteProduct.php?id=";


    public DeleteBook(Context context,String id) {
        this.context = context;
        this.id = id;
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
        Log.i(TAG, "doInBackground: "+url+id);
        HttpHandler handler = new HttpHandler();
        // making request to url and getting response
        String jsonStr = handler.makeServiceDelete(url+id);
        if (jsonStr != null) {
            try {
                Log.i(TAG, "jsonStr "+jsonStr);
//                JSONObject jsonObject = new JSONObject(jsonStr);
//
//                // Getting JSON Array node
//                JSONObject jsonArray = jsonObject;
//                // looping through all Contacts
//                String message = jsonArray.getString("message");
//                if(message.equals("success")){
//
//                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {

        }
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
