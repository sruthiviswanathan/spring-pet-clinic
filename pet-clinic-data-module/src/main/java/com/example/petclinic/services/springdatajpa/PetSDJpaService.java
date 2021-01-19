package com.example.petclinic.services.springdatajpa;

import com.example.petclinic.model.Pet;
import com.example.petclinic.repositories.OwnerRepository;
import com.example.petclinic.repositories.PetRepository;
import com.example.petclinic.services.PetService;
import com.example.petclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class PetSDJpaService implements PetService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final PetTypeService petTypeService;

    public PetSDJpaService(PetRepository petRepository, OwnerRepository ownerRepository, PetTypeService petTypeService) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
        this.petTypeService = petTypeService;
    }

    @Override
    public Set<Pet> findAll() {
        Set<Pet> pets = new HashSet<Pet>();
        petRepository.findAll().forEach(pets::add);
        return pets;
    }

    @Override
    public Pet findById(Long aLong) {
        return petRepository.findById(aLong).orElse(null);
    }

    @Override
    public Pet save(Pet object) {
        return petRepository.save(object);
    }

    @Override
    public void delete(Pet object) {
        petRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        petRepository.deleteById(aLong);
    }
}
