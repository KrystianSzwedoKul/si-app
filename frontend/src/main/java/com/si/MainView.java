package com.si;

import com.si.ui.Header;
import com.si.ui.tab.Tab1;
import com.si.ui.tab.Tab2;
import com.si.ui.tab.Tab3;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;


@Route("")
@PageTitle("Aplikacja si")
@CssImport("./styles/styles.css")
public class MainView extends AppLayout {

    public MainView() {

        addClassName("main-view");
        Button button = new Button("Button");
        addToNavbar(new Header());
        addToDrawer(createMenu());
    }

    private Tabs createMenu() {

        Tab tab1 = new Tab(new RouterLink("Tab1", Tab1.class));
        Tab tab2 = new Tab(new RouterLink("Tab2", Tab2.class));
        Tab tab3 = new Tab(new RouterLink("Tab3", Tab3.class));

        Tabs tabs = new Tabs(tab1, tab2, tab3);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }
}
