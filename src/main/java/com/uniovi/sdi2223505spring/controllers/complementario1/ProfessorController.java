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
        model.addAttribute("professorsList", professorsService.getProfessors());
        return "professor/list";
    }

    @RequestMapping("/professor/add")
    public String getProfessor() {
        return "professor/add";
    }

    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@ModelAttribute Professor professor) {
        professorsService.addProfessor(professor);
        return "redirect:/professor/list";
    }

    @RequestMapping("/professor/details/{dni}")
    public String getDetail(Model model, @PathVariable String dni) {
        model.addAttribute("professor", professorsService.getProfessor(dni));
        return "professor/details";
    }

    @RequestMapping("/professor/delete/{dni}")
    public String deleteProfessor(@PathVariable String dni) {
        professorsService.deleteProfessor(dni);
        return "redirect:/professor/list";
    }

    @RequestMapping(value = "/professor/edit/{dni}")
    public String getEdit(Model model, @PathVariable String dni) {
        model.addAttribute("professor", professorsService.getProfessor(dni));
        return "professor/edit";
    }

    @RequestMapping(value="/professor/edit/{dni}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Professor professor, @PathVariable String dni) {
        professor.setDni(dni);
        professorsService.addProfessor(professor);
        return "redirect:/professor/details/" + dni;
    }

}
