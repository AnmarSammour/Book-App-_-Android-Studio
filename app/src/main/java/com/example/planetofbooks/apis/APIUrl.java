package com.example.planetofbooks.apis;

public interface APIUrl {

    String BASE_URL ="http://10.0.2.2/books/API.php/";
    String BASE_URL_NOVEL ="http://10.0.2.2/books/APInovel.php/";
    String BASE_URL_SELF_DEVELOPMENT ="http://10.0.2.2/books/APIself_development.php/";
    String BASE_URL_TECHNOLOGY ="http://10.0.2.2/books/APItechnology.php/";

    String SIGN_IN = "http://10.0.2.2/books/checkUser.php/";
    String SIGN_UP = "http://10.0.2.2/books/InsertUser.php/";
    String UPDATE_INFO = "http://10.0.2.2/books/updateUser.php/";
    String DELETE_USER = "http://10.0.2.2/books/deleteUser.php/";
    String FAV_INSERT = "http://10.0.2.2/books/InsertFav.php/";
    String FAV_LIST = "http://10.0.2.2/books/FavList.php/";
    String DELETE_FAV = "http://10.0.2.2/books/deleteFav.php/";


}
