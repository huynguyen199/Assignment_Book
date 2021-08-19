package com.example.assignment_book.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_book.HandleJson.ApiService;
import com.example.assignment_book.HandleJson.RetroClient;
import com.example.assignment_book.Model.Cart;
import com.example.assignment_book.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    public static List<Cart> list_cart;
    private Context context;


    public CartAdapter(List<Cart> list_cart, Context context) {
        this.list_cart = list_cart;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        fillData(holder, position);
        CallAddAndRemoveCart(holder, position);
    }


    private void fillData(ViewHolder holder, int position) {
        Cart cart = list_cart.get(position);
        holder.tvName.setText(cart.getName());
        holder.tvAuthors.setText(cart.getAuthors());
        int totalPriceWithAmount = Integer.parseInt(cart.getAmount()) * Integer.parseInt(cart.getPrice());

        holder.tvPrice.setText(String.valueOf(totalPriceWithAmount));
        Log.d("cart", "fillData: " + cart.getAmount());
        holder.tvAmount.setText(cart.getAmount());
    }

    private void CallAddAndRemoveCart(ViewHolder holder, int position) {
        Cart cart = list_cart.get(position);

        //button plus
        holder.img_Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberOfAmount = holder.tvAmount.getText().toString();
                int value = Integer.valueOf(numberOfAmount);
                holder.tvAmount.setText(String.valueOf(++value));
                UpdateDataintoDB(cart, value);
            }
        });

        //button minus
        holder.img_Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberOfAmount = holder.tvAmount.getText().toString();
                int value = Integer.valueOf(numberOfAmount);
                holder.tvAmount.setText(String.valueOf(--value));
                UpdateDataintoDB(cart, value);

                if (value == 0) {
                    ApiService apiService = RetroClient.getApiService();
                    Log.i("cart", "onClick: " + cart.getId());
                    Call<Cart> call = apiService.deleteCart(cart.getId());
                    call.enqueue(new Callback<Cart>() {
                        @Override
                        public void onResponse(Call<Cart> call, Response<Cart> response) {

                        }

                        @Override
                        public void onFailure(Call<Cart> call, Throwable t) {

                        }
                    });

                    list_cart.remove(position);
                }

            }
        });


    }

    public void UpdateDataintoDB(Cart cart, int value) {
        ApiService apiService = RetroClient.getApiService();
        Call<Cart> call = apiService.getUpdateCart(cart.getId(), String.valueOf(value));
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful()) {
                    Cart cart = response.body();

                }

            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return list_cart.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName, tvAuthors, tvPrice;

        private final ImageButton img_Plus, img_Minus;
        private final TextView tvAmount;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            tvName = view.findViewById(R.id.tvName);
            tvAuthors = view.findViewById(R.id.tvAuthors);
            tvPrice = view.findViewById(R.id.tvPrice);


            img_Plus = view.findViewById(R.id.btnPlus);
            img_Minus = view.findViewById(R.id.btnMinus);
            tvAmount = view.findViewById(R.id.tvAmount);

        }


    }

    //set and get
    public List<Cart> getList_cart() {
        return list_cart;
    }
}
