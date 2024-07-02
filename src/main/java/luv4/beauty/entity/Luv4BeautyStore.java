package luv4.beauty.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Luv4BeautyStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long luv4BeautyStoreId; // Unique identifier for the beauty store
    
    private String luv4BeautyStoreName; // Name of the beauty store
    private String luv4BeautyStoreAddress; // Address of the beauty store
    private String luv4BeautyStoreCity; // City where the beauty store is located
    private String luv4BeautyStoreState; // State where the beauty store is located
    private String luv4BeautyStoreZip; // ZIP code for the beauty store
    private String luv4BeautyStorePhone; // Contact phone number for the beauty store
    
    
    // Many-to-many relationship with Customer
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "luv4_beauty_store_customer",
            joinColumns = @JoinColumn(name = "luv4_beauty_store_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Set<Customer> customers = new HashSet<>();
}
