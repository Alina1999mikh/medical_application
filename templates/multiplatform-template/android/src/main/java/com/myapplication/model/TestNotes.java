package com.myapplication.model;

import com.myapplication.exception.DataException;
import lombok.Getter;

import java.io.Serializable;
import java.util.*;

@Getter
public class TestNotes implements Serializable {
    ArrayList<Note> notes = new ArrayList<>();

    public TestNotes() throws DataException {
        add();
    }

    private void add() throws DataException {
        notes.add(new Note(UUID.randomUUID(), "Invitro", new FullName("Alina", "Mikhaleva"),
                "Fe", new Date(), "9", "9-30.4", "mm/l", "null"));
        notes.add(new Note(UUID.randomUUID(), "Invitro", new FullName("Alina", "Mikhaleva"),
                "HbA1c", new Date(), "5.5", "0-6", "%", null));
        notes.add(new Note(UUID.randomUUID(), "KDL", new FullName("Alina", "Mikhaleva"),
                "HbA1c", new Date(), "5.9", "0-6", "%", null));
        notes.add(new Note(UUID.randomUUID(), "Invitro", new FullName("Alina", "Mikhaleva"),
                "Fe", new Date(), "28.85", "9-30.4", "мкмоль/л", null));
        notes.add(new Note(UUID.randomUUID(), "Invitro", new FullName("Alina", "Mikhaleva"),
                "Билирубин общий", new Date(), "14.3", "3.4-20.5", "мкмоль/л", null));
    }

    public ArrayList<Note> searchNote(String findTest) {
        ArrayList<Note> res = new ArrayList();
        for (Note note : notes) {
            if (isSubstring(findTest, note.getTest())) res.add(note);
        }
        return res;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    private boolean isSubstring(String substr, String str) {
        String[] strArray = str.split(" ");
        for (String s : strArray) {
            if(s.length()>=substr.length())
            if (s.substring(0,substr.length()).toLowerCase(Locale.ROOT).equals(substr.toLowerCase(Locale.ROOT)))
                return true;
        }
        return false;
    }
}