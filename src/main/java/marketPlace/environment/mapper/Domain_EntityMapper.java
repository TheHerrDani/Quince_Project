package marketPlace.environment.mapper;

import marketPlace.repository.Entity.Product;
import marketPlace.repository.Entity.Seller;
import marketPlace.services.domain.ProductDomain;
import marketPlace.services.domain.SellerDomain;
import org.springframework.stereotype.Component;

@Component
public interface Domain_EntityMapper {

    Product productDomainToProduct(ProductDomain productDomain);

    Product productDomainToNewProduct(ProductDomain productDomain);

    ProductDomain productToProductDomain(Product product);

    Seller sellerDomainToSeller(SellerDomain sellerDomain);

    Seller sellerDomainToNewSeller(SellerDomain sellerDomain);

    SellerDomain sellerToSellerDomain(Seller seller);

}
