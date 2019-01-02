package marketPlace.environment.mapper;

import marketPlace.controller.model.SellerModel;
import marketPlace.repository.Entity.Seller;
import marketPlace.services.domain.SellerDomain;

public interface SellerMapper {
    Seller sellerDomainToSeller(SellerDomain sellerDomain);

    Seller sellerDomainToNewSeller(SellerDomain sellerDomain);

    SellerDomain sellerToSellerDomain(Seller seller);

    SellerModel sellerDomainToSellerModel(SellerDomain sellerDomain);

    SellerDomain sellerModelToSellerDomain(SellerModel sellerModel);
}
