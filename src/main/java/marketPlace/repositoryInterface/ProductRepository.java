package marketPlace.repositoryInterface;

import org.springframework.data.repository.CrudRepository;
import marketPlace.repository.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
