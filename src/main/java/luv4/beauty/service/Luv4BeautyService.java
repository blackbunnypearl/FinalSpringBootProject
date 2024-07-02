package luv4.beauty.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.Data;
import luv4.beauty.controller.model.Luv4BeautyData;
import luv4.beauty.controller.model.Luv4BeautyData.Luv4BeautyCustomer;
import luv4.beauty.controller.model.Luv4BeautyData.Luv4BeautyPurchaseOrder;
import luv4.beauty.dao.CustomerDao;
import luv4.beauty.dao.Luv4BeautyDao;
import luv4.beauty.dao.PurchaseOrderDao;
import luv4.beauty.entity.Customer;
import luv4.beauty.entity.Luv4BeautyStore;
import luv4.beauty.entity.PurchaseOrder;
@Data
@Service
public class Luv4BeautyService {

    @Autowired
    private Luv4BeautyDao luv4BeautyDao;
    
    @Transactional(readOnly = false)
    public Luv4BeautyData saveLuv4BeautyStore(Luv4BeautyData luv4BeautyData) {
        Long luv4BeautyId = luv4BeautyData.getLuv4BeautyStoreId();
        Luv4BeautyStore luv4BeautyStore = findOrCreateLuv4BeautyStore(luv4BeautyId);

        // Call copyLuv4BeautyFields to update the store data
        copyLuv4BeautyFields(luv4BeautyStore, luv4BeautyData);

        // Save the updated or newly created Luv4 Beauty store and return corresponding Luv4BeautyData object
        return new Luv4BeautyData(luv4BeautyDao.save(luv4BeautyStore));
    }

    // This method copies the data from the input (luv4BeautyData) to the Luv4 Beauty store object (luv4BeautyStore)
    private void copyLuv4BeautyFields(Luv4BeautyStore luv4BeautyStore, Luv4BeautyData luv4BeautyData) {
        luv4BeautyStore.setLuv4BeautyStoreName(luv4BeautyData.getLuv4BeautyStoreName());
        luv4BeautyStore.setLuv4BeautyStoreAddress(luv4BeautyData.getLuv4BeautyStoreAddress());
        luv4BeautyStore.setLuv4BeautyStoreCity(luv4BeautyData.getLuv4BeautyStoreCity());
        luv4BeautyStore.setLuv4BeautyStoreState(luv4BeautyData.getLuv4BeautyStoreState());
        luv4BeautyStore.setLuv4BeautyStoreZip(luv4BeautyData.getLuv4BeautyStoreZip());
        luv4BeautyStore.setLuv4BeautyStorePhone(luv4BeautyData.getLuv4BeautyStorePhone());
    }

    // This method finds an existing Luv4 Beauty store in the database based on the provided ID,
    // or it creates a new Luv4 Beauty store if the ID is null.
    private Luv4BeautyStore findOrCreateLuv4BeautyStore(Long luv4BeautyStoreId) {
        if (luv4BeautyStoreId == null) {
            return new Luv4BeautyStore(); // Create a new Luv4 Beauty store
        } else {
            // If the luv4BeautyStoreId is not null, find the Luv4 Beauty store entity object by the ID
            return luv4BeautyDao.findById(luv4BeautyStoreId)
                    .orElseThrow(() -> new NoSuchElementException("Luv4 Beauty store not found"));
        }
    }


    @Autowired
    private PurchaseOrderDao purchaseOrderDao;

    @Transactional(readOnly = false)
    public Luv4BeautyPurchaseOrder savePurchase(Long customerId, Luv4BeautyPurchaseOrder luv4BeautyStorePurchaseOrder) {
        // Fetch the Luv4 Beauty store object using the provided ID
        Customer customer  = findCustomerById(customerId);

        Long purchaseOrderId = luv4BeautyStorePurchaseOrder.getPurchaseOrderId();
        // Get the existing or create a new PurchaseOrder associated with the Luv4 Beauty store
        PurchaseOrder purchaseOrder = findOrCreatePurchaseOrder(purchaseOrderId, customerId);

        // Set the fields from Luv4BeautyPurchaseOrder to the existing PurchaseOrder
        copyPurchaseOrderFields(purchaseOrder, luv4BeautyStorePurchaseOrder);

        // Set the Luv4 Beauty store object for the PurchaseOrder
        purchaseOrder.setCustomer(customer);
        // Add the PurchaseOrder to the list of orders associated with the Luv4 Beauty store
        customer.getPurchaseOrders().add(purchaseOrder);

        // Save the modified or new PurchaseOrder object to the database
        return new Luv4BeautyPurchaseOrder(purchaseOrderDao.save(purchaseOrder));
    }

