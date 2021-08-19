package com.example.assignment_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment_book.HandleJson.ApiService;
import com.example.assignment_book.HandleJson.InternetConnection;
import com.example.assignment_book.HandleJson.RetroClient;
import com.example.assignment_book.Response.MailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtEmail;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();

        btnNext.setOnClickListener(this);
    }

    public void initView() {
        btnNext = findViewById(R.id.btnNext);
        edtEmail = findViewById(R.id.edtOldPass);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                handleEmailResponse();
                break;
        }
    }

    public void handleEmailResponse() {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            String email = edtEmail.getText().toString();
            ApiService apiMail = RetroClient.getApiService();
            Call<MailResponse> call = apiMail.getGmail(email);

            call.enqueue(new Callback<MailResponse>() {
                @Override
                public void onResponse(Call<MailResponse> call, Response<MailResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(ForgotPasswordActivity.this, "send code successfully", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(ForgotPasswordActivity.this,OtpEmail_Activity.class);
                        myIntent.putExtra("email", email);
                        startActivity(myIntent);
                    }
                }

                @Override
                public void onFailure(Call<MailResponse> call, Throwable t) {

                }
            });



        }

    }

}