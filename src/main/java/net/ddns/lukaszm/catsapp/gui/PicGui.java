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

    private final String BUTTON_CAT_TEXT = "Wyświetl kotka";

    private final String BUTTON_DOG_TEXT = "Wyświetl pieska";

    private Button buttonShow;

    private Button buttonLike;

    private Button buttonGoToLiked;

    Notification notificationAdd;

    RadioButtonGroup<String> radioButtonGroup;

    Image image;

    Animal animal;

    public PicGui(AnimalService animalService) {
        this.animalService = animalService;

        animal = new Animal();

        initButtons();

        initNotification();

        initRadioButtonGroup();

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        addComponents();
    }

    private void initButtons() {
        buttonShow = new Button(BUTTON_CAT_TEXT);

        buttonLike = new Button(new Icon(VaadinIcon.THUMBS_UP));

        buttonGoToLiked = new Button("Zobacz polubione obrazki");

        buttonGoToLiked.addClickListener(event -> UI.getCurrent().navigate("all"));

        buttonLike.setVisible(false);

        buttonShow.addClickListener(clickEvent -> {
            buttonShowHandler();
        });

        buttonLike.addClickListener(event -> {
            buttonLikedHandler();
        });
    }

    private void buttonShowHandler() {
        image = new Image();

        buttonLike.setEnabled(true);
        Animal tempAnimal;
        if(buttonShow.getText().equals(BUTTON_CAT_TEXT)) {
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
    }

    private void buttonLikedHandler() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        animalService.saveAnimal((UserDetails) principal, new Animal(animal));
        buttonLike.setEnabled(false);
        notificationAdd.open();
    }

    private void initNotification() {
        notificationAdd = new Notification(
                "Dodano zdjęcie do ulubionych!", 3000);
    }

    private void initRadioButtonGroup() {
        radioButtonGroup = new RadioButtonGroup<>();

        radioButtonGroup.setLabel("Kotki czy pieski?");

        radioButtonGroup.setItems("Kotki", "Pieski");

        radioButtonGroup.setValue("Kotki");

        radioButtonGroup.addValueChangeListener(event -> {
            if(event.getValue().equals("Kotki"))
                buttonShow.setText(BUTTON_CAT_TEXT);
            else
                buttonShow.setText(BUTTON_DOG_TEXT);
        });
    }

    private void addComponents() {
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