    private Customer findCustomerById(Long customerId) {
      return customerDao.findById(customerId)
              .orElseThrow(() -> new NoSuchElementException("Customer with ID " + customerId + " was not found"));
    }
    
    // Retrieve an existing PurchaseOrder or create a new one.
    private PurchaseOrder findOrCreatePurchaseOrder(Long purchaseOrderId, Long customerId) {
        if (purchaseOrderId == null) {
            return new PurchaseOrder(); // Create a new PurchaseOrder
        } else {
            return findPurchaseOrderById(customerId, purchaseOrderId);
        }
    }

    // Find a PurchaseOrder by its ID and validate its association with the Luv4 Beauty store
    private PurchaseOrder findPurchaseOrderById(Long customerId, Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderDao.findById(purchaseOrderId)
                .orElseThrow(() -> new NoSuchElementException("PurchaseOrder with ID " + purchaseOrderId + " was not found"));

        if (purchaseOrder.getCustomer().getCustomerId().equals(customerId)) {
            return purchaseOrder;
        } else {
            throw new IllegalArgumentException("PurchaseOrder with ID " + purchaseOrderId + " is not associated with the Luv4 Beauty Store with ID " + customerId);
        }
    }

    // Copy fields from Luv4BeautyPurchaseOrder to PurchaseOrder
    private void copyPurchaseOrderFields(PurchaseOrder purchaseOrder, Luv4BeautyPurchaseOrder luv4BeautyPurchaseOrder) {
        purchaseOrder.setPurchaseOrderId(luv4BeautyPurchaseOrder.getPurchaseOrderId());
        purchaseOrder.setPurchaseOrderDate(luv4BeautyPurchaseOrder.getPurchaseOrderDate());
        purchaseOrder.setPurchaseOrderTotal(luv4BeautyPurchaseOrder.getPurchaseOrderTotal());
        purchaseOrder.setPurchaseOrderStatus(luv4BeautyPurchaseOrder.getPurchaseOrderStatus());
    }

        @Autowired
        private CustomerDao customerDao;
        private Luv4BeautyService luv4BeautyService;

        @Transactional(readOnly = false)
        public Luv4BeautyCustomer saveCustomer(Long luv4BeautyStoreId, Luv4BeautyCustomer luv4BeautyCustomer) {
            // Fetch the Luv4 Beauty store object using the provided ID
            Luv4BeautyStore luv4Beauty = findLuv4BeautyById(luv4BeautyStoreId);

            Long customerId = luv4BeautyCustomer.getCustomerId();
            // Get the existing or create a new Customer associated with the Luv4 Beauty store
            Customer customer = findOrCreateCustomer(luv4BeautyStoreId, customerId, luv4BeautyCustomer);

            // Set the fields from Luv4BeautyCustomer to the existing Customer
            copyCustomerFields(customer, luv4BeautyCustomer);

            // Add the Luv4 Beauty store to the customer's associated stores
            customer.getLuv4BeautyStore().add(luv4Beauty);
            luv4Beauty.getCustomers().add(customer);

            // Save the modified or new Customer object to the database
            return new Luv4BeautyCustomer(customerDao.save(customer));
        }

