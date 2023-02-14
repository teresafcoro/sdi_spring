package com.uniovi.sdi2223505spring.controllers.complementario1;

import com.uniovi.sdi2223505spring.entities.complementario1.Professor;
import com.uniovi.sdi2223505spring.services.complementario1.ProfessorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class ProfessorController {

    @Autowired // Inyectar el servicio
    private ProfessorsService professorsService;

    @RequestMapping("/professor/list")
    public String getList(Model model) {
        // return "Getting Professors";
        // return professorsService.getProfessors().toString();
        model.addAttribute("professorList", professorsService.getProfessors());
        return "professor/list";
    }

    @RequestMapping("/professor/add")
    public String getProfessor() {
        // return "Getting Professor";
        return "professor/add";
    }

    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@ModelAttribute Professor professor) {
        professorsService.addProfessor(professor);
        /* return "Added Professor with dni: " + professor.getDni()
                + ", name: " + professor.getName()
                + ", surname: " + professor.getSurname()
                + ", category: " + professor.getCategory(); */
        return "redirect:/professor/list";
    }

    @RequestMapping("/professor/details/{dni}")
    public String getDetail(Model model, @PathVariable String dni) {
        // return "Getting Details =>" + dni;
        // return professorsService.getProfessor(dni).toString();
        model.addAttribute("professor", professorsService.getProfessor(dni));
        return "professor/details";
    }

    @RequestMapping("/professor/delete/{dni}")
    public String deleteProfessor(@PathVariable String dni) {
        professorsService.deleteProfessor(dni);
        // return "Deleting Professor =>" + dni;
        return "redirect:/professor/list";
    }

    @RequestMapping(value = "/professor/edit/{dni}")
    public String getEdit(Model model, @PathVariable String dni) {
        // return "Getting Professor =>" + dni;
        // return professorsService.getProfessor(dni).toString();
        model.addAttribute("professor", professorsService.getProfessor(dni));
        return "professor/edit";
    }

    @RequestMapping(value="/professor/edit/{dni}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Professor professor, @PathVariable String dni) {
        professor.setDni(dni);
        professorsService.addProfessor(professor);
        // return "Editting Professor =>" + dni;
        return "redirect:/professor/details/" + dni;
    }

}
