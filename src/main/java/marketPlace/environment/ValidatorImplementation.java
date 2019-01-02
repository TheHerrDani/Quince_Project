package marketPlace.environment;

import marketPlace.repository.Entity.Product;
import marketPlace.repository.Entity.Seller;


public class ValidatorImplementation implements Validator {
    @Override
    public void productValidator(Product product) {
        if (product.getName() == null) {
            throw new IllegalArgumentException("The product name is required!");
        } else if (product.getPrice() <= 0){
            throw new IllegalArgumentException("The price is not valid!");
        } else if (product.getStock() < 0){
            throw new IllegalArgumentException("The stock is not valid!");
        }else if (product.getSellerId() <= 0){
            throw new IllegalArgumentException("The sellerId is not valid!");
        }
    }

    @Override
    public void sellerValidator(Seller seller) {
        if (seller.getFirstName() == null) {
            throw new IllegalArgumentException("The seller's First Name is required!");
        } else if (seller.getLastName() == null){
            throw new IllegalArgumentException("The seller's Last Name is required!");
        } else if (seller.getEmail() == null){
            throw new IllegalArgumentException("The seller's Email is required!");
        }
    }
}
