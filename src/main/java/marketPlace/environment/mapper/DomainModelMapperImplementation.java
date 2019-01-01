package marketPlace.environment.mapper;

import marketPlace.controller.model.ProductModel;
import marketPlace.controller.model.SellerModel;
import marketPlace.repository.ProductRepository;
import marketPlace.repository.SellerRepository;
import marketPlace.services.domain.ProductDomain;
import marketPlace.services.domain.SellerDomain;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DomainModelMapperImplementation implements Domain_ModelMapper {

    private ProductRepository productRepository;

    private SellerRepository sellerRepository;

    public DomainModelMapperImplementation(ProductRepository productRepository, SellerRepository sellerRepository) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    public ProductModel productDomainToProductModel(ProductDomain productDomain) {
        ProductModel productModel = new ProductModel();
        productModel.setCategory(productDomain.getCategory());
        productModel.setDescription(productDomain.getDescription());
        productModel.setName(productDomain.getName());
        productModel.setPrice(productDomain.getPrice());
        productModel.setStock(productDomain.getStock());
        productModel.setSellerId(productDomain.getSeller().getSellerId());
        return productModel;
    }

    public ProductDomain productModelToProductDomain(ProductModel productModel) {
        ProductDomain productDomain = new ProductDomain();
        productDomain.setCategory(productModel.getCategory());
        productDomain.setDescription(productModel.getDescription());
        productDomain.setName(productModel.getName());
        productDomain.setPrice(productModel.getPrice());
        productDomain.setStock(productModel.getStock());
        productDomain.setSeller(sellerRepository.findById(productModel.getSellerId()).get());
        return productDomain;
    }

    public SellerModel sellerDomainToSellerModel(SellerDomain sellerDomain) {
        SellerModel sellerModel = new SellerModel();
        sellerModel.setFirstName(sellerDomain.getFirstName());
        sellerModel.setLastName(sellerDomain.getLastName());
        sellerModel.setEmail(sellerDomain.getEmail());
        sellerModel.addManyProducts(sellerDomain.getProducts().stream().map(this::productDomainToProductModel).collect(Collectors.toList()));
        return sellerModel;
    }

    public SellerDomain sellerModelToSellerDomain(SellerModel sellerModel) {
        SellerDomain sellerDomain = new SellerDomain();
        sellerDomain.setFirstName(sellerModel.getFirstName());
        sellerDomain.setLastName(sellerModel.getLastName());
        sellerDomain.setEmail(sellerModel.getEmail());
        sellerDomain.addManyProducts(sellerModel.getProducts().stream().map(this::productModelToProductDomain).collect(Collectors.toList()));
        return sellerDomain;
    }
}
