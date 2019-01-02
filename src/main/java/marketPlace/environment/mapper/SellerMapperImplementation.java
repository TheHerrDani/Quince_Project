package marketPlace.environment.mapper;

import marketPlace.controller.model.SellerModel;
import marketPlace.environment.ClassValidator;
import marketPlace.repository.Entity.Seller;
import marketPlace.repository.ProductRepository;
import marketPlace.repository.SellerRepository;
import marketPlace.services.domain.SellerDomain;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

public class SellerMapperImplementation implements SellerMapper {

    private ProductRepository productRepository;

    private SellerRepository sellerRepository;

    private ClassValidator validator;

    private ProductMapper productMapper;

    @Autowired
    public SellerMapperImplementation(ProductRepository productRepository, SellerRepository sellerRepository, ClassValidator validator, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.validator = validator;
        this.productMapper = productMapper;
    }

    @Override
    public Seller sellerDomainToSeller(SellerDomain sellerDomain) {
        Seller seller = sellerRepository.findById(sellerDomain.getSellerId()).get();
        seller.setFirstName(sellerDomain.getFirstName());
        seller.setLastName(sellerDomain.getLastName());
        seller.setEmail(sellerDomain.getEmail());
        seller.addManyProducts(sellerDomain.getProducts().stream().map(productMapper::productDomainToProduct).collect(Collectors.toList()));
        return seller;
    }

    @Override
    public Seller sellerDomainToNewSeller(SellerDomain sellerDomain) {
        Seller seller = new Seller();
        seller.setFirstName(sellerDomain.getFirstName());
        seller.setLastName(sellerDomain.getLastName());
        seller.setEmail(sellerDomain.getEmail());
        seller.addManyProducts(sellerDomain.getProducts().stream().map(productMapper::productDomainToProduct).collect(Collectors.toList()));
        validator.sellerValidator(seller);
        return seller;
    }

    @Override
    public SellerDomain sellerToSellerDomain(Seller seller) {
        SellerDomain sellerDomain = new SellerDomain();
        sellerDomain.setSellerId(seller.getSellerId());
        sellerDomain.setFirstName(seller.getFirstName());
        sellerDomain.setLastName(seller.getLastName());
        sellerDomain.setEmail(seller.getEmail());
        sellerDomain.addManyProducts(seller.getProducts().stream().map(productMapper::productToProductDomain).collect(Collectors.toList()));
        return sellerDomain;
    }

    @Override
    public SellerModel sellerDomainToSellerModel(SellerDomain sellerDomain) {
        SellerModel sellerModel = new SellerModel();
        sellerModel.setSellerId(sellerDomain.getSellerId());
        sellerModel.setFirstName(sellerDomain.getFirstName());
        sellerModel.setLastName(sellerDomain.getLastName());
        sellerModel.setEmail(sellerDomain.getEmail());
        sellerModel.addManyProducts(sellerDomain.getProducts().stream().map(productMapper::productDomainToProductModel).collect(Collectors.toList()));
        return sellerModel;
    }

    @Override
    public SellerDomain sellerModelToSellerDomain(SellerModel sellerModel) {
        SellerDomain sellerDomain = new SellerDomain();
        sellerDomain.setFirstName(sellerModel.getFirstName());
        sellerDomain.setLastName(sellerModel.getLastName());
        sellerDomain.setEmail(sellerModel.getEmail());
        sellerDomain.addManyProducts(sellerModel.getProducts().stream().map(productMapper::productModelToProductDomain).collect(Collectors.toList()));
        return sellerDomain;
    }

}
