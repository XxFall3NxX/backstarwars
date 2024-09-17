package com.example.Starwars.controller;

import com.example.Starwars.DTO.CharacterSearchDTO;
import com.example.Starwars.service.CharacterSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class CharacterSearchController {

    @Autowired
    private CharacterSearchService service;

    @QueryMapping
    public CharacterSearchDTO searchCharacter(String name) throws ExecutionException, InterruptedException {
        return service.searchCharacter(name).get();
    }

    @QueryMapping
    public List<CharacterSearchDTO> getSavedSearches() {
        return service.getSavedSearches();
    }

    @MutationMapping
    public CharacterSearchDTO saveSearch(String name, List<String> films, List<String> vehicles) {
        return service.saveSearch(name, films, vehicles);
    }
}
