package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.repository.Product;
import marketPlace.services.domain.ProductDomain;

import java.util.List;


public interface ProductService {
    String saveProduct(ProductModel productModel);

    String deleteProduct(int productId);

    ProductModel getProductById(int productId);

    List<ProductModel> getAllProducts();
}
