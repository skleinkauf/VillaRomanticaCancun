package com.example.villaromanticacancun.gallery;

public class GalleryDatabase {

    private String galleryPhoto;

    public GalleryDatabase() {
    }

    public GalleryDatabase(String galleryPhoto) {
        this.galleryPhoto = galleryPhoto;
    }

    public String getGalleryPhoto() {
        return galleryPhoto;
    }

    public void setGalleryPhoto(String galleryPhoto) {
        this.galleryPhoto = galleryPhoto;
    }
}
