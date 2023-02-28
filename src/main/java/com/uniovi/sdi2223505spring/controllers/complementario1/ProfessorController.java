package com.uniovi.sdi2223505spring.controllers.complementario1;

import com.uniovi.sdi2223505spring.entities.complementario1.Professor;
import com.uniovi.sdi2223505spring.services.complementario1.ProfessorsService;
import com.uniovi.sdi2223505spring.validators.ProfessorEditFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class ProfessorController {

    @Autowired // Inyectar el servicio
    private ProfessorsService professorsService;

    @Autowired
    private ProfessorEditFormValidator professorEditFormValidator;

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
        return "redirect:/professor/details" + dni;
    }

    @RequestMapping(value = "/professor/edit", method = RequestMethod.GET)
    public String modify(Model model) {
        model.addAttribute("professor", new Professor());
        return "/professor/edit";
    }

    @RequestMapping(value = "/professor/edit", method = RequestMethod.POST)
    public String modify(@Validated Professor professor, BindingResult result) {
        professorEditFormValidator.validate(professor, result);
        if (result.hasErrors())
            return "/professor/edit";

        Professor originalProfessor = professorsService.getProfessor(professor.getDni());
        originalProfessor.setDni(professor.getDni());
        originalProfessor.setName(professor.getName());
        originalProfessor.setSurname(professor.getSurname());
        professorsService.addProfessor(originalProfessor);
        return "redirect:home";
    }

}
