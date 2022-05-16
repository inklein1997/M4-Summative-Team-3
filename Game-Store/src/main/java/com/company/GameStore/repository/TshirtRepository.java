package com.company.GameStore.repository;

import com.company.GameStore.DTO.Tshirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TshirtRepository extends JpaRepository<Tshirt, Integer> {
    List<Tshirt> findByColor(String color);
    List<Tshirt> findBySize(String size);

}
