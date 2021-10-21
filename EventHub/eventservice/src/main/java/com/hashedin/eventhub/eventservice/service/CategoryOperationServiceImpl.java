package com.hashedin.eventhub.eventservice.service;

import com.hashedin.eventhub.eventservice.dto.CategoryDto;
import com.hashedin.eventhub.eventservice.entity.Category;
import com.hashedin.eventhub.eventservice.exception.RecordNotFoundException;
import com.hashedin.eventhub.eventservice.repository.CategoryRepository;
import com.hashedin.eventhub.eventservice.utils.ObjectConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryOperationServiceImpl implements CategoryOperationService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ObjectConversion convert;

    @Override
    public String addCategory(CategoryDto categoryDto) {
        categoryRepository.save((Category) convert.convert(categoryDto, Category.class) );
        return "Successfully Added";
    }

    @Override
    public CategoryDto getCategoryById(String name) throws RecordNotFoundException {
        Optional<Category> category = categoryRepository.findById(name);
        System.out.println(category.get());
        if(category.isPresent())
            return (CategoryDto) convert.convert( category.get(), CategoryDto.class);
        else
            throw new RecordNotFoundException("Category " +category+ " not found");

    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(s->(CategoryDto) convert.convert(s,CategoryDto.class)).collect(Collectors.toList());
    }
}
