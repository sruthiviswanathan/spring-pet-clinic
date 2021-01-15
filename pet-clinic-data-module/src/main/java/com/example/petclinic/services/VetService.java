package com.example.petclinic.services;

import com.example.petclinic.model.Vet;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface VetService extends CrudService<Vet, Long> {
}
