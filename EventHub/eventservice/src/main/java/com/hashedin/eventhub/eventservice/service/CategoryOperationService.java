package com.hashedin.eventhub.eventservice.service;

import com.hashedin.eventhub.eventservice.dto.CategoryDto;
import com.hashedin.eventhub.eventservice.entity.Category;
import com.hashedin.eventhub.eventservice.exception.RecordNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryOperationService {

    String addCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(String name) throws RecordNotFoundException;

    List<CategoryDto> getAllCategories();
}
