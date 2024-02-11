package webemex.eshop.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Item {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;
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

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
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
