package com.example.petclinic.services.springdatajpa;

import com.example.petclinic.repositories.OwnerRepository;
import com.example.petclinic.repositories.PetRepository;
import com.example.petclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.example.petclinic.model.Owner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    public static final String LAST_NAME = "Smith";
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    Owner ownerObject;
    Long ownerId = 1L;

    @BeforeEach
    void setUp() {
        ownerObject = Owner.builder().id(1L).build();
    }

    @Test
    void findAll() {
        Set<Owner> owners = new HashSet<Owner>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());

        when(ownerRepository.findAll()).thenReturn(owners);

        Set<Owner> allOwners = ownerSDJpaService.findAll();
        assertEquals(owners.size(), allOwners.size());
        assertNotNull(owners);
    }

    @Test
    void findById() {
       when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(Owner.builder().id(1L).lastName(LAST_NAME).build()));
       Owner returnedOwner = ownerSDJpaService.findById(1L);
       assertEquals(1L, returnedOwner.getId());
       assertEquals(LAST_NAME, returnedOwner.getLastName());
       verify(ownerRepository).findById(any());
    }

    @Test
    void findByInvalidId() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner returnedOwner = ownerSDJpaService.findById(1L);
        assertNull(returnedOwner);
        verify(ownerRepository).findById(any());
    }

    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(Owner.builder().id(1L).lastName(LAST_NAME).build());
        Owner savedOwner = ownerSDJpaService.save(Owner.builder().id(1L).lastName(LAST_NAME).build());
        assertEquals(1L,savedOwner.getId());
        assertEquals(LAST_NAME, savedOwner.getLastName());
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        ownerSDJpaService.delete(ownerObject);
        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(ownerObject.getId());
        verify(ownerRepository).deleteById(anyLong());
        assertEquals(Optional.empty(), ownerRepository.findById(ownerId));
    }

    @Test
    void findByLastName() {
        Owner returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);
        Owner smith = ownerSDJpaService.findByLastName(LAST_NAME);
        assertEquals(LAST_NAME, smith.getLastName());
        verify(ownerRepository).findByLastName(any());
    }
}