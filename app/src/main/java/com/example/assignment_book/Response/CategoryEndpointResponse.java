package com.example.assignment_book.Response;

import com.example.assignment_book.Model.Categories;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoryEndpointResponse {
    private List<Categories> Categories;

    // public constructor is necessary for collections
    public CategoryEndpointResponse() {
        Categories = new ArrayList<Categories>();
    }

    public static CategoryEndpointResponse parseJSON(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Type collectionType = new TypeToken<List<Categories>>(){}.getType();
        Gson gson = gsonBuilder.create();
        CategoryEndpointResponse employeeEndpointResponse = gson.fromJson(response, CategoryEndpointResponse.class);
        return employeeEndpointResponse;
    }

    public List<Categories> getCategories() {
        return Categories;
    }

    public void setCategories(List<Categories> categories) {
        this.Categories = categories;
    }
}
