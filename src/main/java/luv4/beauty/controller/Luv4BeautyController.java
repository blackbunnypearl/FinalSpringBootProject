package luv4.beauty.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import luv4.beauty.controller.model.Luv4BeautyData;
import luv4.beauty.controller.model.Luv4BeautyData.Luv4BeautyCustomer;
import luv4.beauty.controller.model.Luv4BeautyData.Luv4BeautyPurchaseOrder;
import luv4.beauty.entity.PurchaseOrder;
import luv4.beauty.service.Luv4BeautyService;
@Data
@RestController
@RequestMapping
@Slf4j
public class Luv4BeautyController {

    @Autowired
    private Luv4BeautyService luv4BeautyService;

    // Create a new beauty store
    @PostMapping("/luv4_beauty")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Luv4BeautyData insertLuv4Beauty(@RequestBody Luv4BeautyData luv4BeautyData) {
        log.info("Creating Luv4 Beauty {}", luv4BeautyData);
        return luv4BeautyService.saveLuv4BeautyStore(luv4BeautyData);
    }

    // Update an existing beauty store
    @PutMapping("/luv4_beauty/{luv4BeautyId}")
    public Luv4BeautyData updateLuv4BeautyId(@PathVariable Long luv4BeautyId,
            @RequestBody Luv4BeautyData luv4BeautyData) {
        // Set the beauty ID in the  beauty store data from the ID.
        luv4BeautyData.setLuv4BeautyStoreId(luv4BeautyId);
        log.info("Updating Luv4 Beauty {}", luv4BeautyData);
        return luv4BeautyService.saveLuv4BeautyStore(luv4BeautyData);
    }

    // Add a purchase order to an existing beauty store
    @PostMapping("/luv4_beauty/{customerId}/purchaseorder")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Luv4BeautyPurchaseOrder addPurchaseOrder(@PathVariable Long customerId,
            @RequestBody Luv4BeautyPurchaseOrder purchaseOrder) {
        log.info("Adding purchase order {} to the Luv4 Beauty store with Id {}", purchaseOrder, customerId);
        return luv4BeautyService.savePurchase(customerId, purchaseOrder);
    }

    // Add a customer to an existing beauty store
    @PostMapping("/luv4_beauty/{luv4BeautyId}/customer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Luv4BeautyCustomer addCustomer(@PathVariable Long luv4BeautyId, @RequestBody Luv4BeautyCustomer customer) {
        log.info("Adding Customer {} to the Luv4 Beauty store with Id {}", customer, luv4BeautyId);
        return luv4BeautyService.saveCustomer(luv4BeautyId, customer);
    }
 
 // This method maps to an HTTP GET request with the endpoint "/purchaseorder"
    @GetMapping("/luv4_beauty/{luv4BeautyId}/purchaseorder")
    public List<Luv4BeautyData> retrieveAllPurchaseOrder() {
        // Call the service method to retrieve all purchase orders
        return luv4BeautyService.retrieveAllPurchaseOrder();
    }

    // Get beauty store data with a specific ID
    @GetMapping("/luv4_beauty/{luv4BeautyId}")
    public Luv4BeautyData retrieveLuv4BeautyWithId(@PathVariable Long luv4BeautyId) {
        return luv4BeautyService.retrieveLuv4BeautyWithID(luv4BeautyId);
    }

    // Delete a beauty store by its ID
    @DeleteMapping("/luv4_beauty/{luv4BeautyId}")
    public Map<String, String> deleteLuv4BeautyById(@PathVariable Long luv4BeautyId) {
        log.info("Deleting Luv4 Beauty store with Id={}", luv4BeautyId);
        luv4BeautyService.deleteLuv4BeautyById(luv4BeautyId);
        return Map.of("message", "Deletion of Luv4 Beauty store with Id=" + luv4BeautyId + " was successful.");
    }
}
