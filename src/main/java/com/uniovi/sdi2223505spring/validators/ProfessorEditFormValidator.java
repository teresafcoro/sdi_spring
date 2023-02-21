package com.uniovi.sdi2223505spring.validators;

import com.uniovi.sdi2223505spring.entities.complementario1.Professor;
import com.uniovi.sdi2223505spring.services.complementario1.ProfessorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProfessorEditFormValidator implements Validator {

    @Autowired
    private ProfessorsService professorService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Professor.class.equals(clazz.isAnonymousClass());
    }

    @Override
    public void validate(Object target, Errors errors) {
        Professor professor = (Professor) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");
        // El DNI debe tener una longitud de 9 caracteres.
        if (professor.getDni().length() != 9) {
            errors.rejectValue("dni", "Error.modify.dni.length");
        }
        // En el DNI el último carácter tiene que ser una letra.
        if (Character.isAlphabetic(professor.getDni().charAt(8))) {
            errors.rejectValue("dni", "Error.modify.dni.value");
        }
        // El DNI tiene que ser único.
        if (professorService.getProfessor(professor.getDni()) != null) {
            errors.rejectValue("dni", "Error.modify.dni.duplicate");
        }
    }

}
