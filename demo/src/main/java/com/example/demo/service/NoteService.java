package com.example.demo.service;

import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getNotesByUser(User user) {
        return noteRepository.findByUser(user);
    }

    public void deleteById(Long id, User user) {
        Note note = noteRepository.findById(id).orElse(null);
        if (note != null && note.getUser().equals(user)) {
            noteRepository.delete(note);
        }
    }

    public void add(String title, String content, User user) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(user);
        noteRepository.save(note);
    }

    public void update(Long id, String title, String content, User user) {
        Note note = noteRepository.findById(id).orElse(null);
        if (note != null && note.getUser().equals(user)) {
            note.setTitle(title);
            note.setContent(content);
            noteRepository.save(note);
        }
    }

    public Note getById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }
}
