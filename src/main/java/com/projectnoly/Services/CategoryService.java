package com.projectnoly.Services;

import com.projectnoly.Model.MySql.Category;
import com.projectnoly.Repositories.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;
    public CategoryService (CategoryRepo categoryRepo){
        this.categoryRepo = categoryRepo;
    }

    public List<Category> getAllCategories(){
        return categoryRepo.findAll();
    }
    public Category getCategoryById(int id){
        return categoryRepo.findById(id).orElse(null);
    }


}
