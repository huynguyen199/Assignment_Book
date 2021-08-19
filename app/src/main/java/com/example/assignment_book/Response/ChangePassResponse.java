package com.example.assignment_book.Response;

public class ChangePassResponse {
    private String email;
    private String oldpassword;
    private String newpassword;
    private Boolean status;
    private String message;

    public ChangePassResponse() {
    }

    public ChangePassResponse(String email, String oldpassword, String newpassword) {
        this.email = email;
        this.oldpassword = oldpassword;
        this.newpassword = newpassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
