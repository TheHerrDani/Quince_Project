package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.controller.model.SellerModel;
import marketPlace.environment.mapper.Domain_EntityMapper;
import marketPlace.environment.mapper.Domain_ModelMapper;
import marketPlace.repository.Entity.Seller;
import marketPlace.repository.ProductRepository;
import marketPlace.repository.SellerRepository;
import marketPlace.services.domain.SellerDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ComponentScan(basePackages = "marketPlace.environment")
public class SellerServiceDbImplementation implements SellerService {

    private ProductRepository productRepository;

    private SellerRepository sellerRepository;

    private Domain_ModelMapper domain_modelMapper;

    private Domain_EntityMapper domain_entityMapper;

    @Autowired
    public SellerServiceDbImplementation(ProductRepository productRepository, SellerRepository sellerRepository, Domain_ModelMapper domain_modelMapper, Domain_EntityMapper domain_entityMapper) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.domain_modelMapper = domain_modelMapper;
        this.domain_entityMapper = domain_entityMapper;
    }


    public String saveSeller(SellerModel sellerModel) {
        SellerDomain sellerDomain = domain_modelMapper.sellerModelToSellerDomain(sellerModel);
        sellerRepository.save(domain_entityMapper.sellerDomainToNewSeller(sellerDomain));
        return "Saved Seller Successfully";
    }

    public SellerModel getSellerById(int sellerId) {
        SellerDomain sellerDomain = domain_entityMapper.sellerToSellerDomain(sellerRepository.findById(sellerId).get());
        return domain_modelMapper.sellerDomainToSellerModel(sellerDomain);
    }

    @Override
    public List<SellerModel> getAllSellers() {
        Iterable<Seller> iterableSellers = sellerRepository.findAll();
        List<SellerDomain> sellerDomains = new ArrayList<>();
        iterableSellers.forEach(seller -> sellerDomains.add(domain_entityMapper.sellerToSellerDomain(seller)));
        return sellerDomains.stream().map(sellerDomain -> domain_modelMapper.sellerDomainToSellerModel(sellerDomain)).collect(Collectors.toList());
    }

    @Override
    public String deleteSeller(int sellerId) {
        sellerRepository.deleteById(sellerId);
        return "Successful delete";
    }

    @Override
    public List<ProductModel> getProductsBySeller(int sellerId) {
        SellerDomain sellerDomain = domain_entityMapper.sellerToSellerDomain(sellerRepository.findById(sellerId).get());
        return sellerDomain.getProducts().stream().map(productDomain -> domain_modelMapper.productDomainToProductModel(productDomain)).collect(Collectors.toList());
    }
}
