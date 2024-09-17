package com.example.Starwars.service;

import com.example.Starwars.DTO.CharacterSearchDTO;
import com.example.Starwars.model.CharacterSearch;
import com.example.Starwars.repository.CharacterSearchRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CharacterSearchService {

    @Autowired
    private CharacterSearchRepository repository;

    private final String SWAPI_BASE_URL = "https://swapi.dev/api/people/?search=";

    @Async
    public CompletableFuture<CharacterSearchDTO> searchCharacter(String name) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(SWAPI_BASE_URL + name, String.class);
        JSONObject json = new JSONObject(result);
        JSONArray results = json.getJSONArray("results");

        if (results.isEmpty()) {
            return CompletableFuture.failedFuture(new Exception("Character not found"));
        }

        JSONObject character = results.getJSONObject(0);
        List<String> films = getFilmsFromApi(restTemplate, character.getJSONArray("films"));
        List<String> vehicles = getVehiclesFromApi(restTemplate, character.getJSONArray("vehicles"));

        CharacterSearchDTO dto = new CharacterSearchDTO();
        dto.setName(character.getString("name"));
        dto.setFilms(films);
        dto.setVehicles(vehicles);

        return CompletableFuture.completedFuture(dto);
    }

    public CharacterSearchDTO saveSearch(String name, List<String> films, List<String> vehicles) {
        CharacterSearch characterSearch = new CharacterSearch();
        characterSearch.setName(name);
        characterSearch.setFilms(films);
        characterSearch.setVehicles(vehicles);
        repository.save(characterSearch);
        return new CharacterSearchDTO(name, films, vehicles);
    }

    public List<CharacterSearchDTO> getSavedSearches() {
        List<CharacterSearch> searches = repository.findAll();
        List<CharacterSearchDTO> dtoList = new ArrayList<>();

        for (CharacterSearch search : searches) {
            dtoList.add(new CharacterSearchDTO(search.getName(), search.getFilms(), search.getVehicles()));
        }

        return dtoList;
    }

    private List<String> getFilmsFromApi(RestTemplate restTemplate, JSONArray filmsArray) {
        List<String> films = new ArrayList<>();
        for (Object filmUrl : filmsArray) {
            JSONObject filmJson = new JSONObject(restTemplate.getForObject(filmUrl.toString(), String.class));
            films.add(filmJson.getString("title"));
        }
        return films;
    }

    private List<String> getVehiclesFromApi(RestTemplate restTemplate, JSONArray vehiclesArray) {
        List<String> vehicles = new ArrayList<>();
        for (Object vehicleUrl : vehiclesArray) {
            JSONObject vehicleJson = new JSONObject(restTemplate.getForObject(vehicleUrl.toString(), String.class));
            vehicles.add(vehicleJson.getString("name"));
        }
        return vehicles;
    }
}
