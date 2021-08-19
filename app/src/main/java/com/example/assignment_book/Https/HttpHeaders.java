package com.example.assignment_book.Https;

import retrofit2.http.POST;

public class HttpHeaders {
    //URL
    public static final String URL_CATEGORY = "http://10.0.2.2:80/Assignment/getAllCategories.php";



    public static final String CONTENT_TYPE = "Authorization";
    public static final String AUTHORIZATION = "Content-Type";


    //CRUD
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

}
