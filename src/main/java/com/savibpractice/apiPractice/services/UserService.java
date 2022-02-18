package com.savibpractice.apiPractice.services;

import com.savibpractice.apiPractice.domain.User;
import com.savibpractice.apiPractice.exceptions.AuthException;

public interface UserService {
    User validateUser(String email, String password) throws AuthException;
    User registerUser(String firstName, String lastName, String email, String password) throws AuthException;
}
