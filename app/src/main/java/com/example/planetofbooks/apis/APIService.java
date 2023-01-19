package com.example.planetofbooks.apis;

import com.example.planetofbooks.model.Book;
import com.example.planetofbooks.model.Favorite;
import com.example.planetofbooks.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {


    @GET ("API.php")
    Call<List<Book>> getBook();

    @GET ("APInovel.php")
    Call<List<Book>> getBookNovel();

    @GET ("APIself_development.php")
    Call<List<Book>> getBooKSelfDevelopment();

    @GET ("APItechnology.php")
    Call<List<Book>> getBookTechnology();

    @GET ("FavList.php")
    Call<List<Favorite>> getFavList();

    /* the SignIn call */
    @FormUrlEncoded
    @POST("checkUser.php")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    /*The SignUp Call */
    @FormUrlEncoded
    @POST("InsertUser.php")
    Call<Result>  insertUser(
            @Field("name") String name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("phone") String phone

    );

    @FormUrlEncoded
    @POST("updateUser.php")
    Call<Result> updateUser(
            @Field("id") int id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("deleteUser.php")
    Call<Result> deleteUser(
            @Field("id") int id

    );

    @FormUrlEncoded
    @POST("InsertFav.php")
    Call<Result>  insertFav(

            @Field("namebook") String namebook,
            @Field("nameauthor") String nameauthor,
            @Field("year") String year,
            @Field("description") String description,
            @Field("imgLink") String imgLink,
            @Field("PdfLink") String PdfLink,
            @Field("user_email") String user_email,
            @Field("Categories") String Categories

    );

    @FormUrlEncoded
    @POST("deleteFav.php")
    Call<Result> deleteFav(
            @Field("namebook") String namebook
    );

}
