package com.example.demo.service;

import com.example.demo.model.Note;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NoteService {
    private final Map<Long, Note> notesStorage = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);


    public List<Note> listAll() {
        return new ArrayList<>(notesStorage.values());
    }

    public Note add(String title, String context) {
        Note note = new Note();
        long id = idGenerator.getAndIncrement();
        note.setId(id);
        note.setTitle(title);
        note.setContent(context);
        notesStorage.put(id, note);
        return note;
    }

    public void deleteById(long id) {
        if (!notesStorage.containsKey(id)) {
            throw new NoSuchElementException("Note with id " + id + " not found");
        }
        notesStorage.remove(id);
    }

    public void update(Long id, String title, String context) {
        if (!notesStorage.containsKey(id)) {
            throw new NoSuchElementException("Note with id " + id + " not found");
        }
        Note existingNote = notesStorage.get(id);
        existingNote.setTitle(title);
        existingNote.setContent(context);
    }

    public Note getById(long id) {
        Note note = notesStorage.get(id);
        if (note == null) {
            throw new NoSuchElementException("Note with id " + id + " not found");
        }
        return note;
    }
}
