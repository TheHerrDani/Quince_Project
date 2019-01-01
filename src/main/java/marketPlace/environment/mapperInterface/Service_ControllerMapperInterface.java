package marketPlace.environment.mapperInterface;

import marketPlace.controller.model.ProductModel;
import marketPlace.controller.model.SellerModel;
import marketPlace.services.domain.ProductDomain;
import marketPlace.services.domain.SellerDomain;
import org.springframework.stereotype.Component;

@Component
public interface Service_ControllerMapperInterface {

    ProductModel productDomainToProductModel(ProductDomain productDomain);

    ProductDomain productModelToProductDomain(ProductModel productModel);

    SellerModel sellerDomainToSellerModel(SellerDomain sellerDomain);

    SellerDomain sellerModelToSellerDomain(SellerModel sellerModel);
}
