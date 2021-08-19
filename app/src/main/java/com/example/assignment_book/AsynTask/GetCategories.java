package com.example.assignment_book.AsynTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_book.Adapter.categoriesAdapter;
import com.example.assignment_book.Https.HttpHandler;
import com.example.assignment_book.MainActivity;
import com.example.assignment_book.Model.Categories;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetCategories extends AsyncTask<String, Void, Void> {
    private String TAG = MainActivity.class.getSimpleName();
    public static String url = "http://10.0.2.2:80/Assignment/getAllCategories.php";

    ArrayList<Categories> listCategories;

    private ProgressDialog pDialog;


    private RecyclerView recyclerview;
    Spinner spinner;
    Context context;


    categoriesAdapter adapter;

    public GetCategories(Spinner spinner, Context context) {
        this.spinner = spinner;
        this.context = context;
    }

    public GetCategories(RecyclerView recyclerview, Spinner spinner, Context context) {
        this.recyclerview = recyclerview;
        this.spinner = spinner;
        this.context = context;
        this.listCategories = new ArrayList<>();

    }

    @Override
    protected Void doInBackground(String... strings) {
            GetAllCategories();


        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        pDialog = new ProgressDialog(context);
//        pDialog.setMessage("Please wait...");
//        pDialog.setCancelable(false);
//        pDialog.show();
    }


    private void GetAllCategories(){
        HttpHandler handler = new HttpHandler();
        // making request to url and getting response
        String jsonStr = handler.makeServiceCall(url);
        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray categories = jsonObject.getJSONArray("Categories");
                // looping through all Contacts
                for (int i = 0; i < categories.length(); i++) {
                    JSONObject c = categories.getJSONObject(i);
                    String id = c.getString("id");
                    String name = c.getString("name");
                    Log.i(TAG, "doInBackground: "+id);
                    //receive data
                    listCategories.add(new Categories(id,name));


                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }

    public ArrayList<Categories> getListCategories() {
        return listCategories;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

            adapter = new categoriesAdapter(listCategories,context);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerview.setLayoutManager(layoutManager2);
            recyclerview.setAdapter(adapter);

            ArrayAdapter<Categories> adapter = new ArrayAdapter<Categories>(context,
                    android.R.layout.simple_spinner_item, listCategories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);



    }

}
