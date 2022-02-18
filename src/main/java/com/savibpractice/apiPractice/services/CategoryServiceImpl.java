package com.savibpractice.apiPractice.services;

import com.savibpractice.apiPractice.domain.Category;
import com.savibpractice.apiPractice.exceptions.BadRequestException;
import com.savibpractice.apiPractice.exceptions.ResourcesNotFoundException;
import com.savibpractice.apiPractice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> fetchAllCategories(Integer userId) {
        return null;
    }

    @Override
    public Category fetchCategoryById(Integer userId, Integer categoryId) throws ResourcesNotFoundException {
        return categoryRepository.findById(userId,categoryId);
    }

    @Override
    public Category addCategory(Integer userId, String title, String description) throws BadRequestException {
        int categoryId= categoryRepository.create(userId,title,description);
        return categoryRepository.findById(userId,categoryId);
    }

    @Override
    public void updateCategory(Integer userId, Integer categoryId, Category category) throws BadRequestException {

    }

    @Override
    public void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws BadRequestException {

    }
}
