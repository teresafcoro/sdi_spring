package com.uniovi.sdi2223505spring.services;

import com.uniovi.sdi2223505spring.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.uniovi.sdi2223505spring.entities.Mark;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    /* private List<Mark> marksList = new LinkedList<>();

    @PostConstruct
    public void init() {
        marksList.add(new Mark(1L, "Ejercicio 1", 10.0));
        marksList.add(new Mark(2L, "Ejercicio 2", 9.0));
    } */

    public List<Mark> getMarks() {
        List<Mark> marks = new ArrayList<Mark>();
        marksRepository.findAll().forEach(marks::add);
        return marks;
        // return marksList;
    }

    public Mark getMark(Long id) {
        return marksRepository.findById(id).get();
        // return marksList.stream().filter(mark -> mark.getId().equals(id)).findFirst().get();
    }

    public void addMark(Mark mark) {
        // Si en Id es null le asignamos el ultimo + 1 de la lista
        marksRepository.save(mark);
        /* if (mark.getId() == null) {
            mark.setId(marksList.get(marksList.size() - 1).getId() + 1);
        }
        marksList.add(mark); */
    }

    public void deleteMark(Long id) {
        marksRepository.deleteById(id);
        // marksList.removeIf(mark -> mark.getId().equals(id));
    }

}
