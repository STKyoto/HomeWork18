package com.example.demo.controler;

import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/main")
    public String mainPage(Model model, Principal principal) {
        if (principal != null) { // Check if user is logged in
            String username = principal.getName();
            Optional<User> existingUser = userRepository.findByUserName(username);
            if (existingUser.isPresent()) {
                model.addAttribute("user", existingUser.get());
            }
        }
        return "main";
    }

    @GetMapping("/note/view")
    public String viewNote(@RequestParam("id") Long id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "note_view";
    }

    @GetMapping("/note/list")
    public String getNote(Model model, Principal principal) {
        String username = principal.getName();
        Optional<User> existingUser = userRepository.findByUserName(username);
        if (existingUser.isPresent()) {
            List<Note> notes = noteService.getNotesByUser(existingUser.get());
            model.addAttribute("notes", notes);
        }
        return "note_list";
    }

    @PostMapping("/note/delete")
    public String deleteNote(@RequestParam("id") Long id, RedirectAttributes redirectAttributes, Principal principal) {
        String username = principal.getName();
        Optional<User> existingUser = userRepository.findByUserName(username);
        if (existingUser.isPresent()) {
            noteService.deleteById(id, existingUser.get());
        }
        redirectAttributes.addFlashAttribute("message", "Note deleted successfully");
        return "redirect:/note/list";
    }

    @GetMapping("/note/edit")
    public String editNotePage(@RequestParam(value = "id", required = false) Long id, Model model, Principal principal) {
        String username = principal.getName();
        Note note = null;
        Optional<User> existingUser = userRepository.findByUserName(username);
        if (existingUser.isPresent()) {
            if (id == null || id == 0) {
                note = new Note();
                note.setUser(existingUser.get());
            } else {
                note = noteService.getById(id);
                if (note == null || !note.getUser().equals(existingUser.get())) {
                    return "redirect:/note/list";
                }
            }
        }
        model.addAttribute("note", note);
        return "edit_note";
    }

    @PostMapping("/note/edit")
    public String saveNote(@RequestParam("id") Long id, @RequestParam("title") String title,
                           @RequestParam("content") String content, RedirectAttributes redirectAttributes, Principal principal) {
        String username = principal.getName();
        Optional<User> existingUser = userRepository.findByUserName(username);
        if (existingUser.isPresent()) {
            if (id == 0) {
                noteService.add(title, content, existingUser.get());
            } else {
                noteService.update(id, title, content, existingUser.get());
            }
        }
        redirectAttributes.addFlashAttribute("message", "Note saved successfully");
        return "redirect:/note/list";
    }
}