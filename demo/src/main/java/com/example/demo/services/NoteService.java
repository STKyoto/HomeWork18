package com.example.demo.services;

import com.example.demo.models.Note;
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

    public Note add(Note note) {
        long id = idGenerator.getAndIncrement();
        note.setId(id);
        notesStorage.put(id, note);
        return note;
    }

    public void deleteById(long id) {
        if (!notesStorage.containsKey(id)) {
            throw new NoSuchElementException("Note with id " + id + " not found");
        }
        notesStorage.remove(id);
    }

    public void update(Note note) {
        if (!notesStorage.containsKey(note.getId())) {
            throw new NoSuchElementException("Note with id " + note.getId() + " not found");
        }
        Note existingNote = notesStorage.get(note.getId());
        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
    }

    public Note getById(long id) {
        Note note = notesStorage.get(id);
        if (note == null) {
            throw new NoSuchElementException("Note with id " + id + " not found");
        }
        return note;
    }
}
