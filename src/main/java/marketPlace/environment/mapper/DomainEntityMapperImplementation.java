package marketPlace.environment.mapper;

import marketPlace.environment.Validator;
import marketPlace.repository.Entity.Product;
import marketPlace.repository.Entity.Seller;
import marketPlace.repository.ProductRepository;
import marketPlace.repository.SellerRepository;
import marketPlace.services.domain.ProductDomain;
import marketPlace.services.domain.SellerDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DomainEntityMapperImplementation implements Domain_EntityMapper {

    private ProductRepository productRepository;

    private SellerRepository sellerRepository;

    private Validator validator;

    @Autowired
    public DomainEntityMapperImplementation(ProductRepository productRepository, SellerRepository sellerRepository, Validator validator) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.validator = validator;
    }

    public Product productDomainToProduct(ProductDomain productDomain) {
        Product product = productRepository.findById(productDomain.getProductId()).get();
        product.setCategory(productDomain.getCategory());
        product.setDescription(productDomain.getDescription());
        product.setName(productDomain.getName());
        product.setPrice(productDomain.getPrice());
        product.setStock(productDomain.getStock());
        product.setSellerId(productDomain.getSeller().getSellerId());
        return product;
    }

    public Product productDomainToNewProduct(ProductDomain productDomain) {
        Product product = new Product();
        product.setCategory(productDomain.getCategory());
        product.setDescription(productDomain.getDescription());
        product.setName(productDomain.getName());
        product.setPrice(productDomain.getPrice());
        product.setStock(productDomain.getStock());
        product.setSellerId(productDomain.getSeller().getSellerId());
        validator.productValidator(product);
        return product;
    }

    public ProductDomain productToProductDomain(Product product) {
        ProductDomain productDomain = new ProductDomain();
        productDomain.setProductId(product.getProductId());
        productDomain.setCategory(product.getCategory());
        productDomain.setDescription(product.getDescription());
        productDomain.setName(product.getName());
        productDomain.setPrice(product.getPrice());
        productDomain.setStock(product.getStock());
        productDomain.setSeller(sellerRepository.findById(product.getSellerId()).get());
        return productDomain;
    }

    public Seller sellerDomainToSeller(SellerDomain sellerDomain) {
        Seller seller = sellerRepository.findById(sellerDomain.getSellerId()).get();
        seller.setFirstName(sellerDomain.getFirstName());
        seller.setLastName(sellerDomain.getLastName());
        seller.setEmail(sellerDomain.getEmail());
        seller.addManyProducts(sellerDomain.getProducts().stream().map(this::productDomainToProduct).collect(Collectors.toList()));
        return seller;
    }

    public Seller sellerDomainToNewSeller(SellerDomain sellerDomain) {
        Seller seller = new Seller();
        seller.setFirstName(sellerDomain.getFirstName());
        seller.setLastName(sellerDomain.getLastName());
        seller.setEmail(sellerDomain.getEmail());
        seller.addManyProducts(sellerDomain.getProducts().stream().map(this::productDomainToProduct).collect(Collectors.toList()));
        validator.sellerValidator(seller);
        return seller;
    }

    public SellerDomain sellerToSellerDomain(Seller seller) {
        SellerDomain sellerDomain = new SellerDomain();
        sellerDomain.setSellerId(seller.getSellerId());
        sellerDomain.setFirstName(seller.getFirstName());
        sellerDomain.setLastName(seller.getLastName());
        sellerDomain.setEmail(seller.getEmail());
        sellerDomain.addManyProducts(seller.getProducts().stream().map(this::productToProductDomain).collect(Collectors.toList()));
        return sellerDomain;
    }
}
