package marketPlace.environment.mapper;

import marketPlace.repository.Product;
import marketPlace.repository.Seller;
import marketPlace.repositoryInterface.ProductRepository;
import marketPlace.repositoryInterface.SellerRepository;
import marketPlace.services.domain.ProductDomain;
import marketPlace.services.domain.SellerDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Repository_ServiceMapper {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    SellerRepository sellerRepository;

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
        return product;
    }

    public ProductDomain productToProductDomain(Product product) {
        ProductDomain productDomain = new ProductDomain();
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
        return seller;
    }

    public SellerDomain sellerToSellerDomain(Seller seller) {
        SellerDomain sellerDomain = new SellerDomain();
        sellerDomain.setFirstName(seller.getFirstName());
        sellerDomain.setLastName(seller.getLastName());
        sellerDomain.setEmail(seller.getEmail());
        sellerDomain.addManyProducts(seller.getProducts().stream().map(this::productToProductDomain).collect(Collectors.toList()));
        return sellerDomain;
    }
}
