package marketPlace.environment.mapper;

import marketPlace.controller.model.ProductModel;
import marketPlace.repository.Entity.Product;
import marketPlace.services.domain.ProductDomain;

public interface ProductMapper {

    Product productDomainToProduct(ProductDomain productDomain);

    Product productDomainToNewProduct(ProductDomain productDomain);

    ProductDomain productToProductDomain(Product product);

    ProductModel productDomainToProductModel(ProductDomain productDomain);

    ProductDomain productModelToProductDomain(ProductModel productModel);
}
