package com.msi.interationtest.integrationTestUsingSpringBootAndJunit.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msi.interationtest.integrationTestUsingSpringBootAndJunit.domain.Note;
import com.msi.interationtest.integrationTestUsingSpringBootAndJunit.service.NoteService;




@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private NoteService noteService;

    @Autowired
    public NoteController( NoteService noteService ) {
        this.noteService = noteService;
    }


    @GetMapping(value = "")
    public ResponseEntity<List<Note>> findAllNotes(){
        List<Note> notes = noteService.findAllNotes();
        Boolean isNoteEmpty = notes.toString().equals( "[]");
        ResponseEntity noteEmpty = new ResponseEntity( HttpStatus.NO_CONTENT);
        ResponseEntity<List<Note>> noteNotEmpty = new ResponseEntity<>( notes, HttpStatus.FOUND );
        return isNoteEmpty ? noteEmpty : noteNotEmpty;
    }


    @GetMapping(value ="/{id}")
    public ResponseEntity<Note> findOneNote(@PathVariable int id){
        Boolean isNoteDoesntExist= noteService.findOneNote(id) == null;
        ResponseEntity noteNotFound = new ResponseEntity(HttpStatus.NOT_FOUND );
        ResponseEntity<Note> noteFound = new ResponseEntity<>(noteService.findOneNote(id), HttpStatus.FOUND );
        return isNoteDoesntExist ? noteNotFound : (ResponseEntity) noteFound;
    }

  
    @GetMapping(value = "title/{title}")
    public ResponseEntity<List<Note>> findAllNotesByTitle(@PathVariable String title){
        List<Note> responseBody = noteService.findAllNotesByTitle(title);
        Boolean isNoteDoesntExist= responseBody.toString().equals( "[]" );
        ResponseEntity noteNotFound =new ResponseEntity(HttpStatus.NOT_FOUND );
        ResponseEntity<List<Note>> noteFound = new ResponseEntity<>(responseBody, HttpStatus.FOUND );
        return isNoteDoesntExist ? noteNotFound : noteFound;
    }


    @GetMapping("content/{content}")
    public ResponseEntity<List<Note>> findAllNotesByContent(@PathVariable String content){
        List<Note> responseBody = noteService.findAllContentLike( content );
        Boolean isNoteEmpty = responseBody.toString().equals( "[]" );
        ResponseEntity noteEmpty = new ResponseEntity( HttpStatus.NO_CONTENT );
        ResponseEntity<List<Note>> noteNotEmpty= new ResponseEntity<>( responseBody, HttpStatus.FOUND );
        return isNoteEmpty ? noteEmpty : noteNotEmpty;
    }



    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody Note note){
        Note result = noteService.addNote(note);
        Boolean isNoteFound = findOneNote(result.getId()) != null;
        ResponseEntity<Note> noteFound = new ResponseEntity<>(result,HttpStatus.CREATED);
        ResponseEntity noteNotFound = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return isNoteFound ? noteFound : noteNotFound;
    }



    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable int id, @RequestBody Note note){
        Boolean isNoteDoesntExist= noteService.findOneNote( id ) == null;
        if ( isNoteDoesntExist ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST );
        }else{
            noteService.updateNote(note);
            return new ResponseEntity<>(noteService.findOneNote( id ), HttpStatus.OK );
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteNote(@PathVariable int id){
        Boolean isNoteDoesntExist= noteService.findOneNote( id ) == null;
        if ( isNoteDoesntExist ){
            return new ResponseEntity( HttpStatus.BAD_REQUEST );
        }else{
            noteService.deleteNote( id );
            return new ResponseEntity( HttpStatus.OK );
        }
    }


}