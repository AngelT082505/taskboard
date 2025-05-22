package org.angel.taskboard.controller;

import org.angel.taskboard.entity.Note;
import org.angel.taskboard.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/task/{taskId}")
    public ResponseEntity<Note> addNote(@PathVariable Long taskId, @RequestBody Note note) {
        Note savedNote = noteService.addNoteToTask(taskId, note);
        return ResponseEntity.ok(savedNote);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Note>> getNotes(@PathVariable Long taskId) {
        List<Note> notes = noteService.getNotesByTaskId(taskId);
        return ResponseEntity.ok(notes);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<Note> updateNote(@PathVariable Long noteId, @RequestBody Note note) {
        Note updatedNote = noteService.updateNote(noteId, note);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
        noteService.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }
}
