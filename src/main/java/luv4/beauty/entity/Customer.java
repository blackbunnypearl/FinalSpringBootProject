package luv4.beauty.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;

    // Many-to-many relationship with Luv4BeautyStore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "customers")
    private Set<Luv4BeautyStore> luv4BeautyStore = new HashSet<>();
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
     @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
     private Set<PurchaseOrder> purchaseOrders = new HashSet<>();
}
