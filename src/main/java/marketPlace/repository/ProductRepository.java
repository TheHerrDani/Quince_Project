package marketPlace.repository;

import org.springframework.data.repository.CrudRepository;
import marketPlace.repository.Entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
