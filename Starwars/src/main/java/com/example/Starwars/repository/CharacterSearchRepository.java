package com.example.Starwars.repository;

import com.example.Starwars.model.CharacterSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterSearchRepository extends JpaRepository<CharacterSearch, Long> {
}
