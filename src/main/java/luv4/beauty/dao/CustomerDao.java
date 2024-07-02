package luv4.beauty.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import luv4.beauty.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

  Optional<Customer> findByCustomerEmail(String customerEmail);

}
