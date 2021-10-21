package com.hashedin.eventhub.eventservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashedin.eventhub.eventservice.controller.EnrollmentController;
import com.hashedin.eventhub.eventservice.controller.EventController;
import com.hashedin.eventhub.eventservice.dto.EventDto;
import com.hashedin.eventhub.eventservice.exception.RecordNotFoundException;
import com.hashedin.eventhub.eventservice.repository.EventRepository;
import com.hashedin.eventhub.eventservice.service.EnrollmentOperationService;
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
@WebMvcTest(EnrollmentController.class)
class EnrollmentControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EnrollmentOperationService enrollmentOperationService;

	@Test
	void testCancelEnroll() throws Exception{

		String msg="{\"message\":\"Successfully removed from enrolled event\"}";
		String eventjson="{\"eventId\":null,\"eventCreationDate\":null,\"eventName\":null,\"eventDate\":null,\"eventStartTime\":null,\"eventEndTime\":null,\"eventImage\":null,\"eventDesc\":null,\"eventRules\":null,\"zoomlink\":null,\"enrollSeats\":null,\"categoryDto\":null}";
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.post("/event/cancelenroll").accept(MediaType.APPLICATION_JSON)
						.content(eventjson).content(eventjson)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String Result= mvcResult.getResponse().getContentAsString();

		assertEquals(msg,Result);
	}

	@Test
	void testEnrollEvent() throws Exception{

		String msg="{\"message\":\"Successfully enrolled in the event\"}";
		String eventjson="{\"eventId\":null,\"eventCreationDate\":null,\"eventName\":null,\"eventDate\":null,\"eventStartTime\":null,\"eventEndTime\":null,\"eventImage\":null,\"eventDesc\":null,\"eventRules\":null,\"zoomlink\":null,\"enrollSeats\":null,\"categoryDto\":null}";
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.post("/event/enroll").accept(MediaType.APPLICATION_JSON)
						.content(eventjson).content(eventjson)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String Result= mvcResult.getResponse().getContentAsString();

		assertEquals(msg,Result);
	}
	@Test
	void testgetParticipants() throws Exception{

		String msg="0";
		String eventjson="{\"eventId\":null,\"eventCreationDate\":null,\"eventName\":null,\"eventDate\":null,\"eventStartTime\":null,\"eventEndTime\":null,\"eventImage\":null,\"eventDesc\":null,\"eventRules\":null,\"zoomlink\":null,\"enrollSeats\":null,\"categoryDto\":null}";
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.get("/event/getparticipants/12"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String Result= mvcResult.getResponse().getContentAsString();

		assertEquals(msg,Result);
	}
	@Test
	void testEnrollEventUser() throws Exception{

		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.get("/event/user/10"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String Result= mvcResult.getResponse().getContentAsString();

	}
	@Test
	void testEnrolledEvents() throws Exception{

		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.get("/event/enroll/user/Umesh"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String Result= mvcResult.getResponse().getContentAsString();

	}

}
