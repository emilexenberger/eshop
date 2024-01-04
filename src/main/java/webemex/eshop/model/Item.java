package webemex.eshop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item") // not necessary if the name of the table is the same as class name but lowercase
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String productName;
    private int productCode;
    private double price;
    private int volume;

    public Item() {
    }

    public Item(String productName, int productCode, double price, int volume) {
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.volume = volume;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Item{" +
                "productName='" + productName + '\'' +
                ", productCode=" + productCode +
                ", price=" + price +
                ", volume=" + volume +
                '}';
    }
}
