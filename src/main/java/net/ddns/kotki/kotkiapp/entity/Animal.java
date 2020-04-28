package net.ddns.kotki.kotkiapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "url")
    private String url;

    @Column(name = "animalType")
    private AnimalType animalType;

    public Animal() {
    }

    public Animal(Animal animal) {
        this.id = animal.id;
        this.url = animal.url;
        this.user = animal.user;
        this.animalType = animal.animalType;
    }

    public Animal(Long id, User user, String url, AnimalType animalType) {
        this.id = id;
        this.url = url;
        this.animalType = animalType;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", user=" + user +
                ", url='" + url + '\'' +
                ", animalType=" + animalType +
                '}';
    }
}
