package com.example.assignment_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.assignment_book.HandleJson.ApiService;
import com.example.assignment_book.HandleJson.RetroClient;
import com.example.assignment_book.Response.ResetPassResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassActivity extends AppCompatActivity {
    EditText edtPass,edtRepass;
    Button btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        initView();
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String pass = edtPass.getText().toString();
                    String Repass = edtRepass.getText().toString();
                    if(pass!=null&& Repass!= null){
                        CallResetPassword();
                    }else{

                    }
            }
        });

    }
    public void CallResetPassword(){
        String email = getIntent().getStringExtra("email");
        String password = edtPass.getText().toString();
        ApiService apiLogin = RetroClient.getApiService();
        Call<ResetPassResponse> call = apiLogin.getResetPassword(email,password);
        call.enqueue(new Callback<ResetPassResponse>() {
            @Override
            public void onResponse(Call<ResetPassResponse> call, Response<ResetPassResponse> response) {
                if(response.isSuccessful()){
                    Intent i = new Intent(getApplication(), SigninActivity.class); // Your list's Intent
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<ResetPassResponse> call, Throwable t) {

            }
        });
    }

    public void initView(){
        edtPass = findViewById(R.id.edtOldPass);
        edtRepass = findViewById(R.id.edtNewPass);
        btnReset = findViewById(R.id.btnChangePass);
    }
}