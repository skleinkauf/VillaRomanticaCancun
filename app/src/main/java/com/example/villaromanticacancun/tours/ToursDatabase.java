package com.example.villaromanticacancun.tours;

import android.os.Parcel;
import android.os.Parcelable;

public class ToursDatabase  implements Parcelable {
    private String tourName;
    private String tourLocation;
    private String tourAbout;
    private String tourPhoto;

    public ToursDatabase() {
    }

    public ToursDatabase(String tourName, String tourLocation, String tourAbout, String tourPhoto) {
        this.tourName = tourName;
        this.tourLocation = tourLocation;
        this.tourAbout = tourAbout;
        this.tourPhoto = tourPhoto;
    }

    protected ToursDatabase(Parcel in) {
        tourName = in.readString();
        tourLocation = in.readString();
        tourAbout = in.readString();
        tourPhoto = in.readString();
    }

    public static final Creator<ToursDatabase> CREATOR = new Creator<ToursDatabase>() {
        @Override
        public ToursDatabase createFromParcel(Parcel in) {
            return new ToursDatabase(in);
        }

        @Override
        public ToursDatabase[] newArray(int size) {
            return new ToursDatabase[size];
        }
    };

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourLocation() {
        return tourLocation;
    }

    public void setTourLocation(String tourLocation) {
        this.tourLocation = tourLocation;
    }

    public String getTourAbout() {
        return tourAbout;
    }

    public void setTourAbout(String tourAbout) {
        this.tourAbout = tourAbout;
    }

    public String getTourPhoto() {
        return tourPhoto;
    }

    public void setTourPhoto(String tourPhoto) {
        this.tourPhoto = tourPhoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tourName);
        parcel.writeString(tourLocation);
        parcel.writeString(tourAbout);
        parcel.writeString(tourPhoto);
    }
}
