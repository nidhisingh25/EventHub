package com.hashedin.eventhub.eventservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashedin.eventhub.eventservice.controller.EventController;
import com.hashedin.eventhub.eventservice.dto.EventDto;
import com.hashedin.eventhub.eventservice.entity.Event;
import com.hashedin.eventhub.eventservice.exception.RecordNotFoundException;
import com.hashedin.eventhub.eventservice.repository.EventRepository;
import com.hashedin.eventhub.eventservice.service.EventOperationService;
import com.hashedin.eventhub.eventservice.service.EventOperationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EventController.class)
class EventServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private EventOperationService eventOperationService;

	@MockBean
	private EventRepository eventRepository;




	@Test
	void testgetEventByid() throws Exception{

		EventDto e = new EventDto();
		Mockito.when(eventOperationService.getEventById(Mockito.anyLong())).thenReturn(e);
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.get("/event/eventid/2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		EventDto result=objectMapper.readValue(mvcResult.getResponse().getContentAsString(),EventDto.class);

		assertEquals(e,result);
	}
	@Test
	void testExceptiongetEventById(){

		Mockito.when(eventOperationService.getEventById(Mockito.anyLong())).thenThrow(new RecordNotFoundException());
		assertThrows(RecordNotFoundException.class,()->eventOperationService.getEventById(200L));
	}
	@Test
	void testgetAllEventsByCategory() throws Exception{

		List<EventDto> e = new ArrayList<>();
		e.add(new EventDto());
		Mockito.when(eventOperationService.getEventByCategory("art")).thenReturn(e);
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.get("/event/category/art"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		List<EventDto> result= (List<EventDto>) objectMapper.readValue(mvcResult.getResponse().getContentAsString(),new TypeReference<List<EventDto>>() {});

		assertEquals(e,result);
	}

	@Test
	void testgetAllEvents() throws Exception{

		List<EventDto> e = new ArrayList<>();
		e.add(new EventDto());
		Mockito.when(eventOperationService.getAllEvents()).thenReturn(e);
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.get("/event/allevents"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		List<EventDto> result= (List<EventDto>) objectMapper.readValue(mvcResult.getResponse().getContentAsString(),new TypeReference<List<EventDto>>() {});

		assertEquals(e,result);
	}

	@Test
	void testgetUpcomingEvents() throws Exception{

		List<EventDto> e = new ArrayList<>();
		e.add(new EventDto());
		Mockito.when(eventOperationService.getUpcomingEvents()).thenReturn(e);
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.get("/event/upcomingevents"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		List<EventDto> result= (List<EventDto>) objectMapper.readValue(mvcResult.getResponse().getContentAsString(),new TypeReference<List<EventDto>>() {});
		assertEquals(e,result);
	}
	@Test
	void testgetPopularEvents() throws Exception{

		List<EventDto> e = new ArrayList<>();
		e.add(new EventDto());
		Mockito.when(eventOperationService.getPopularEvents()).thenReturn(e);
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.get("/event/popularevents"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		List<EventDto> result= (List<EventDto>) objectMapper.readValue(mvcResult.getResponse().getContentAsString(),new TypeReference<List<EventDto>>() {});

		assertEquals(e,result);
	}
	@Test
	void testAddEvents() throws Exception{

		Long id=10L;
		String eventjson="{\"eventId\":null,\"eventCreationDate\":null,\"eventName\":null,\"eventDate\":null,\"eventStartTime\":null,\"eventEndTime\":null,\"eventImage\":null,\"eventDesc\":null,\"eventRules\":null,\"zoomlink\":null,\"enrollSeats\":null,\"categoryDto\":null}";
		Mockito.when(eventOperationService.addEvent("",new EventDto())).thenReturn(id);
		MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.post("/event/addevent").accept(MediaType.APPLICATION_JSON)
						.content(eventjson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		Long Result= Long.valueOf(mvcResult.getResponse().getContentAsString());
		Long result= objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Long.class);

		assertEquals(id,Result);
	}


}
