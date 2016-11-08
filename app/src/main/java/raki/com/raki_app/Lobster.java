package raki.com.raki_app;

/**
 * Class for describe lobster item.
 *
 * @author Kostian Sasha
 * @version %I%, %G%
 */

public class Lobster {
    private String name;
    private String price;
    private int pictureResource; // ресурс флага

    public Lobster(String name, String price, int picture){
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
