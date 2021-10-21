package com.hashedin.eventhub.eventservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashedin.eventhub.eventservice.controller.CategoryController;
import com.hashedin.eventhub.eventservice.dto.CategoryDto;

import com.hashedin.eventhub.eventservice.repository.CategoryRepository;
import com.hashedin.eventhub.eventservice.service.CategoryOperationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CategoryController.class)
class CategoryControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CategoryOperationService categoryOperationService;




	@Test
	void testgetAllCategories() throws Exception{

		List<CategoryDto> category = new ArrayList<>();
		category.add(new CategoryDto());
		Mockito.when(categoryOperationService.getAllCategories()).thenReturn(category);
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.get("/event/categories"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		List<CategoryDto> result=objectMapper.readValue(mvcResult.getResponse().getContentAsString(),new TypeReference<List<CategoryDto>>() {});

		assertEquals(category,result);
	}

	@Test
	void testAddCategory() throws Exception{

		String category="test";
		String eventjson="{\"categoryName\": \"test\"}";
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCategoryName("test");
		Mockito.when(categoryOperationService.addCategory(categoryDto)).thenReturn(category);
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.post("/event/addCategory").accept(MediaType.APPLICATION_JSON)
						.content(eventjson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String Result= mvcResult.getResponse().getContentAsString();

		assertEquals(category,Result);
	}


}
