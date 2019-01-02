package marketPlace.environment.mapper;

import marketPlace.controller.model.SellerModel;
import marketPlace.repository.Entity.Seller;
import marketPlace.services.domain.SellerDomain;
import org.springframework.stereotype.Component;

@Component
public interface SellerMapper {
    Seller sellerDomainToSeller(SellerDomain sellerDomain);

    Seller sellerDomainTonewSeller(SellerDomain sellerDomain);

    SellerDomain sellerToSellerDomain(Seller seller);

    SellerModel sellerDomainToSellerModel(SellerDomain sellerDomain);

    SellerDomain sellerModelToSellerDomain(SellerModel sellerModel);
}
