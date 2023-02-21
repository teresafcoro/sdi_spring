package com.uniovi.sdi2223505spring.validators;

import com.uniovi.sdi2223505spring.entities.Mark;
import com.uniovi.sdi2223505spring.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MarkEditFormValidator implements Validator {

    @Autowired
    private UsersService usersService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Mark.class.equals(clazz.isAnonymousClass());
    }

    @Override
    public void validate(Object target, Errors errors) {
        Mark mark = (Mark) target;
        // El rango de notas tiene que estar entre 0 y 10.
        if (mark.getScore() < 0 || mark.getScore() > 10) {
            errors.rejectValue("score", "Error.modify.score.range");
        }
        // La descripción debe tener una longitud mínima de 20 caracteres
        if (mark.getDescription().length() < 20) {
            errors.rejectValue("description", "Error.modify.description.length");
        }
    }

}
