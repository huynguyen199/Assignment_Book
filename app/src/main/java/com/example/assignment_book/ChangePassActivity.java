package com.example.assignment_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment_book.HandleJson.ApiService;
import com.example.assignment_book.HandleJson.InternetConnection;
import com.example.assignment_book.HandleJson.RetroClient;
import com.example.assignment_book.Response.ChangePassResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassActivity extends AppCompatActivity {
    EditText edtOldpass,edtNewpass,edtCofirmPass;
    Button btnChangePass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        initView();

        Intent i = getIntent();
        String email = i.getStringExtra("email");
        Log.i("email", "onCreate: "+email);

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                String email = i.getStringExtra("email");
                String oldpass = edtOldpass.getText().toString();
                String newpass = edtNewpass.getText().toString();
                String cofirmpass =edtCofirmPass.getText().toString();

                if(oldpass.isEmpty()){
                    Toast.makeText(ChangePassActivity.this, "Please fill the field", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newpass.equals(cofirmpass) && cofirmpass.equals(newpass)){
                    handleChangeResponse(email,oldpass,newpass);
                }else{
                    Toast.makeText(ChangePassActivity.this, "Confirm new password is incorrect", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    public void handleChangeResponse(String email,String oldpassword,String newpass) {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            ApiService apiLogin = RetroClient.getApiService();
            Call<ChangePassResponse> call = apiLogin.getChangePassword(email,oldpassword,newpass);
            call.enqueue(new Callback<ChangePassResponse>() {
                @Override
                public void onResponse(Call<ChangePassResponse> call, Response<ChangePassResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().getStatus()){
                            Toast.makeText(ChangePassActivity.this, "Change password Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ChangePassActivity.this, "Password change failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<ChangePassResponse> call, Throwable t) {

                }
            });

        }
    }


        public void initView(){
        edtOldpass = findViewById(R.id.edtOldPass);
        edtNewpass = findViewById(R.id.edtNewPass);
        edtCofirmPass = findViewById(R.id.edtCofirmPass);
        btnChangePass = findViewById(R.id.btnChangePass);
    }


}