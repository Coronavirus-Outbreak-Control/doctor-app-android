package com.example.coronavirusherdimmunitydoctor.models;

public class Contact {
    private final String name;
    private final String phone;
    private final String photo;
    private boolean selected;

    public Contact(String name, String phone, String photo) {
        this.name = name;
        this.phone = phone;
        this.photo = photo;
        selected = false;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhoto() {
        return photo;
    }

    public String getFirstLetter() {
        return "" + name.charAt(0);
    }

    public boolean isSelected() {
        return selected;
    }

    public void toggleSelected() {
        selected = !selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
