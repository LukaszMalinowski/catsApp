package net.ddns.lukaszm.catsapp.dao;

import net.ddns.lukaszm.catsapp.entity.Animal;
import net.ddns.lukaszm.catsapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findAllByUser(User user);
}