        // Find a Customer by its ID and validate its association with the Luv4 Beauty store
        private Customer findCustomerById(Long luv4BeautyStoreId, Long customerId) {
            Customer customer = customerDao.findById(customerId)
                    .orElseThrow(() -> new NoSuchElementException("Customer with ID " + customerId + " was not found"));

            // Loop through the list of Luv4 Beauty stores looking for the store with the given ID
            for (Luv4BeautyStore luv4BeautyStore : customer.getLuv4BeautyStore()) {
                if (luv4BeautyStore.getLuv4BeautyStoreId().equals(luv4BeautyStoreId)) {
                    throw new IllegalArgumentException("Customer with ID " + customerId + " is already associated with the Luv4 Beauty store with ID " + luv4BeautyStoreId);
                }
            }
            return customer;
        }

        // Retrieve an existing Customer or create a new one based on the provided data
        private Customer findOrCreateCustomer(Long luv4BeautyStoreId, Long customerId, Luv4BeautyCustomer luv4BeautyCustomer) {
            if (customerId == null) {
                // If customerId is null, try to find an existing customer with the given email
                String customerEmail = luv4BeautyCustomer.getCustomerEmail();

                Optional<Customer> existingCustomer = customerDao.findByCustomerEmail(customerEmail);

                if (existingCustomer.isPresent()) {
                    return existingCustomer.get(); // Return the existing customer
                } else {
                    // No matching customer was found, so create a new customer
                    Customer newCustomer = new Customer();
                    newCustomer.setCustomerEmail(customerEmail);
                    return newCustomer;
                }
            } else {
                // If customerId is provided, find the customer by ID
                return findCustomerById(luv4BeautyStoreId, customerId);
            }
        }

        // Copy fields from Luv4BeautyCustomer to Customer
        private void copyCustomerFields(Customer customer, Luv4BeautyCustomer luv4BeautyCustomer) {
            customer.setCustomerFirstName(luv4BeautyCustomer.getCustomerFirstName());
            customer.setCustomerLastName(luv4BeautyCustomer.getCustomerLastName());
            customer.setCustomerEmail(luv4BeautyCustomer.getCustomerEmail());
        }

           
      /**
 * Retrieves all purchase orders.
 *
 * @return List of PurchaseOrder objects.
 */
@Transactional(readOnly = true)
public List<Luv4BeautyData> retrieveAllPurchaseOrder() {
    List<Luv4BeautyStore> luv4BeautyStores = luv4BeautyDao.findAll();

    List<Luv4BeautyData> resultList = new LinkedList<>();

    for (Luv4BeautyStore luv4BeautyStore : luv4BeautyStores) {
        Luv4BeautyData psd = new Luv4BeautyData(luv4BeautyStore);
        psd.getCustomers().clear(); // Remove customer data (if needed)

        resultList.add(psd);
    }

    return resultList;
}



            /**
             * Retrieves a specific Luv4 Beauty store by its ID.
             *
             * @param luv4BeautyStoreId ID of the Luv4 Beauty store
             * @return Luv4BeautyData representing the store (excluding customer and order data)
             */
            @Transactional(readOnly = true)
            public Luv4BeautyData retrieveLuv4BeautyWithID(Long luv4BeautyStoreId) {
                Luv4BeautyStore luv4BeautyStore = findLuv4BeautyById(luv4BeautyStoreId); // Retrieve the Luv4 Beauty store entity
                return new Luv4BeautyData(luv4BeautyStore);
            }

            /**
             * Deletes a Luv4 Beauty store by its ID.
             *
             * @param luv4BeautyStoreId ID of the Luv4 Beauty store to delete
             */
            @Transactional(readOnly = false)
            public void deleteLuv4BeautyById(Long luv4BeautyStoreId) {
                Luv4BeautyStore luv4BeautyStore = findLuv4BeautyById(luv4BeautyStoreId); // Retrieve the Luv4 Beauty store entity
                luv4BeautyDao.delete(luv4BeautyStore);
            }

          
            // Helper method to find a Luv4 Beauty store by its ID
            private Luv4BeautyStore findLuv4BeautyById(Long luv4BeautyStoreId) {
                return luv4BeautyDao.findById(luv4BeautyStoreId)
                        .orElseThrow(() -> new NoSuchElementException("Luv4 Beauty store with ID " + luv4BeautyStoreId + " does not exist."));
            }
        }
