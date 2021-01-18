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

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, PetService petService, SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.petService = petService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {

        // Creating Pet Types
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);
        System.out.println("Loaded petTypes...");


        Pet pet1 = new Pet();
        pet1.setName("Tommy");
        pet1.setPetType(savedDogPetType);
        pet1.setBirthDate(LocalDate.ofYearDay(2020, 5));
        petService.save(pet1);

        Pet pet2 = new Pet();
        pet2.setName("Kitty");
        pet2.setPetType(savedCatPetType);
        pet2.setBirthDate(LocalDate.ofYearDay(2020, 12));
        petService.save(pet2);
        System.out.println("Loaded Pets...");

        // Creating Owners
        Owner owner1 = new Owner();
        owner1.setFirstName("Micheal");
        owner1.setLastName("Weston");
        owner1.setAddress("T Nagar");
        owner1.setCity("Chennai");
        owner1.setTelephone("123445");
        owner1.getPets().add(pet1);
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Micheal");
        owner2.setLastName("North");
        owner2.setAddress("KK Nagar");
        owner2.setCity("Chennai");
        owner2.setTelephone("986445");
        owner2.getPets().add(pet2);
        ownerService.save(owner2);
        System.out.println("Loaded Owners with Pets....");


        //Loading Speciality

        Speciality spl1 = new Speciality();
        spl1.setDescription("Dogs");
        specialityService.save(spl1);

        Speciality spl2 = new Speciality();
        spl2.setDescription("Cats");
        specialityService.save(spl2);

        System.out.println("Loaded Specialities....");


        // Creating Vets
        Vet vet1 = new Vet();
        vet1.setFirstName("Max");
        vet1.setLastName("Axe");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Maxi");
        vet2.setLastName("Nick");
        vetService.save(vet2);

        System.out.println("Loaded vets....");
    }
}
