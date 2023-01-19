package com.example.planetofbooks.model;

import com.google.gson.annotations.SerializedName;

public class Favorite {

    @SerializedName("Namebook")
    String namebook;
    @SerializedName("Nameauthor")
    String nameauthor;
    @SerializedName("Year")
    String year;
    @SerializedName("Description")
    String description;
    @SerializedName("imgLink")
    String imgLink;
    @SerializedName("PdfLink")
    String PdfLink;
    @SerializedName("user_email")
    String user_email;
    @SerializedName("Categories")
    String Categories;


    public Favorite(String namebook, String nameauthor, String year, String description, String imgLink, String pdfLink, String user_email, String categories) {
        this.namebook = namebook;
        this.nameauthor = nameauthor;
        this.year = year;
        this.description = description;
        this.imgLink = imgLink;
        PdfLink = pdfLink;
        this.user_email = user_email;
        Categories = categories;
    }


    public String getNamebook() {
        return namebook;
    }

    public String getNameauthor() {
        return nameauthor;
    }

    public String getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public String getImgLink() {
        return imgLink;
    }

    public String getPdfLink() {
        return PdfLink;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getCategories() {
        return Categories;
    }
}
