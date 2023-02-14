package com.uniovi.sdi2223505spring.controllers.complementario1;

import com.uniovi.sdi2223505spring.entities.complementario1.Professor;
import com.uniovi.sdi2223505spring.services.complementario1.ProfessorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfessorController {

    @Autowired // Inyectar el servicio
    private ProfessorsService professorsService;

    @RequestMapping("/professor/list")
    public String getList() {
        // return "Getting Professors";
        return professorsService.getProfessors().toString();
    }

    @RequestMapping("/professor/add")
    public String getProfessor() {
        return "Getting Professor";
    }

    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@ModelAttribute Professor professor) {
        professorsService.addProfessor(professor);
        return "Added Professor with dni: " + professor.getDni()
                + ", name: " + professor.getName()
                + ", surname: " + professor.getSurname()
                + ", category: " + professor.getCategory();
    }

    @RequestMapping("/professor/details/{dni}")
    public String getDetail(@PathVariable String dni) {
        // return "Getting Details =>" + dni;
        return professorsService.getProfessor(dni).toString();
    }

    @RequestMapping("/professor/delete/{dni}")
    public String deleteProfessor(@PathVariable String dni) {
        professorsService.deleteProfessor(dni);
        return "Deleting Professor =>" + dni;
    }

    @RequestMapping(value = "/professor/edit/{dni}")
    public String getEdit(@PathVariable String dni) {
        // return "Getting Professor =>" + dni;
        return professorsService.getProfessor(dni).toString();
    }

    @RequestMapping(value="/professor/edit/{dni}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Professor professor, @PathVariable String dni) {
        professor.setDni(dni);
        professorsService.addProfessor(professor);
        return "Editting Professor =>" + dni;
    }

}
