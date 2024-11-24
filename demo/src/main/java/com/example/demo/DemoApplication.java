package com.example.demo;

import com.example.demo.models.Note;
import com.example.demo.services.NoteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner run(NoteService noteService) {
        return args -> {

            Note note1 = new Note("First Note", "This is the first note");
            Note note2 = new Note("Second Note", "This is the second note");

            note1 = noteService.add(note1);
            note2 = noteService.add(note2);


            System.out.println("Notes after adding:");
            noteService.listAll().forEach(System.out::println);


            note1.setTitle("Updated First Note");
            note1.setContent("Updated content of the first note");
            noteService.update(note1);

            System.out.println("\nNotes after update:");
            noteService.listAll().forEach(System.out::println);


            noteService.deleteById(note2.getId());
            System.out.println("\nNotes after deleting the second note:");
            noteService.listAll().forEach(System.out::println);


            Note foundNote = noteService.getById(note1.getId());
            System.out.println("\nFound Note by ID: " + foundNote);
        };
    }
}
