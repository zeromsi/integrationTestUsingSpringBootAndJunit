package com.msi.interationtest.integrationTestUsingSpringBootAndJunit.repository;

import java.util.List;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.msi.interationtest.integrationTestUsingSpringBootAndJunit.domain.Note;

@Repository
public interface NoteRepository extends CrudRepository<Note,Integer>{


    List<Note> findAllNoteByTitleIgnoreCaseOrderByIdAsc(String title);
    List<Note> findAllByContentLikeIgnoreCaseOrderByIdDesc(String content);
}