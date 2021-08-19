package com.example.assignment_book.AsynTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.assignment_book.BookDetailsActivity;
import com.example.assignment_book.Https.HttpHandler;
import com.example.assignment_book.Https.HttpHeaders;
import com.example.assignment_book.MainActivity;
import com.example.assignment_book.Model.Book;
import com.example.assignment_book.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostDetailBook extends AsyncTask<String, Void, Void> {
    private String TAG = MainActivity.class.getSimpleName();
    public static String url = "http://10.0.2.2:80/Assignment/detailProduct.php";

    ArrayList<Book> BookList;

    Context context;
    Map<String, String> params;


    private ProgressDialog pDialog;

    public TextView tvTitle, tvPrice, tvName, tvDescription;


    public PostDetailBook(Context context, Map<String, String> params) {
        this.context = context;
        this.params = params;
        this.BookList = new ArrayList<Book>();
    }


    @Override
    protected Void doInBackground(String... strings) {
        GetIdProduct();

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


    private void GetIdProduct() {
        HttpHandler handler = new HttpHandler();
        // making request to url and getting response
        String jsonStr = handler.makeServiceFindId(url, params);
        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray Book = jsonObject.getJSONArray("Book");
                // looping through all Contacts
                for (int i = 0; i < Book.length(); i++) {
                    JSONObject c = Book.getJSONObject(i);
                    String id = c.getString("id");
                    String title = c.getString("title");
                    String name = c.getString("name");
                    Log.i(TAG, "doInBackground: " + title);
                    //receive data
                    Book book = new Book();
                    book.setId(id);
                    book.setName(name);
                    book.setTitle(title);
                    BookList.add(book);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        initViewFindId();
        Book book = BookList.get(0);
        tvTitle.setText(book.getTitle());
        tvName.setText(book.getName());

    }

    public void initViewFindId() {
        tvTitle = ((Activity) context).findViewById(R.id.tvTitle);
        tvPrice = ((Activity) context).findViewById(R.id.tvPrice);
        tvName = ((Activity) context).findViewById(R.id.tvName);
        tvDescription = ((Activity) context).findViewById(R.id.tvDescription);

    }


}
