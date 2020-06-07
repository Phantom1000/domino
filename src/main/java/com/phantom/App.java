package com.phantom;

import com.phantom.entities.Game;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        //launch();
        try {
            new Game();
        } catch (IOException exception) {

        }
    }

}