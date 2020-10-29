package com.example.villaromanticacancun.rooms;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomsDatabase implements Parcelable {
    private String roomPhoto;
    private String roomType;
    private String roomLocation;
    private String roomAbout;

    public RoomsDatabase() {
    }

    public RoomsDatabase(String roomPhoto, String roomType, String roomLocation, String roomAbout) {
        this.roomPhoto = roomPhoto;
        this.roomType = roomType;
        this.roomLocation = roomLocation;
        this.roomAbout = roomAbout;
    }

    protected RoomsDatabase(Parcel in) {
        roomPhoto = in.readString();
        roomType = in.readString();
        roomLocation = in.readString();
        roomAbout = in.readString();
    }

    public static final Creator<RoomsDatabase> CREATOR = new Creator<RoomsDatabase>() {
        @Override
        public RoomsDatabase createFromParcel(Parcel in) {
            return new RoomsDatabase(in);
        }

        @Override
        public RoomsDatabase[] newArray(int size) {
            return new RoomsDatabase[size];
        }
    };

    public String getRoomPhoto() {
        return roomPhoto;
    }

    public void setRoomPhoto(String roomPhoto) {
        this.roomPhoto = roomPhoto;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomLocation() {
        return roomLocation;
    }

    public void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }

    public String getRoomAbout() {
        return roomAbout;
    }

    public void setRoomAbout(String roomAbout) {
        this.roomAbout = roomAbout;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(roomPhoto);
        parcel.writeString(roomType);
        parcel.writeString(roomLocation);
        parcel.writeString(roomAbout);
    }
}
