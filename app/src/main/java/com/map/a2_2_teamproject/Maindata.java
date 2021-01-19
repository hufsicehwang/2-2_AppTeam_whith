package com.map.a2_2_teamproject;

public class Maindata {
    private int image;
    private String name;
    private String adress;

    public Maindata(int image, String name, String adress) {
        this.image = image;
        this.name = name;
        this.adress = adress;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
