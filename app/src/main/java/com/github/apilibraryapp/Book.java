package com.github.apilibraryapp;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Book implements Serializable{
    @SerializedName("title")
    private String title;
    @SerializedName("author_name")
    private List<String> authors;
    @SerializedName("cover_i")
    private String cover;
    @SerializedName("number_of_pages_median")
    private String numberOfPages;
    @SerializedName("subtitle")
    private String subtitle;
    @SerializedName("weight")
    private String weight;
    @SerializedName("language")
    private List<String> languages;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getSubtitle() {return subtitle;}

    public void setSubtitle(String subtitle) {this.subtitle = subtitle;}

    public String getWeight() {return weight;}

    public void setWeight(String weight) {this.weight = weight;}

    public List<String> getLanguages() {return languages;}

    public void setLanguages(List<String> languages) {this.languages = languages;}
}
