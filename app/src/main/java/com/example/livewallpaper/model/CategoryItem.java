package com.example.livewallpaper.model;

public class CategoryItem
{
    public String imageLink;
    public String name;

    public CategoryItem() {
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public CategoryItem(String imageLink, String name) {
        this.imageLink = imageLink;
        this.name = name;
    }


}
