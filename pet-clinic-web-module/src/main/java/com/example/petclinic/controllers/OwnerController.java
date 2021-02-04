package com.example.petclinic.controllers;

import com.example.petclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.petclinic.model.Owner;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    @Autowired
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping({"/index", "/index.html"})
    public String ownerList(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

    @RequestMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable int ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(Long.valueOf(ownerId)));
        return mav;
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model){
        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        List<Owner> results = ownerService.findAllByLastNameLike("%"+ owner.getLastName() + "%");

        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.get(0);
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners found
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/update/{ownerId}")
    public ModelAndView renderUpdateOwnerForm(@PathVariable String ownerId) {
        Owner existingOwner = ownerService.findById(Long.valueOf(ownerId));
        ModelAndView mav = new ModelAndView("owners/createOrUpdateOwner");
        mav.addObject("owner", existingOwner);
        return mav;
    }

    @PostMapping("/update/{ownerId}")
    public String saveUpdatedOwner(@ModelAttribute("owner") Owner owner, BindingResult result ,@PathVariable int ownerId) {
        if(result.hasErrors()) {
            return "owners/createOrUpdateOwner";
        } else {
            owner.setId(Long.valueOf(ownerId));
            ownerService.save(owner);
            return "redirect:/owners/" + ownerId;
        }
    }

    @GetMapping("/new")
    public ModelAndView renderCreateOwnerForm() {
        ModelAndView mav = new ModelAndView("owners/createOrUpdateOwner");
        mav.addObject("owner", Owner.builder().build());
        return mav;
    }

    @PostMapping("/new")
    public String saveOwner(@ModelAttribute("owner") Owner owner) {
        Owner savedOwnerObject = ownerService.save(owner);
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject("owner", savedOwnerObject);
        return "redirect:/owners/" + savedOwnerObject.getId();
    }


}
