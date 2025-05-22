package org.angel.taskboard.service;

import org.angel.taskboard.entity.Note;
import org.angel.taskboard.entity.Task;
import org.angel.taskboard.repository.NoteRepository;
import org.angel.taskboard.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Note addNoteToTask(Long taskId, Note note) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        note.setTask(task);
        note.setDateCreated(LocalDateTime.now());

        return noteRepository.save(note);
    }

    public List<Note> getNotesByTaskId(Long taskId) {
        return noteRepository.findByTaskId(taskId);
    }

    public Note updateNote(Long noteId, Note updatedNote) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        note.setTitle(updatedNote.getTitle());
        note.setContent(updatedNote.getContent());


        return noteRepository.save(note);
    }

    public void deleteNote(Long noteId) {
        if (!noteRepository.existsById(noteId)) {
            throw new RuntimeException("Note not found");
        }
        noteRepository.deleteById(noteId);
    }
}
