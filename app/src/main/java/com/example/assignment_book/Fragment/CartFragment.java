package com.example.assignment_book.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment_book.Adapter.CartAdapter;
import com.example.assignment_book.HandleJson.ApiService;
import com.example.assignment_book.HandleJson.RetroClient;
import com.example.assignment_book.Model.Cart;
import com.example.assignment_book.R;
import com.example.assignment_book.Response.CartEndpointResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView recyler_cart;
    private TextView tvTotal;


    private ArrayList<String> list_cart;
    List<com.example.assignment_book.Model.Cart> Cart;


    CartAdapter cartAdapter;


    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewFindId(view);
        insertArrays();
        getAllCart();
    }


    private void CalculateTotalAmount(List<Cart> cart) {
        int total = 0;

        Log.i("cart", "CalculateTotalAmount: "+cart.size());

        for (Cart c : cart) {
            int moneyAndAmount = Integer.parseInt(c.getPrice())*Integer.parseInt(c.getAmount());
            total += moneyAndAmount;
        }
        tvTotal.setText("$"+total);
        Log.i("cart", "total "+total);

    }


    private void insertArrays() {
        list_cart = new ArrayList<>();
        list_cart.add("sddsa");
        list_cart.add("csa");

    }


    private void getAllCart() {
        Cart = new ArrayList<>();
        ApiService apiService = RetroClient.getApiService();
        Call<CartEndpointResponse> call = apiService.listCart();
        call.enqueue(new Callback<CartEndpointResponse>() {
            @Override
            public void onResponse(Call<CartEndpointResponse> call, Response<CartEndpointResponse> response) {
                CartEndpointResponse cartEndpointResponse = response.body();
                Cart = cartEndpointResponse.getCart();
                Log.i("cart", "onResponse: " + Cart);
                customRecyclerView(Cart);
                CalculateTotalAmount(Cart);
            }

            @Override
            public void onFailure(Call<CartEndpointResponse> call, Throwable t) {

            }
        });
    }

    private void initViewFindId(View view) {
        recyler_cart = view.findViewById(R.id.recyler_cart);
        tvTotal = view.findViewById(R.id.tvTotal);
    }

    //handle recylerview ,arraylist categories,book
    private void customRecyclerView(List<com.example.assignment_book.Model.Cart> cart) {

        //categories
        cartAdapter = new CartAdapter(cart, getContext());
        recyler_cart.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyler_cart.setLayoutManager(layoutManager);
        recyler_cart.setAdapter(cartAdapter);


    }

}