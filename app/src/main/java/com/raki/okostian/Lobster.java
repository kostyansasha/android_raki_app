package com.raki.okostian;

/**
 * Class for describe lobster item.
 *
 * @author Kostian Sasha
 * @version %I%, %G%
 */

public class Lobster {
    private String name;
    private int price;
    private int pictureResource; // ресурс флага

    public Lobster(String name, int price, int picture){
        this.name = name;
        this.price = price;
        this.pictureResource = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getPictureResource() {
        return pictureResource;
    }

    public void setPictureResource(int pictureResource) {
        this.pictureResource = pictureResource;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
