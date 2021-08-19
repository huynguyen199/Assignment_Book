package com.example.assignment_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_book.HandleJson.ApiService;
import com.example.assignment_book.HandleJson.InternetConnection;
import com.example.assignment_book.HandleJson.RetroClient;
import com.example.assignment_book.Response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtEmail, edtPassword;
    Button btnSignin;
    TextView tvForgot,tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initView();
        edtEmail.setText("huyabata@gmail.com");
        edtPassword.setText("123123");
        btnSignin.setOnClickListener(this);
        tvForgot.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
    }

    private void initView() {
        edtEmail = findViewById(R.id.edtOldPass);
        edtPassword = findViewById(R.id.edtNewPass);
        btnSignin = findViewById(R.id.btnChangePass);
        tvForgot = findViewById(R.id.tvForgotPassword);
        tvSignup = findViewById(R.id.tvSignup);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangePass:
                handleLoginResponse();
                break;
            case R.id.tvForgotPassword:
                Intent i = new Intent(getApplication(), ForgotPasswordActivity.class); // Your list's Intent
                startActivity(i);
                break;
            case R.id.tvSignup:
                i = new Intent(getApplication(),SignupActivity.class); // Your list's Intent
                startActivity(i);
                break;
        }
    }
    public void handleLoginResponse(){
        if (InternetConnection.checkConnection(getApplicationContext())) {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();


            ApiService apiLogin = RetroClient.getApiService();
            Call<LoginResponse> call = apiLogin.getLogin(email, password);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        if(loginResponse.getMessage().equals("success")){

                            Intent i = new Intent(getApplication(), MainActivity.class); // Your list's Intent
                            Log.i("Signin", "onResponse: "+email);
                            i.putExtra("email",email);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);

                            Toast.makeText(SigninActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SigninActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("test", "onFailure: " + t.getMessage());

                }
            });


        }
    }
}