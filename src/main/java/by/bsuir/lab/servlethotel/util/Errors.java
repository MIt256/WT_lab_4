package by.bsuir.lab.servlethotel.util;

import java.util.ArrayList;

public class Errors extends ArrayList<String> {

    public static Errors empty() {
        return new Errors();
    }

    public Errors push(String error) {
        super.add(error);
        return this;
    }
}
