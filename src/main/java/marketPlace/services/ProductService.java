package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.repository.Entity.ProductCategory;

import java.util.List;
import java.util.Map;


public interface ProductService {
    String saveProduct(ProductModel productModel);

    String deleteProduct(int productId);

    ProductModel getProductById(int productId);

    List<ProductModel> getAllProducts();

    String modifyProduct(int productId, ProductModel productModel);

    String buyingProductById(int productId, int amount);

    List<ProductModel> getProductsWithSalesData();

    List<ProductModel> orderingProductsBySalesData();

    List<ProductModel> mostViewedProducts();

    Map<ProductCategory, Integer> salesByCategory();
}
