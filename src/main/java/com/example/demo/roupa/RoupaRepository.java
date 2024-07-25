package com.example.demo.roupa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface RoupaRepository extends JpaRepository<Roupa, Long> {

    @Query("SELECT r FROM Roupa r WHERE r.active = true")
    List<Roupa> findAllActiveRoupas();

    @Query("SELECT r FROM Roupa r WHERE r.id = :id AND r.active = true")
    Roupa findActiveRoupaById(Long id);

}


