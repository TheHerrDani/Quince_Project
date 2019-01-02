package marketPlace.environment;

import marketPlace.repository.Entity.Product;
import marketPlace.repository.Entity.Seller;
import org.springframework.stereotype.Component;

@Component
public interface ClassValidator {
    void productValidator(Product product);

    void sellerValidator(Seller seller);

    boolean existingSeller(int sellerId);
}
