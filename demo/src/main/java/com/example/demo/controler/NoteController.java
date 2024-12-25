package com.example.demo.controler;

import com.example.demo.model.Note;
import com.example.demo.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
@AllArgsConstructor
public class NoteController {

    @Autowired
    private NoteService noteService;


    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/note/view")
    public String viewNote(@RequestParam("id")Long id, Model model){
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "note_view";
    }
    @GetMapping("/note/list")
    public String getNote(Model model) {
        List<Note> notes = noteService.listAll();
        model.addAttribute("notes", notes);
        return "note_list";
    }

    @PostMapping("/note/delete")
    public String deleteNote(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        noteService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Note deleted successfully");
        return "redirect:/note/list";
    }

    @GetMapping("/note/edit")
    public String editNotePage(@RequestParam(value = "id", required = false) Long id, Model model) {
        Note note;
        if (id == null || id == 0) {
            note = new Note();
        } else {
            note = noteService.getById(id);
            if (note == null) {
                return "redirect:/note/list";
            }
        }
        model.addAttribute("note", note);
        return "edit_note";
    }

    @PostMapping("/note/edit")
    public String saveNote(@RequestParam("id") Long id, @RequestParam("title") String title,
                           @RequestParam("content") String content, RedirectAttributes redirectAttributes) {
        if (id == 0) {
            noteService.add(title, content);
        } else {
            noteService.update(id, title, content);
        }
        redirectAttributes.addFlashAttribute("message", "Note saved successfully");
        return "redirect:/note/list";
    }




}
