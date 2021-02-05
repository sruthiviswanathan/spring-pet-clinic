package com.example.petclinic.controllers;

import com.example.petclinic.model.Owner;
import com.example.petclinic.model.Pet;
import com.example.petclinic.model.PetType;
import com.example.petclinic.model.Visit;
import com.example.petclinic.services.PetService;
import com.example.petclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;
    @Mock
    PetService petService;

    @InjectMocks
    VisitController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        when(petService.findById(anyLong())).thenReturn(Pet.builder().visits(new HashSet<>()).petType(PetType.builder().build()).build());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void initNewVisitForm() throws Exception {
        mockMvc.perform(get("/owners/1/pets/2/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisit"));
    }

    @Test
    void processNewVisitForm() throws Exception {
        when(visitService.save(any())).thenReturn(Visit.builder().pet(Pet.builder().build()).build());
        mockMvc.perform(post("/owners/1/pets/2/visits/new")
                .param("date","2018-11-11")
                .param("description", "friendly behaviour"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    void processNewVisitFormFailureTest() throws Exception {
        mockMvc.perform(post("/owners/1/pets/3/visits/new")
                    .param("date", "ccccccc"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("error"))
                    .andExpect(view().name("pets/createOrUpdateVisit"));
        verifyNoInteractions(visitService);
    }


}