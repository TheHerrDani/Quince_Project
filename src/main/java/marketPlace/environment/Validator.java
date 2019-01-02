package marketPlace.environment;

import marketPlace.repository.Entity.Product;
import marketPlace.repository.Entity.Seller;

public interface Validator {
    void productValidator(Product product);

    void sellerValidator(Seller seller);
}
