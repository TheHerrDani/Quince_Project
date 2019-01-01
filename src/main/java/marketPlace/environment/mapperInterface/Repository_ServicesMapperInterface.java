package marketPlace.environment.mapperInterface;

import marketPlace.repository.Product;
import marketPlace.repository.Seller;
import marketPlace.services.domain.ProductDomain;
import marketPlace.services.domain.SellerDomain;
import org.springframework.stereotype.Component;

@Component
public interface Repository_ServicesMapperInterface {

    Product productDomainToProduct(ProductDomain productDomain);

    Product productDomainToNewProduct(ProductDomain productDomain);

    ProductDomain productToProductDomain(Product product);

    Seller sellerDomainToSeller(SellerDomain sellerDomain);

    Seller sellerDomainToNewSeller(SellerDomain sellerDomain);

    SellerDomain sellerToSellerDomain(Seller seller);

}
