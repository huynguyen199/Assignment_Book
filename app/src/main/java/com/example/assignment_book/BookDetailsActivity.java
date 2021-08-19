package com.example.assignment_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.assignment_book.AsynTask.PostDetailBook;
import com.example.assignment_book.HandleJson.ApiService;
import com.example.assignment_book.HandleJson.RetroClient;
import com.example.assignment_book.Model.Book;
import com.example.assignment_book.Model.Cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailsActivity extends AppCompatActivity {
    private ImageButton pre_img, add_img, minus_img;

    public TextView tvTitle, tvPrice, tvName, tvDescription, tvAmount;
    ArrayList<Book> BookList;
    public Button mbtnAddCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        initViewFindId();
        handleAddToCart();

        Intent i = getIntent();
        String id = i.getStringExtra("id");
        Log.i("id", "onCreate: " + id);
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        PostDetailBook postDetailBook = new PostDetailBook(this, params);
        postDetailBook.execute();


        pre_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void handleAddToCart() {
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberOfAmount = tvAmount.getText().toString();
                int value = Integer.valueOf(numberOfAmount);
                tvAmount.setText(String.valueOf(++value));
            }
        });

        minus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberOfAmount = tvAmount.getText().toString();
                int value = Integer.valueOf(numberOfAmount);
                if (value != 0) {
                    tvAmount.setText(String.valueOf(--value));

                }
            }
        });


        mbtnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                String IdOfBook = i.getStringExtra("id");
                String numberOfAmount = tvAmount.getText().toString();

                ApiService apiLogin = RetroClient.getApiService();
                Call<Cart> call = apiLogin.getInserCart(IdOfBook,numberOfAmount);
                call.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        Log.i("response", "onResponse: "+response.body());
                        Cart cart = response.body();
                        Log.i("cart", "onResponse: "+cart.getMessage());
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {

                    }
                });



            }
        });


    }





        public void initViewFindId() {
        pre_img = findViewById(R.id.image_back);
        tvTitle = findViewById(R.id.tvTitle);
        tvPrice = findViewById(R.id.tvPrice);
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvDescription);

        //form add to cart
        add_img = findViewById(R.id.imgAdd);
        minus_img = findViewById(R.id.imgMinus);
        tvAmount = findViewById(R.id.tvAmount);
        mbtnAddCart = findViewById(R.id.btnCart);

    }


}