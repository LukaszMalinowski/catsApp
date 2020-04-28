package net.ddns.kotki.kotkiapp.dao;

import net.ddns.kotki.kotkiapp.entity.Animal;
import net.ddns.kotki.kotkiapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
