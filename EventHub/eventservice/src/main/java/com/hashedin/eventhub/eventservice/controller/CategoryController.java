package com.hashedin.eventhub.eventservice.controller;

import com.hashedin.eventhub.eventservice.dto.CategoryDto;
import com.hashedin.eventhub.eventservice.service.CategoryOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
@RequestMapping(value="/event")
public class CategoryController {

    @Autowired
    CategoryOperationService categoryService;

    @GetMapping("/categories")
    public  ResponseEntity<?> getAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK)).body(categoryService.getAllCategories());
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> save(@RequestBody CategoryDto categoryDto)
    {
        String message = categoryService.addCategory(categoryDto);
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
