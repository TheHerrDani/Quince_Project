package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.controller.model.SellerModel;
import marketPlace.environment.mapper.ProductMapper;
import marketPlace.environment.mapper.SellerMapper;
import marketPlace.repository.Entity.Seller;
import marketPlace.repository.SellerRepository;
import marketPlace.services.domain.ProductDomain;
import marketPlace.services.domain.SellerDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
        sellerRepository.save(sellerMapper.sellerDomainTonewSeller(sellerDomain));
        return "Saved Seller Successfully";
    }

    public SellerModel getSellerById(int sellerId) {
        if (sellerIsNotExist(sellerId))
            throw new IllegalArgumentException("There is none seller with this sellerId");
        SellerDomain sellerDomain = sellerMapper.sellerToSellerDomain(sellerRepository.findById(sellerId).get());
        SellerModel result = sellerMapper.sellerDomainToSellerModel(sellerDomain);
        result.setAverageRatings(averageRatings(sellerDomain));
        return result;
    }

    @Override
    public List<SellerModel> getAllSellers() {
        List<SellerDomain> sellerDomains = getAllSellerDomain();
        return sellerDomainListToSellerModelList(sellerDomains);
    }

    @Override
    public String deleteSeller(int sellerId) {
        if (sellerIsNotExist(sellerId))
            throw new IllegalArgumentException("There is none seller with this sellerId");
        sellerRepository.deleteById(sellerId);
        return "Successful delete";
    }

    @Override
    public List<ProductModel> getProductsBySeller(int sellerId) {
        if (sellerIsNotExist(sellerId))
            throw new IllegalArgumentException("There is none seller with this sellerId");
        SellerDomain sellerDomain = sellerMapper.sellerToSellerDomain(sellerRepository.findById(sellerId).get());
        return sellerDomain.getProducts().stream().map(productDomain -> productMapper.productDomainToProductModel(productDomain)).collect(Collectors.toList());
    }

    @Override
    public String addRating(int sellerId, int rating) {
        ratingValidator(sellerId, rating);
        Seller seller = sellerRepository.findById(sellerId).get().addRatings(rating);
        sellerRepository.save(seller);
        return "Rating added successfully.";
    }

    @Override
    public String modifySellerById(int sellerId, SellerModel sellerModel) {
        if (sellerIsNotExist(sellerId))
            throw new IllegalArgumentException("There is none seller with this sellerId");

        Seller starterSeller = sellerRepository.findById(sellerId).get();

        SellerDomain oldSellerDomain = sellerMapper.sellerToSellerDomain(starterSeller);

        SellerDomain newSellerDomain = sellerMapper.sellerModelToSellerDomain(sellerModel);

        if (newSellerDomain.getFirstName() != null && !newSellerDomain.getFirstName().equals(oldSellerDomain.getFirstName()))
            oldSellerDomain.setFirstName(newSellerDomain.getFirstName());

        if (newSellerDomain.getLastName() != null && !newSellerDomain.getLastName().equals(oldSellerDomain.getLastName()))
            oldSellerDomain.setLastName(newSellerDomain.getLastName());

        if (newSellerDomain.getEmail() != null && !newSellerDomain.getEmail().equals(oldSellerDomain.getEmail()))
            oldSellerDomain.setEmail(newSellerDomain.getEmail());

        sellerRepository.save(sellerMapper.sellerDomainToSeller(oldSellerDomain));
        return String.format("Successful modified product with %s id ", sellerId);
    }

    @Override
    public List<SellerModel> getSellersWithSalesData() {
        List<SellerDomain> allSellerDomain = getAllSellerDomain();
        getSellerDomainWithSalesData(allSellerDomain);
        return sellerDomainListToSellerModelList(allSellerDomain);
    }

    @Override
    public List<SellerModel> orderingProductsBySalesData(boolean ascending) {
        List<SellerDomain> allSellerDomain = getAllSellerDomain();
        List<SellerModel> sellerModelList = sellerDomainListToSellerModelList(allSellerDomain);
        if(ascending){
            sellerModelList.sort(compareByAvrageRating);
        }else{
            sellerModelList.sort(Collections.reverseOrder(compareByAvrageRating));
        }
        return sellerModelList;
    }

    @Override
    public List<SellerModel> top5Seller() {
        return null;
    }


    // Helper Methods and Attributes

    private Function<SellerDomain, SellerModel> sellerModelMapper = (sellerDomain) -> {
        SellerModel sellerModel = sellerMapper.sellerDomainToSellerModel(sellerDomain);
        sellerModel.setAverageRatings(averageRatings(sellerDomain));
        return sellerModel;
    };

    public boolean sellerIsNotExist(int sellerId) {
        return !sellerRepository.existsById(sellerId);
    }

    public void ratingValidator(int sellerId, int rating) {
        if (sellerIsNotExist(sellerId)) {
            throw new IllegalArgumentException("rating must be in the scale of 1-5");
        } else if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("There is none seller with this sellerId");
        }
    }

    private double averageRatings(SellerDomain sellerDomain) {
        IntSummaryStatistics intSummaryStatistics = sellerDomain.getRatings().stream().collect(Collectors.summarizingInt(Integer::intValue));
        return intSummaryStatistics.getAverage();
    }

    private List<SellerDomain> getAllSellerDomain() {
        Iterable<Seller> iterableSellers = sellerRepository.findAll();
        List<SellerDomain> sellerDomains = new ArrayList<>();
        iterableSellers.forEach(seller -> sellerDomains.add(sellerMapper.sellerToSellerDomain(seller)));
        return sellerDomains;
    }

    private List<SellerModel> sellerDomainListToSellerModelList(List<SellerDomain> sellerDomains) {
        return sellerDomains.stream().map(sellerModelMapper).collect(Collectors.toList());
    }

    private List<SellerDomain> getSellerDomainWithSalesData(List<SellerDomain> sellerDomains) {
        Consumer<SellerDomain> getSellerDomainValue = sellerDomain -> {
            List<ProductDomain> productDomains = sellerDomain.getProducts();
            productDomains.forEach(productDomain -> {
                double value = productDomain.getNumberOfSales() * productDomain.getPrice();
                productDomain.setSalesValue(value);
                sellerDomain.addSellValue(value);
                sellerDomain.addSellAmount(productDomain.getNumberOfSales());
            });
        };
        sellerDomains.forEach(getSellerDomainValue);
        return sellerDomains;
    }

    private Comparator<SellerModel> compareByAvrageRating = (SellerModel sellerModelFirst, SellerModel sellerModelSecond) -> {
        if (sellerModelFirst.getAverageRatings() > sellerModelSecond.getAverageRatings()){
            return 1;
        }else if (sellerModelFirst.getAverageRatings() == sellerModelSecond.getAverageRatings()){
            return 0;
        }else{
            return -1;
        }
    };

}
