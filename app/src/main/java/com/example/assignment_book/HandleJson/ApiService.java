package com.example.assignment_book.HandleJson;



import com.example.assignment_book.Model.Cart;
import com.example.assignment_book.Response.CartEndpointResponse;
import com.example.assignment_book.Response.ChangePassResponse;
import com.example.assignment_book.Response.LoginResponse;
import com.example.assignment_book.Response.MailResponse;
import com.example.assignment_book.Response.ProfileResponse;
import com.example.assignment_book.Response.RegisterResponse;
import com.example.assignment_book.Response.ResetPassResponse;
import com.example.assignment_book.Response.CategoryEndpointResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    /*
    * LOGIN
    * */
    @FormUrlEncoded
    @POST("Assignment/login.php")
    Call<LoginResponse> getLogin(@Field("email") String email,@Field("password") String password);


    @POST("Assignment/register.php")
    Call<RegisterResponse> getRegister(@Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("Email/mailer.php")
    Call<MailResponse> getGmail(@Field("email") String email);


    @FormUrlEncoded
    @POST("Email/VerifyCode.php")
    Call<MailResponse> getVerifiedEmail(@Field("email") String email,@Field("code") String code);

    @FormUrlEncoded
    @POST("Assignment/ResetPassword.php")
    Call<ResetPassResponse> getResetPassword(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("Assignment/ChangePassword.php")
    Call<ChangePassResponse> getChangePassword(@Field("email") String email, @Field("oldpassword") String oldpassword, @Field("newpassword") String newpassword);


    @FormUrlEncoded
    @POST("Assignment/profileEmail.php")
    Call<ProfileResponse> getProfile(@Field("email") String email);

    /*
    * CATEGORIES
    * */
    @GET("Assignment/getAllCategories.php/") //  the string in the GET is end part of the endpoint url
    Call<CategoryEndpointResponse> listCategories();


    /*
    * Cart
    * */
    @GET("Assignment/Cart/getAllCart.php") //  the string in the GET is end part of the endpoint url
    Call<CartEndpointResponse> listCart();

    @FormUrlEncoded
    @POST("Assignment/Cart/insertCart.php") //  the string in the GET is end part of the endpoint url
    Call<Cart> getInserCart(@Field("book_id") String id, @Field("amount") String amount);

    @FormUrlEncoded
    @POST("Assignment/Cart/updateCart.php") //  the string in the GET is end part of the endpoint url
    Call<Cart> getUpdateCart(@Field("id") String id, @Field("amount") String amount);

    @DELETE("Assignment/Cart/deleteCart.php") //  the string in the GET is end part of the endpoint url
    Call<Cart> deleteCart(@Query("id") String id);

}
