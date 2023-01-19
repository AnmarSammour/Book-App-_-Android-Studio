package com.example.planetofbooks.model;

import com.google.gson.annotations.SerializedName;

public class Book {
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
    @SerializedName("Categories")
    String Categories;

    public Book(String namebook, String nameauthor, String year, String description, String imgLink, String pdfLink, String categories) {
        this.namebook = namebook;
        this.nameauthor = nameauthor;
        this.year = year;
        this.description = description;
        this.imgLink = imgLink;
        PdfLink = pdfLink;
        Categories = categories;
    }

    public String getNamebook() {
        return namebook;
    }

    public void setNamebook(String namebook) {
        this.namebook = namebook;
    }

    public String getNameauthor() {
        return nameauthor;
    }

    public void setNameauthor(String nameauthor) {
        this.nameauthor = nameauthor;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getPdfLink() {
        return PdfLink;
    }

    public void setPdfLink(String pdfLink) {
        PdfLink = pdfLink;
    }

    public String getCategories() {
        return Categories;
    }

    public void setCategories(String categories) {
        Categories = categories;
    }
}
