package com.savibpractice.apiPractice.services;

import com.savibpractice.apiPractice.domain.Category;
import com.savibpractice.apiPractice.exceptions.BadRequestException;
import com.savibpractice.apiPractice.exceptions.ResourcesNotFoundException;

import java.util.List;

public interface CategoryService {
    List<Category> fetchAllCategories(Integer userId);

    Category fetchCategoryById(Integer userId, Integer categoryId) throws ResourcesNotFoundException;

    Category addCategory(Integer userId, String title, String description) throws BadRequestException;

    void updateCategory(Integer userId, Integer categoryId, Category category) throws BadRequestException;

    void removeCategoryWithAllTransactions(Integer userId,Integer categoryId) throws BadRequestException;
}
