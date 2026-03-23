package com.si.ui.tab;

import com.si.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;


@Route(value = "/tab1", layout = MainView.class)
public class Tab1 extends VerticalLayout {

    public Tab1() {

        try {
            buildUI();
        } catch (Exception e) {
            System.out.println("[Tab1] Error while build UI.");
        }
    }

    private void buildUI() {

        addClassName("tab1");
        VerticalLayout content = new VerticalLayout();
        content.addClassName("tab1-content");

        Span info = new Span("Sprawdź gramatykę zdania");
        info.addClassName("tab1-span");

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.addClassName("tab1-span");
        List<String> languages = List.of("Polski", "Angielski", "Niemiecki");
        comboBox.setItems(languages);
        comboBox.setPlaceholder("Wybierz język:");

        TextArea text = new TextArea("");
        text.addClassName("tab1-text-area");
        text.setPlaceholder("Wpisz zdanie do sprawdzenia...");

        Button button = new Button("Sprawdz",
                VaadinIcon.AIRPLANE.create());
        button.addClassName("clen");

        content.add(info, comboBox, text,button);
        add(content);
    }
}
