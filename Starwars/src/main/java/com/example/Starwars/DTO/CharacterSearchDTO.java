package com.example.Starwars.DTO;

import java.util.List;

public class CharacterSearchDTO {

    private String name;
    private List<String> films;
    private List<String> vehicles;

    // Constructors
    public CharacterSearchDTO() {}

    public CharacterSearchDTO(String name, List<String> films, List<String> vehicles) {
        this.name = name;
        this.films = films;
        this.vehicles = vehicles;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFilms() {
        return films;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }

    public List<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<String> vehicles) {
        this.vehicles = vehicles;
    }
}
