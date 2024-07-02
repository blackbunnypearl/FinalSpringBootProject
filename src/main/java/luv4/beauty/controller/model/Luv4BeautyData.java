package luv4.beauty.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import luv4.beauty.entity.Customer;
import luv4.beauty.entity.Luv4BeautyStore;
import luv4.beauty.entity.PurchaseOrder;

@Data
@NoArgsConstructor
public class Luv4BeautyData {
    private Long luv4BeautyStoreId;
    private String luv4BeautyStoreName;
    private String luv4BeautyStoreAddress;
    private String luv4BeautyStoreCity;
    private String luv4BeautyStoreState;
    private String luv4BeautyStoreZip;
    private String luv4BeautyStorePhone;
    private Set<Luv4BeautyCustomer> customers = new HashSet<>();

    // constructor takes a PetStore as a parameter
    public Luv4BeautyData(Luv4BeautyStore luv4BeautyStore) {
      luv4BeautyStoreId = luv4BeautyStore.getLuv4BeautyStoreId();
      luv4BeautyStoreName = luv4BeautyStore.getLuv4BeautyStoreName();
      luv4BeautyStoreAddress = luv4BeautyStore.getLuv4BeautyStoreAddress();
      luv4BeautyStoreCity = luv4BeautyStore.getLuv4BeautyStoreCity();
      luv4BeautyStoreState = luv4BeautyStore.getLuv4BeautyStoreState();
      luv4BeautyStoreZip = luv4BeautyStore.getLuv4BeautyStoreZip();
      luv4BeautyStorePhone = luv4BeautyStore.getLuv4BeautyStorePhone();
        
        for(Customer customer : luv4BeautyStore.getCustomers()) {
            customers.add(new Luv4BeautyCustomer(customer));
        }
        
            }

    // Inner class , public static so can be used as a separate DTO
    @Data
    @NoArgsConstructor
    public static class Luv4BeautyCustomer {
        
        private Long customerId;
        private String customerFirstName;
        private String customerLastName;
        private String customerEmail;
        
        //constructor that takes a Customer Object
        public Luv4BeautyCustomer(Customer customer) {
            //store fields from customer into the fields in beauty store customer
            customerId = customer.getCustomerId();
            customerFirstName = customer.getCustomerFirstName();
            customerLastName = customer.getCustomerLastName();
            customerEmail = customer.getCustomerEmail();
            
                }
    }

    // Using inner class it maintains the relationship
    @Data
    @NoArgsConstructor
    public static class Luv4BeautyPurchaseOrder {
        
        private Long purchaseOrderId;
        private String purchaseOrderDate;
        private double purchaseOrderTotal;
        private String purchaseOrderStatus;
        
        //Constructor that takes purchase order Object
        public Luv4BeautyPurchaseOrder(PurchaseOrder purchaseOrder) {
            purchaseOrderId = purchaseOrder.getPurchaseOrderId();
            purchaseOrderDate = purchaseOrder.getPurchaseOrderDate();
            purchaseOrderTotal = purchaseOrder.getPurchaseOrderTotal();
            purchaseOrderStatus = purchaseOrder.getPurchaseOrderStatus();
            
        }
    }

}