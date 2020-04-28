package net.ddns.kotki.kotkiapp.dao;

import net.ddns.kotki.kotkiapp.entity.Animal;
import net.ddns.kotki.kotkiapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findAllByUser(User user);
}
