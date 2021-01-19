package com.example.petclinic.services.map;

import com.example.petclinic.model.Visit;
import com.example.petclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("mapservice")
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {

    @Override
    public Set<Visit> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Visit object) {

    }

    @Override
    public Visit save(Visit object) {
        if (object.getPet() == null || object.getPet().getOwner() == null || object.getPet().getId() == null
        || object.getPet().getOwner().getId() == null) {
            throw new RuntimeException("Invalid visit object");
        }
        return super.save(object);
    }

    @Override
    public Visit findById(Long id) {
        return super.findById(id);
    }
}
