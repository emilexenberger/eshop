package webemex.eshop.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_user")
    private AppUser appUser;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_item")
    private Item item;

    private int volume;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", appUser=" + appUser +
                ", item=" + item +
                ", volume=" + volume +
                '}';
    }
}
