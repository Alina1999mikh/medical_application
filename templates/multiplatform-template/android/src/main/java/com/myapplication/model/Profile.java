package com.myapplication.model;

import android.graphics.Bitmap;
import androidx.compose.runtime.Composable;
import com.myapplication.exception.DataException;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@Composable
public class Profile {
    public Bitmap photo;
    public FullName name;
    public Calendar dateOfBirth;
    public char gender;

    public Profile(FullName name, Calendar dateOfBirth, char gender) throws DataException {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.photo = null;
        //TODO get photo;
        setGender(gender);
    }

    public Profile() {
        emptyProfile();
    }

    private void emptyProfile() {
        photo = null;
        name = null;
        dateOfBirth = null;
    }

    public void clearProfile() {
        emptyProfile();
    }

    public Boolean isEmptyProfile() {
        return photo == null && name == null && dateOfBirth == null;
    }

    public Profile(FullName name, Calendar dateOfBirth, char gender, Bitmap photo) throws DataException {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.photo = photo;
        if (isGender(gender)) this.gender = gender;
        else throw new DataException("Incorrect gender " + gender + ". Gender must be 'M' for male or 'F' for female");
    }

    public void setGender(char gender) throws DataException {
        if (isGender(gender)) this.gender = gender;
        else throw new DataException("Incorrect gender " + gender + ". Gender must be 'M' for male or 'F' for female");
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    private boolean isGender(char gender) {
        return gender == 'M' || gender == 'F';
    }

    public String getGenderToString() {
        if (gender == 'M') return "Мужской";
        else return "Женский";
    }
}