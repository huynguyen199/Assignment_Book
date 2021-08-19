package com.example.assignment_book.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MailResponse {


    private String email;

    private String code;

    private String message;


    private Boolean status;

    public MailResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MailResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
