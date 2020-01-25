package com.example.livewallpaper.model;


public class GalleryView
{
    private String Image;
    private String formRegDate;
    private String formRegTime;
    private String uploadedBy;
    private String caption;

    public GalleryView(String image, String formRegDate, String formRegTime, String uploadedBy, String caption) {
        Image = image;
        this.formRegDate = formRegDate;
        this.formRegTime = formRegTime;
        this.uploadedBy = uploadedBy;
        this.caption = caption;
    }


    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getFormRegDate() {
        return formRegDate;
    }

    public void setFormRegDate(String formRegDate) {
        this.formRegDate = formRegDate;
    }

    public String getFormRegTime() {
        return formRegTime;
    }

    public void setFormRegTime(String formRegTime) {
        this.formRegTime = formRegTime;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


    public GalleryView()
    {

    }


}
