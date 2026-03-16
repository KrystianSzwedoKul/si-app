package com.si.ui;

import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Header extends HorizontalLayout {

    private Span clock;

    public Header() {

        setClassName("header");
        setWidthFull();
        setHeight("36px");
        getStyle().setColor("yellow");
        try {
            buildUI();
        } catch (Exception e) {
            System.out.println("[Header] Error while building UI. " + e);
        }
    }

    private void buildUI() {

        DrawerToggle burger = new DrawerToggle();
        burger.addClassName("header-burger");
        clock = new Span(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        clock.addClassName("header-clock");
        add(burger, clock);
        startClock();
    }

    private void startClock() {

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    getUI().ifPresent(ui -> {
                        ui.access(() -> clock.setText(LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                    });
                } catch (InterruptedException e) {
                    System.out.println("[Header] Error while updating time. " + e);
                }
            }
        }).start();
    }
}
