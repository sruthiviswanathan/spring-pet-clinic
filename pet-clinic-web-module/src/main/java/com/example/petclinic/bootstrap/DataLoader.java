package com.example.petclinic.bootstrap;

import com.example.petclinic.model.*;
import com.example.petclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final PetService petService;
    private final SpecialityService specialityService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
                      PetService petService, SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.petService = petService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = petTypeService.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        // Creating Pet Types
        PetType dog = new PetType("Dog");
        PetType cat = new PetType("Cat");
        PetType savedDogInstance = petTypeService.save(dog);
        PetType savedCatInstance = petTypeService.save(cat);

        // Creating Pets
        Pet pet1 = new Pet("Tommy", savedDogInstance, LocalDate.ofYearDay(2020, 5));
        Pet pet2 = new Pet("Kitty", savedCatInstance, LocalDate.ofYearDay(2020, 6));

        // Creating Owners
        Owner owner1 = new Owner("Micheal", "Western", "TNagar", "Chennai", "1234556", pet1);
        Owner owner2 = new Owner("Mike", "Ulrich", "KK Nagar", "Chennai", "8776534", pet2);

        // Saving Pet Objects
        ownerService.save(owner1);
        ownerService.save(owner2);
        System.out.println("Loaded Owners with Pets and PetTypes....");

        // Creating Speciality
        Speciality radiologySpeciality = new Speciality("Radiology");
        Speciality surgerySpeciality = new Speciality("Surgery");
        Speciality savedRadiologySpeciality = specialityService.save(radiologySpeciality);
        Speciality savedSurgerySpeciality = specialityService.save(surgerySpeciality);

        // Creating Vets
        Vet vet1 = new Vet("Max", "Axe", savedRadiologySpeciality);
        Vet vet2 = new Vet("Maxi", "Nick", savedSurgerySpeciality);
        vetService.save(vet1);
        vetService.save(vet2);
        System.out.println("Loaded Vets with Speciality....");
    }
}
