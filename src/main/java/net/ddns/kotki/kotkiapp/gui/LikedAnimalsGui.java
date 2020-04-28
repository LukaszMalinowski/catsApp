package net.ddns.kotki.kotkiapp.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import net.ddns.kotki.kotkiapp.entity.Animal;
import net.ddns.kotki.kotkiapp.service.AnimalService;
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

        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Animal> allAnimals = service.getAllAnimals((UserDetails)user);

        if(allAnimals != null && !allAnimals.isEmpty()) {
            Stream<Image> images = allAnimals.stream().map(Animal::getUrl).map(url -> new Image(url, "błąd obrazka"));

            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
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
}
