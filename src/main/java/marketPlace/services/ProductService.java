package marketPlace.services;

import marketPlace.controller.model.ProductModel;

import java.util.List;


public interface ProductService {
    String saveProduct(ProductModel productModel);

    String deleteProduct(int productId);

    ProductModel getProductById(int productId);

    List<ProductModel> getAllProducts();

    String modifyProduct(int productId, ProductModel productModel);
}
