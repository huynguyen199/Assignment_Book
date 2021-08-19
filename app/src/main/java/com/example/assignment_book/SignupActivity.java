package com.example.assignment_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assignment_book.HandleJson.ApiService;
import com.example.assignment_book.HandleJson.InternetConnection;
import com.example.assignment_book.HandleJson.RetroClient;
import com.example.assignment_book.Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtEmail, edtPassword, edtFullName;
    Button btnSignup;
    TextView tvNotify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
        btnSignup.setOnClickListener(this);


    }

    private void initView() {
        btnSignup = findViewById(R.id.btnChangePass);
        edtEmail = findViewById(R.id.edtOldPass);
        edtPassword = findViewById(R.id.edtNewPass);
        edtFullName = findViewById(R.id.edtCofirmPass);
        tvNotify = findViewById(R.id.TvFormError);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangePass:
                handleRegisterResponse();
                break;

        }
    }

    private void handleRegisterResponse() {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();
            String fullname = edtFullName.getText().toString();
            RegisterResponse registerResponse = new RegisterResponse(email, password, fullname);
            ApiService apiLogin = RetroClient.getApiService();
            Call<RegisterResponse> call = apiLogin.getRegister(registerResponse.getFromRequest(email, password, fullname));
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful()) {
                        RegisterResponse registerResponse = response.body();
                        Log.i("test", "onResponse: " + response.message());

                        if (registerResponse.getMessage().equals("success")) {
                            //start activity
                            Intent i = new Intent(getApplication(), MainActivity.class); // Your list's Intent
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        } else {
                            //set text
                            tvNotify.setText("Tài khoản đã tồn tại");
                        }

                    }

                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {

                }
            });
        }
    }

}