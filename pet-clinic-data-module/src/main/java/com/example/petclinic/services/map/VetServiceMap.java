package com.example.petclinic.services.map;

import com.example.petclinic.model.Vet;
import com.example.petclinic.services.SpecialityService;
import com.example.petclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialityService specialityService;

    public VetServiceMap(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Vet object) {
        super.delete(object);
    }

    @Override
    public Vet save(Vet object) {
        if (object != null) {
            if (object.getSpecialities() != null) {
                object.getSpecialities().forEach(spl -> {
                    if(spl.getId() == null) {
                       throw new RuntimeException("Speciality must be specified");
                    }
                });
            }
            return super.save(object);
        } else {
            throw new RuntimeException("Vet Object cannot be empty");
        }

    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }
}
