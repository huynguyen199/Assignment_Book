package com.example.assignment_book.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment_book.ChangePassActivity;
import com.example.assignment_book.HandleJson.ApiService;
import com.example.assignment_book.HandleJson.InternetConnection;
import com.example.assignment_book.HandleJson.RetroClient;
import com.example.assignment_book.Response.ProfileResponse;
import com.example.assignment_book.R;
import com.example.assignment_book.SigninActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    Button btnChangePass, btnLogout;
    TextView tvEmail, tvName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        btnChangePass.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        CallProfile();
        Log.i("Profile", "onViewCreated: " + getActivity().getIntent().getStringExtra("email"));
    }

    public void CallProfile() {
        if (InternetConnection.checkConnection(getContext().getApplicationContext())) {
            String email = getActivity().getIntent().getStringExtra("email");
            ApiService apiMail = RetroClient.getApiService();

            Call<ProfileResponse> call = apiMail.getProfile(email);
            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    if(response.isSuccessful()){
                        Log.i("hello", "onResponse: "+response.body());
                        tvEmail.setText(response.body().getEmail());
                        tvName.setText(response.body().getFullname());
                    }
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {

                }
            });
        }
    }


    private void initView() {
        btnChangePass = getView().findViewById(R.id.btnChange);
        btnLogout = getView().findViewById(R.id.btnLogout);
        tvEmail = getView().findViewById(R.id.TvEmail);
        tvName = getView().findViewById(R.id.tvName);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChange:
                String email = getActivity().getIntent().getStringExtra("email");
                Intent i = new Intent(getActivity(), ChangePassActivity.class);
                i.putExtra("email", email);
                getActivity().startActivity(i);
                break;
            case R.id.btnLogout:
                Intent i2 = new Intent(getActivity(), SigninActivity.class); // Your list's Intent
                i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i2);
                break;

        }
    }
}