package com.uniovi.sdi2223505spring.services.complementario1;

import com.uniovi.sdi2223505spring.entities.complementario1.Professor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfessorsService {

    private List<Professor> professorsList = new LinkedList<>();

    @PostConstruct
    public void init() {
        professorsList.add(new Professor("1A", "Nombre1", "Apellido1", "Profesor"));
        professorsList.add(new Professor("2B", "Nombre2", "Apellido2", "Profesor"));
    }

    public Object getProfessors() {
        return professorsList;
    }

    public Object getProfessor(String dni) {
        return professorsList.stream().filter(professor -> professor.getDni().equals(dni)).findFirst().get();
    }

    public void addProfessor(Professor professor) {
        // Si en dni es null le asignamos uno nuevo
        if (professor.getDni() == null) {
            professor.setDni("3C");
        }
        professorsList.add(professor);
    }

    public void deleteProfessor(String dni) {
        professorsList.removeIf(p -> p.getDni().equals(dni));
    }

}