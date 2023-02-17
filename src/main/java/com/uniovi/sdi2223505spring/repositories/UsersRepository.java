package com.uniovi.sdi2223505spring.repositories;

import com.uniovi.sdi2223505spring.entities.User;
import org.springframework.data.repository.CrudRepository;

// Repositorio para la entidad User
public interface UsersRepository extends CrudRepository<User, Long> {


}
