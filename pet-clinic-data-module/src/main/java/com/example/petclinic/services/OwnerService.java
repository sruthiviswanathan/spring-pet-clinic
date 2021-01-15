package com.example.petclinic.services;

import com.example.petclinic.model.Owner;
import org.springframework.stereotype.Service;


@Service
public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
}
