package com.hashedin.eventhub.eventservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashedin.eventhub.eventservice.controller.FavouriteController;
import com.hashedin.eventhub.eventservice.dto.EventDto;
import com.hashedin.eventhub.eventservice.dto.UserEventQueryParamsDto;
import com.hashedin.eventhub.eventservice.exception.RecordNotFoundException;
import com.hashedin.eventhub.eventservice.repository.EventRepository;
import com.hashedin.eventhub.eventservice.service.FavouriteOperationService;
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
@WebMvcTest(FavouriteController.class)
class FavouriteControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FavouriteOperationService favouriteOperationService;

	@Test
	void testgetAllFavouriteEvents() throws Exception{

		List<EventDto> e = new ArrayList<>();
		e.add(new EventDto());
		Mockito.when(favouriteOperationService.getFavouriteEvents("art","fads")).thenReturn(e);
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.get("/event/favourite/user/\"rajat\""))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}

	@Test
	void testAddFavouriteEvents() throws Exception{

		String msg="{\"message\":\"Successfully added as favourite\"}";
		String eventjson="{\"eventId\":null,\"eventCreationDate\":null,\"eventName\":null,\"eventDate\":null,\"eventStartTime\":null,\"eventEndTime\":null,\"eventImage\":null,\"eventDesc\":null,\"eventRules\":null,\"zoomlink\":null,\"enrollSeats\":null,\"categoryDto\":null}";
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.post("/event/addtofavourite").accept(MediaType.APPLICATION_JSON)
						.content(eventjson).content(eventjson)
						.contentType(MediaType.APPLICATION_JSON)
						)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String Result= mvcResult.getResponse().getContentAsString();

		assertEquals(msg,Result);
	}


	@Test
	void testRemoveFavouriteEvents() throws Exception{

		String msg="{\"message\":\"Successfully remove from favourite\"}";
		String eventjson="{\"eventId\":null,\"eventCreationDate\":null,\"eventName\":null,\"eventDate\":null,\"eventStartTime\":null,\"eventEndTime\":null,\"eventImage\":null,\"eventDesc\":null,\"eventRules\":null,\"zoomlink\":null,\"enrollSeats\":null,\"categoryDto\":null}";
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.post("/event/removefavourite").accept(MediaType.APPLICATION_JSON)
						.content(eventjson).content(eventjson)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String Result= mvcResult.getResponse().getContentAsString();

		assertEquals(msg,Result);
	}

}
