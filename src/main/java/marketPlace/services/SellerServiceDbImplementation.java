package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.controller.model.SellerModel;
import marketPlace.environment.mapper.ProductMapper;
import marketPlace.environment.mapper.SellerMapper;
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

    private SellerRepository sellerRepository;

    private ProductMapper productMapper;

    private SellerMapper sellerMapper;

    @Autowired
    public SellerServiceDbImplementation( SellerRepository sellerRepository, ProductMapper productMapper, SellerMapper sellerMapper) {
        this.sellerRepository = sellerRepository;
        this.productMapper = productMapper;
        this.sellerMapper = sellerMapper;
    }


    public String saveSeller(SellerModel sellerModel) {
        SellerDomain sellerDomain = sellerMapper.sellerModelToSellerDomain(sellerModel);
        sellerRepository.save(sellerMapper.sellerDomainToNewSeller(sellerDomain));
        return "Saved Seller Successfully";
    }

    public SellerModel getSellerById(int sellerId) {
        SellerDomain sellerDomain = sellerMapper.sellerToSellerDomain(sellerRepository.findById(sellerId).get());
        return sellerMapper.sellerDomainToSellerModel(sellerDomain);
    }

    @Override
    public List<SellerModel> getAllSellers() {
        Iterable<Seller> iterableSellers = sellerRepository.findAll();
        List<SellerDomain> sellerDomains = new ArrayList<>();
        iterableSellers.forEach(seller -> sellerDomains.add(sellerMapper.sellerToSellerDomain(seller)));
        return sellerDomains.stream().map(sellerDomain -> sellerMapper.sellerDomainToSellerModel(sellerDomain)).collect(Collectors.toList());
    }

    @Override
    public String deleteSeller(int sellerId) {
        sellerRepository.deleteById(sellerId);
        return "Successful delete";
    }

    @Override
    public List<ProductModel> getProductsBySeller(int sellerId) {
        SellerDomain sellerDomain = sellerMapper.sellerToSellerDomain(sellerRepository.findById(sellerId).get());
        return sellerDomain.getProducts().stream().map(productDomain -> productMapper.productDomainToProductModel(productDomain)).collect(Collectors.toList());
    }
}
