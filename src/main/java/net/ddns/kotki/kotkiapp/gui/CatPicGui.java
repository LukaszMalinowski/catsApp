package net.ddns.kotki.kotkiapp.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Route;
import net.ddns.kotki.kotkiapp.dao.AnimalRepository;
import net.ddns.kotki.kotkiapp.dao.UserRepository;
import net.ddns.kotki.kotkiapp.entity.Animal;
import net.ddns.kotki.kotkiapp.entity.User;
import net.ddns.kotki.kotkiapp.model.AnimalType;
import net.ddns.kotki.kotkiapp.service.AnimalService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Route ("")
@StyleSheet("/css/style.css")
public class CatPicGui extends VerticalLayout {

    private AnimalService animalService;

    private AnimalRepository animalRepository;

    private UserRepository userRepository;

    private final String kotki = "Wyświetl kotka";

    private final String pieski = "Wyświetl pieska";

    public CatPicGui(AnimalService animalService, AnimalRepository animalRepository, UserRepository userRepository) {
        this.animalService = animalService;
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;

        Button button = new Button("Wyświetl kotka");

        RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>();

        radioButtonGroup.setLabel("Kotki czy pieski?");

        radioButtonGroup.setItems("Kotki", "Pieski");

        radioButtonGroup.setValue("Kotki");

        radioButtonGroup.addValueChangeListener(event -> {
            if(event.getValue().equals("Kotki"))
                button.setText(kotki);
            else
                button.setText(pieski);
        });

        Image image = new Image();

        button.addClickListener(clickEvent -> {
            if(button.getText().equals(kotki)) {
                image.setSrc(animalService.getAnimal(AnimalType.CAT));
             }
             else {
                 image.setSrc(animalService.getAnimal(AnimalType.DOG));
             }
            image.setMaxWidth("500px");
            image.setMaxHeight("500px");
             image.setSizeFull();

            userRepository.save(new User(1L, "Jan", "Jan123", "USER"));
            animalRepository.save(new Animal(1L, userRepository.findById(1L).get(),"https://cdn2.thecatapi.com/images/2np.jpg", AnimalType.CAT));
        });

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(button);
        add(radioButtonGroup);
        add(image);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
            }
}
