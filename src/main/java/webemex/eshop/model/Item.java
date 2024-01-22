package webemex.eshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String productName;
    private int productCode;
    private double price;
    private int volume;

    @OneToMany(
            mappedBy="item", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<CartItem> cartItems;

    @OneToMany(
            mappedBy="item", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<OrderItem> orderItems;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
