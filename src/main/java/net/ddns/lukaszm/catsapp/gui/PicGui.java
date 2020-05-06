package net.ddns.lukaszm.catsapp.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Route;
import net.ddns.lukaszm.catsapp.entity.Animal;
import net.ddns.lukaszm.catsapp.entity.AnimalType;
import net.ddns.lukaszm.catsapp.service.AnimalService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Route ("")
public class PicGui extends VerticalLayout {

    private AnimalService animalService;

    private final String kotki = "Wyświetl kotka";

    private final String pieski = "Wyświetl pieska";

    Animal animal;

    public PicGui(AnimalService animalService) {
        this.animalService = animalService;

        animal = new Animal();

        Button buttonShow = new Button("Wyświetl kotka");

        Button buttonLike = new Button(new Icon(VaadinIcon.THUMBS_UP));

        Button buttonGoToLiked = new Button("Zobacz polubione obrazki");

        buttonGoToLiked.addClickListener(event -> UI.getCurrent().navigate("all"));

        //todo dodaj "dodano pieska / kotka"
        Notification notificationAdd = new Notification(
                "Dodano zdjęcie do ulubionych!", 3000);

        buttonLike.setVisible(false);

        RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>();

        radioButtonGroup.setLabel("Kotki czy pieski?");

        radioButtonGroup.setItems("Kotki", "Pieski");

        radioButtonGroup.setValue("Kotki");

        radioButtonGroup.addValueChangeListener(event -> {
            if(event.getValue().equals("Kotki"))
                buttonShow.setText(kotki);
            else
                buttonShow.setText(pieski);
        });

        Image image = new Image();

        buttonShow.addClickListener(clickEvent -> {
            buttonLike.setEnabled(true);
            Animal tempAnimal = null;
            if(buttonShow.getText().equals(kotki)) {
                tempAnimal = animalService.getAnimal(AnimalType.CAT);
            }
            else {
                tempAnimal = animalService.getAnimal(AnimalType.DOG);
            }
            image.setSrc(tempAnimal.getUrl());
            image.setMaxWidth("500px");
            image.setMaxHeight("500px");
            image.setSizeFull();
            setAnimal(tempAnimal);
            buttonLike.setVisible(true);
           });

        buttonLike.addClickListener(event -> {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            animalService.saveAnimal((UserDetails) principal, new Animal(animal));
            buttonLike.setEnabled(false);
            notificationAdd.open();
        });

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(buttonShow);
        add(radioButtonGroup);
        add(buttonGoToLiked);
        add(buttonLike);
        add(image);
    }

    private void setAnimal(Animal tempAnimal) {
        animal.setUrl(tempAnimal.getUrl());
        animal.setAnimalType(tempAnimal.getAnimalType());
    }
}
