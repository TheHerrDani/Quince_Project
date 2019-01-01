package marketPlace.repository;

import org.springframework.data.repository.CrudRepository;
import marketPlace.repository.Entity.Seller;

public interface SellerRepository extends CrudRepository<Seller, Integer>{

}
