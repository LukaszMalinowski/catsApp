package net.ddns.lukaszm.catsapp.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import net.ddns.lukaszm.catsapp.entity.Animal;
import net.ddns.lukaszm.catsapp.service.AnimalService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Stream;

@Route("all")
public class LikedAnimalsGui extends VerticalLayout {

    private AnimalService service;

    public LikedAnimalsGui(AnimalService service) {
        this.service = service;

        Button buttonBack = new Button("Powrót do losowania obrazków");

        buttonBack.addClickListener(event -> UI.getCurrent().navigate(""));

        add(buttonBack);

        UserDetails user = getCurrentUser();

        List<Animal> allAnimals = service.getAllAnimals(user);

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        if(allAnimals != null && !allAnimals.isEmpty()) {
            Stream<Image> images = allAnimals.stream().map(Animal::getUrl).map(url -> new Image(url, "błąd obrazka"));

            images.forEach(image -> {
                image.setMaxWidth("500px");
                image.setMaxHeight("500px");
                image.setSizeFull();
                add(image);
            });
        }
        else {
            Label label = new Label("Brak obrazków");
            add(label);
        }
    }

    private UserDetails getCurrentUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
