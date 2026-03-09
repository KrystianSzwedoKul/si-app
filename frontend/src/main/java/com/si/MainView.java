package com.si;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route("")
@PageTitle("Aplikacja si")
@CssImport("./styles/styles.css")
public class MainView extends VerticalLayout {

    public MainView() {

        addClassName("main-view");
        Button button = new Button("Button");
        add(button);
    }
}
