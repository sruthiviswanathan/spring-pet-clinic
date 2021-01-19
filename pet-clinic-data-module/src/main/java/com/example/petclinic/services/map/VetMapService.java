package com.example.petclinic.services.map;

import com.example.petclinic.model.Speciality;
import com.example.petclinic.model.Vet;
import com.example.petclinic.services.SpecialityService;
import com.example.petclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("mapservice")
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialityService specialityService;

    public VetMapService(SpecialityService specialityService) {
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
                        Speciality savedSpl = specialityService.save(spl);
                        spl.setId(savedSpl.getId());
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
