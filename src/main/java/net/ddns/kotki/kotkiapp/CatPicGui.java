package net.ddns.kotki.kotkiapp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.io.IOException;

@Route ("")
public class CatPicGui extends VerticalLayout {

    CatService service;

    public CatPicGui(CatService service) {
        this.service = service;
        Button button = new Button("Wyświetl kotka");

        Image image = new Image();

        button.addClickListener(clickEvent -> {
            try {
                image.setSrc(service.getCat());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        add(button);
        add(image);
    }
}
