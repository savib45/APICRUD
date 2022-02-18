package com.savibpractice.apiPractice.resources;
import com.savibpractice.apiPractice.domain.Category;
import com.savibpractice.apiPractice.services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/categories")
public class CategoryResource {
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public String getAllCategories(HttpServletRequest request){
        int userId =(Integer) request.getAttribute("userId");
        return "Authenticated! UserId "+userId;
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(HttpServletRequest request,@PathVariable("categoryId") Integer categoryId){
        int userId=(Integer) request.getAttribute("userId");
        Category category= categoryService.fetchCategoryById(userId, categoryId);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Category> addCategory(HttpServletRequest request, @RequestBody Map<String, Object> categoryMap){
        int userId=(Integer) request.getAttribute("userId");
        String title=(String) categoryMap.get("title");
        String description =(String) categoryMap.get("description");
        Category category= categoryService.addCategory(userId,title,description);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
}
