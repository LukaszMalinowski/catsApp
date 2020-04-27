package net.ddns.kotki.kotkiapp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Route;

import java.io.IOException;

@Route ("")
public class CatPicGui extends VerticalLayout {

    private CatService catService;

    private DogService dogService;



    private final String kotki = "Wyświetl kotka";

    private final String pieski = "Wyświetl pieska";

    public CatPicGui(CatService catService, DogService dogService) {
        this.catService = catService;
        this.dogService = dogService;

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
            try {
                if(button.getText().equals(kotki)) {
                    image.setSrc(catService.getCat());
                 }
                 else {
                     image.setSrc(dogService.getDog());
                 }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        add(button);
        add(radioButtonGroup);
        add(image);
    }
}
