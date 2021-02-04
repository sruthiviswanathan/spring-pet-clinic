package com.example.petclinic.controllers;

import com.example.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.petclinic.model.Owner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    Model model;

    @InjectMocks
    OwnerController controller;

    Set<Owner> owners;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());

        // Building mockMvc object
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    void ownerListByIndex() throws Exception {
        when(ownerService.findAll()).thenReturn(owners);
        mockMvc.perform(get("/owners/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void getOwnerById() throws Exception {

        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1l).build());

        mockMvc.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))))
                .andExpect(view().name("owners/ownerDetails"));
        verify(ownerService, times(1)).findById(anyLong());
    }

    @Test
    void testFindOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/findOwners"));
        verifyNoInteractions(ownerService);
    }

    @Test
    void findManyOwners() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString()))
                .thenReturn(Arrays.asList(Owner.builder().id(1L).build(), Owner.builder().id(2L).build()));
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }


    @Test
    void findOneOwner() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(Owner.builder().id(1L).build()));
        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + 1L));
    }

    @Test
    void findOwnerById() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1l).build());
        mockMvc.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))))
                .andExpect(view().name("owners/ownerDetails"));
        verify(ownerService, times(1)).findById(anyLong());
    }

    @Test
    void initUpdateOwnerForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(get("/owners/update/112"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))))
                .andExpect(view().name("owners/createOrUpdateOwner"));
    }

    @Test
    void initCreateOwnerForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwner"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    void saveOwnerTest() throws Exception {

    }
}