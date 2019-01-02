package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.controller.model.SellerModel;
import marketPlace.environment.mapper.ProductMapper;
import marketPlace.environment.mapper.SellerMapper;
import marketPlace.repository.Entity.Seller;
import marketPlace.repository.SellerRepository;
import marketPlace.services.domain.SellerDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@ComponentScan(basePackages = "marketPlace.environment")
public class SellerServiceDbImplementation implements SellerService {

    private SellerRepository sellerRepository;

    private ProductMapper productMapper;

    private SellerMapper sellerMapper;

    @Autowired
    public SellerServiceDbImplementation(SellerRepository sellerRepository, ProductMapper productMapper, SellerMapper sellerMapper) {
        this.sellerRepository = sellerRepository;
        this.productMapper = productMapper;
        this.sellerMapper = sellerMapper;
    }


    public String saveSeller(SellerModel sellerModel) {
        SellerDomain sellerDomain = sellerMapper.sellerModelToSellerDomain(sellerModel);
        sellerRepository.save(sellerMapper.sellerDomainToSeller(sellerDomain));
        return "Saved Seller Successfully";
    }

    public SellerModel getSellerById(int sellerId) {
        SellerDomain sellerDomain = sellerMapper.sellerToSellerDomain(sellerRepository.findById(sellerId).get());
        SellerModel result = sellerMapper.sellerDomainToSellerModel(sellerDomain);
        result.setAverageRatings(averageRatings(sellerDomain));
        return result;
    }

    @Override
    public List<SellerModel> getAllSellers() {
        Iterable<Seller> iterableSellers = sellerRepository.findAll();
        List<SellerDomain> sellerDomains = new ArrayList<>();
        iterableSellers.forEach(seller -> sellerDomains.add(sellerMapper.sellerToSellerDomain(seller)));
        return sellerDomains.stream().map(sellerModelMapper).collect(Collectors.toList());
    }

    @Override
    public String deleteSeller(int sellerId) {
        sellerRepository.deleteById(sellerId);
        return "Successful delete";
    }

    @Override
    public List<ProductModel> getProductsBySeller(int sellerId) {
        if (existingSeller(sellerId))
            throw new IllegalArgumentException("There is none seller with this sellerId");
        SellerDomain sellerDomain = sellerMapper.sellerToSellerDomain(sellerRepository.findById(sellerId).get());
        return sellerDomain.getProducts().stream().map(productDomain -> productMapper.productDomainToProductModel(productDomain)).collect(Collectors.toList());
    }

    @Override
    public String addRating(int sellerId, int rating) {
        sellerRepository.findById(sellerId).get().addRatings(rating);
        return "Rating added successfully.";
    }
    // Helper Methods and Attributes

    private Function<SellerDomain, SellerModel> sellerModelMapper = (sellerDomain) -> {
        SellerModel sellerModel = sellerMapper.sellerDomainToSellerModel(sellerDomain);
        sellerModel.setAverageRatings(averageRatings(sellerDomain));
        return sellerModel;
    };


    public boolean existingSeller(int sellerId) {
        return this.getAllSellers().stream().noneMatch(sellerModel -> sellerModel.getSellerId() == sellerId);
    }

    private double averageRatings(SellerDomain sellerDomain) {
        return sellerDomain.getRatings().stream().mapToInt(Integer::intValue).average().getAsDouble();
    }
}
