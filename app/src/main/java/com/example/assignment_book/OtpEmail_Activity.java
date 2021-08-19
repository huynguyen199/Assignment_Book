package com.example.assignment_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_book.HandleJson.ApiService;
import com.example.assignment_book.HandleJson.InternetConnection;
import com.example.assignment_book.HandleJson.RetroClient;
import com.example.assignment_book.Response.MailResponse;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpEmail_Activity extends AppCompatActivity {
    private OtpTextView otpTextView;
    private TextView tvCountDown, tvResend;
    private Button btnVerify;
    CountDownTimer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_email);
        otpTextView = findViewById(R.id.otp_view);
        tvCountDown = findViewById(R.id.tvCountDown);
        btnVerify = findViewById(R.id.btnVerify);
        tvResend = findViewById(R.id.tvtResend);

        StartCallCountDown();

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count.cancel();
                CallReSendEmail();

                count.start();

            }
        });


        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
            }

            @Override
            public void onOTPComplete(String otp) {
                otpTextView.showSuccess();
                btnVerify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        CallVerifyCode(otpTextView.getOTP());
                        if (otpTextView.getOTP() != null && !tvCountDown.getText().toString().contains("done!")) {
                            CallVerifyCode(otpTextView.getOTP());
                            Toast.makeText(OtpEmail_Activity.this, "Verify code", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // fired when user has entered the OTP fully.
                Toast.makeText(OtpEmail_Activity.this, "The OTP is " + otpTextView.getOTP(), Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void CallVerifyCode(String otp) {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            Intent intent = getIntent();
            Log.i("test", "CallVerifyCode: " + intent.getStringExtra("email"));
            String email = intent.getStringExtra("email");
            String code = otp;

            ApiService apiMail = RetroClient.getApiService();
            Call<MailResponse> call = apiMail.getVerifiedEmail(email, code);
            call.enqueue(new Callback<MailResponse>() {
                @Override
                public void onResponse(Call<MailResponse> call, Response<MailResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            Toast.makeText(OtpEmail_Activity.this, "verify success", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(OtpEmail_Activity.this, ResetPassActivity.class);
                            myIntent.putExtra("email", email);
                            startActivity(myIntent);

                        }
                    }
                }

                @Override
                public void onFailure(Call<MailResponse> call, Throwable t) {

                }
            });

        }
    }

    public void CallReSendEmail() {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            String email = getIntent().getStringExtra("email");
            ApiService apiMail = RetroClient.getApiService();
            Call<MailResponse> call = apiMail.getGmail(email);

            call.enqueue(new Callback<MailResponse>() {
                @Override
                public void onResponse(Call<MailResponse> call, Response<MailResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(OtpEmail_Activity.this, "Resend code successfully", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MailResponse> call, Throwable t) {

                }
            });



        }

    }


    public void StartCallCountDown() {
        count = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvCountDown.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvCountDown.setText("done!");

                btnVerify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tvCountDown.getText().toString().contains("done!")) {

                            Toast.makeText(OtpEmail_Activity.this, "Please  resend to enter the new code", Toast.LENGTH_SHORT).show();

                        }

                    }
                });


            }
        }.start();
    }
}