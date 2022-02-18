package com.savibpractice.apiPractice.repository;

import com.savibpractice.apiPractice.domain.Category;
import com.savibpractice.apiPractice.exceptions.BadRequestException;
import com.savibpractice.apiPractice.exceptions.ResourcesNotFoundException;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll(Integer userId) throws ResourcesNotFoundException;
    Category findById(Integer userId,Integer categoryId) throws ResourcesNotFoundException;
    Integer create(Integer userId, String title, String description) throws BadRequestException;
    void update(Integer userId, Integer categoryId ,Category category) throws BadRequestException;
    void removeBy(Integer userId, Integer categoryId);
}
