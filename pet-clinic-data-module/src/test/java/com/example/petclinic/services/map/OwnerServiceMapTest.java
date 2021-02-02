package com.example.petclinic.services.map;

import com.example.petclinic.model.Owner;
import com.example.petclinic.model.Pet;
import com.example.petclinic.services.PetService;
import com.example.petclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;

    @Mock
    PetTypeService petTypeService;
    @Mock
    PetService petService;

    Long ownerId = 1L;
    String lastName = "Staffur";

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(petTypeService, petService);
        ownerServiceMap.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findByLastName() {
        Owner owner = ownerServiceMap.findByLastName(lastName);
        assertNotNull(owner);
    }

    @Test
    void findByInvalidLastName() {
        Owner owner = ownerServiceMap.findByLastName("InvalidLastName");
        assertNull(owner);
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerServiceMap.findAll();
        assertEquals(1, ownerSet.size());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(ownerId);
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(ownerId));
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void saveExistingId() {
        Owner owner2 = Owner.builder().id(2L).lastName("McBroom").build();
        Owner savedObject = ownerServiceMap.save(owner2);
        assertEquals(2L, savedObject.getId());
    }

    @Test
    void saveWithoutId() {
        Owner savedObject = ownerServiceMap.save(Owner.builder().build());
        assertNotNull(savedObject);
    }

    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(ownerId);
        assertEquals(1L, owner.getId());
    }
}