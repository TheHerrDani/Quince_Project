package marketPlace.environment.mapper;

import marketPlace.controller.model.ProductModel;
import marketPlace.environment.ClassValidator;
import marketPlace.repository.Entity.Product;
import marketPlace.repository.ProductRepository;
import marketPlace.repository.SellerRepository;
import marketPlace.services.domain.ProductDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImplementation implements ProductMapper {

    private ProductRepository productRepository;

    private SellerRepository sellerRepository;

    private ClassValidator validator;

    @Autowired
    public ProductMapperImplementation(ProductRepository productRepository, SellerRepository sellerRepository, ClassValidator validator) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.validator = validator;
    }

    public Product productDomainToProduct(ProductDomain productDomain) {
        Product product = productRepository.findById(productDomain.getProductId()).get();
        return copyProductAttributesToProduct(product, productDomain);
    }

    public Product productDomainToNewProduct(ProductDomain productDomain) {
        Product product = new Product();
        copyProductAttributesToProduct(product, productDomain);
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
        productDomain.setNumberOfSales(product.getNumberOfSales());
        productDomain.setNumberOfViewed(product.getNumberOfViewed());
        return productDomain;
    }

    public ProductModel productDomainToProductModel(ProductDomain productDomain) {
        ProductModel productModel = new ProductModel();
        productModel.setProductId(productDomain.getProductId());
        productModel.setCategory(productDomain.getCategory());
        productModel.setDescription(productDomain.getDescription());
        productModel.setName(productDomain.getName());
        productModel.setPrice(productDomain.getPrice());
        productModel.setStock(productDomain.getStock());
        productModel.setSellerId(productDomain.getSeller().getSellerId());
        productModel.setNumberOfSales(productDomain.getNumberOfSales());
        productModel.setNumberOfViewed(productDomain.getNumberOfViewed());
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

    private Product copyProductAttributesToProduct(Product product, ProductDomain productDomain) {
        product.setCategory(productDomain.getCategory());
        product.setDescription(productDomain.getDescription());
        product.setName(productDomain.getName());
        product.setPrice(productDomain.getPrice());
        product.setStock(productDomain.getStock());
        product.setSellerId(productDomain.getSeller().getSellerId());
        return product;
    }

}
