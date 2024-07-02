package luv4.beauty.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import luv4.beauty.entity.PurchaseOrder;

public interface PurchaseOrderDao extends JpaRepository<PurchaseOrder, Long> {

}
