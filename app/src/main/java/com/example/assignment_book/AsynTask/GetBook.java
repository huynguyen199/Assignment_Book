package com.example.assignment_book.AsynTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_book.Adapter.BookAdapter;
import com.example.assignment_book.Https.HttpHandler;
import com.example.assignment_book.Https.HttpHeaders;
import com.example.assignment_book.MainActivity;
import com.example.assignment_book.Model.Book;
import com.example.assignment_book.Model.Categories;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetBook extends AsyncTask<String, Void, Void> {
    private String TAG = MainActivity.class.getSimpleName();
    public static String url = "http://10.0.2.2:80/Assignment/getAllProduct.php";

    ArrayList<Book> BookList;

    private ProgressDialog pDialog;
    private RecyclerView recyclerview;
    Context context;
    BookAdapter adapter;

    GetCategories getCategories;

    public ArrayList<Book> getBookList() {
        return BookList;
    }

    public BookAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BookAdapter adapter) {
        this.adapter = adapter;
    }

    public void setBookList(ArrayList<Book> bookList) {
        BookList = bookList;
    }

    public GetBook(RecyclerView recyclerview, Context context) {
        this.recyclerview = recyclerview;
        this.context = context;
        this.BookList = new ArrayList<>();
    }


    @Override
    protected Void doInBackground(String... strings) {
        GetAllProduct();


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


    private void GetAllProduct() {
        HttpHandler handler = new HttpHandler();
        // making request to url and getting response
        String jsonStr = handler.makeServiceCall(url);
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
                    Log.i(TAG, "doInBackground: " + id);
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
        adapter = new BookAdapter(BookList, context);


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager2);
        recyclerview.setAdapter(adapter);

    }

    public GetCategories getGetCategories() {
        return getCategories;
    }

    public void setGetCategories(GetCategories getCategories) {
        this.getCategories = getCategories;
    }
}
