package com.example.demo.service;

import com.example.demo.model.Note;
import com.example.demo.repository.NoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Transactional
    public void add(String title, String context) {
        Note note = new Note(title, context);
        noteRepository.save(note);
    }

    @Transactional
    public void deleteById(long id) {
        noteRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, String title, String context) {
        Note findedNote = noteRepository.getReferenceById(id);
        findedNote.setContent(context);
        findedNote.setTitle(title);
    }

    @Transactional
    public Note getById(long id) {
        return noteRepository.getReferenceById(id);
    }

    @Transactional
    public List<Note> listAll(){
        return noteRepository.findAll();
    }
}
