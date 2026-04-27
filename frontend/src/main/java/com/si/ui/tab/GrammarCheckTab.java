package com.si.ui.tab;

import com.si.MainView;
import com.si.config.ApplicationContext;
import com.si.servis.WebService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

import java.util.List;


@Route(value = "/grammar-check-tab", layout = MainView.class)
public class GrammarCheckTab extends VerticalLayout {

    public GrammarCheckTab() {

        try {
            buildUI();
        } catch (Exception e) {
            System.out.println("[GrammarCheckTab] Error while build UI.");
        }
    }

    private void buildUI() {

        addClassName("grammar-check-tab");
        VerticalLayout content = new VerticalLayout();
        content.addClassName("grammar-check-tab-content");

        Span info = new Span("Sprawdź gramatykę zdania");
        info.addClassName("grammar-check-tab-span");

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.addClassName("grammar-check-tab-span");
        List<String> languages = List.of("Polski", "Angielski", "Niemiecki");
        comboBox.setItems(languages);
        comboBox.setPlaceholder("Wybierz język:");

        TextArea text = new TextArea("");
        text.addClassName("grammar-check-tab-text-area");
        text.setPlaceholder("Wpisz zdanie do sprawdzenia...");

        Button button = new Button("Sprawdz",
                VaadinIcon.AIRPLANE.create());
        button.addClassName("grammar-check-tab-check-button");
        button.addClickListener(e -> {
            String language = comboBox.getValue();
            String sentence = text.getValue();

            WebService webService = ApplicationContext.getBean(WebService.class);
            webService.isValidSentence(language, sentence);
        });
        content.add(info, comboBox, text, button);
        add(content);
    }
}
