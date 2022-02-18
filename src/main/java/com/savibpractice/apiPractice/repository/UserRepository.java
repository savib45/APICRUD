package com.savibpractice.apiPractice.repository;

import com.savibpractice.apiPractice.domain.User;
import com.savibpractice.apiPractice.exceptions.AuthException;

public interface UserRepository {
    Integer create(String firstName, String lastName, String email, String password) throws AuthException;
    User findByEmailAndPassword(String email, String password) throws AuthException;
    Integer getCountByEmail(String email);
    User findBy(Integer userId);
}
