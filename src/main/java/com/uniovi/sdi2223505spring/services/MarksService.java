package com.uniovi.sdi2223505spring.services;

import com.uniovi.sdi2223505spring.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.uniovi.sdi2223505spring.entities.Mark;

import javax.servlet.http.HttpSession;

@Service
public class MarksService {

    /* Inyección de dependencias basada en atributos */
    @Autowired
    private MarksRepository marksRepository;

    // Sesión 04 - Inyección de la sesión, objeto HttpSession
    /* Inyección de dependencias basada en constructor */
    private final HttpSession httpSession;

    @Autowired public MarksService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public List<Mark> getMarks() {
        List<Mark> marks = new ArrayList<Mark>();
        marksRepository.findAll().forEach(marks::add);
        return marks;
    }

    public Mark getMark(Long id) {
        // Obtengo el objeto con clave "consultedList"
        Set<Mark> consultedList = (Set<Mark>) httpSession.getAttribute("consultedList");
        // Como es la primera vez que la obtengo, esta puede ser nula
        if (consultedList == null) {
            consultedList = new HashSet<Mark>();
        }
        // Obtengo la nota del repositorio con id especificado como param.
        // La añado a la lista de notas consultadas y la guardo en sesión
        Mark obtainedMark = marksRepository.findById(id).get();
        consultedList.add(obtainedMark);
        httpSession.setAttribute("consultedList", consultedList);
        return obtainedMark;
    }

    public void addMark(Mark mark) {
        marksRepository.save(mark);
    }

    public void deleteMark(Long id) {
        marksRepository.deleteById(id);
    }

}
