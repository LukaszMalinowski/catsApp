package net.ddns.kotki.kotkiapp.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Route;
import net.ddns.kotki.kotkiapp.model.AnimalType;
import net.ddns.kotki.kotkiapp.service.AnimalService;

@Route ("")
public class CatPicGui extends VerticalLayout {

    private AnimalService animalService;

    private final String kotki = "Wyświetl kotka";

    private final String pieski = "Wyświetl pieska";

    public CatPicGui(AnimalService animalService) {
        this.animalService = animalService;

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
             image.setMaxHeight("400px");
             image.setMaxWidth("400px");
        });

        add(button);
        add(radioButtonGroup);
        add(image);
    }
}
