package com.example.demo.service;

import com.example.demo.model.Note;
import com.example.demo.model.MyUser;
import com.example.demo.repository.NoteRepository;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public List<Note> getNotesByUser(MyUser myUser) {
        return noteRepository.findByUser(myUser);
    }

    public void deleteById(Long id, MyUser myUser) {
        Note note = noteRepository.findById(id).orElse(null);
        if (note != null && note.getUser().equals(myUser)) {
            noteRepository.delete(note);
        }
    }

    public void add(String title, String content, MyUser myUser) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(myUser);
        noteRepository.save(note);
    }

    public void update(Long id, String title, String content, MyUser myUser) {
        Note note = noteRepository.findById(id).orElse(null);
        if (note != null && note.getUser().equals(myUser)) {
            note.setTitle(title);
            note.setContent(content);
            noteRepository.save(note);
        }
    }

    public Note getById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }
}
