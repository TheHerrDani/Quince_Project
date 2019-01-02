package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.environment.mapper.DomainEntityMapperImplementation;
import marketPlace.environment.mapper.Domain_EntityMapper;
import marketPlace.environment.mapper.Domain_ModelMapper;
import marketPlace.repository.Entity.Product;
import marketPlace.repository.Entity.Seller;
import marketPlace.repository.ProductCategory;
import marketPlace.repository.ProductRepository;
import marketPlace.repository.SellerRepository;
import marketPlace.services.domain.ProductDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceDbImplementation implements ProductService {

    private ProductRepository productRepository;


    private Domain_ModelMapper domain_modelMapper;

    private Domain_EntityMapper domain_entityMapper;

    @Autowired
    public ProductServiceDbImplementation(ProductRepository productRepository, Domain_ModelMapper domain_modelMapper, Domain_EntityMapper domain_entityMapper) {
        this.productRepository = productRepository;
        this.domain_modelMapper = domain_modelMapper;
        this.domain_entityMapper = domain_entityMapper;
    }

    public String saveProduct(ProductModel productModel) {
        ProductDomain productDomain = domain_modelMapper.productModelToProductDomain(productModel);
        productRepository.save(domain_entityMapper.productDomainToNewProduct(productDomain));
        return "Saved Product Successfully";
    }

    public ProductModel getProductById(int productId) {
        Product starter = productRepository.findById(productId).get();
        ProductDomain productDomain = domain_entityMapper.productToProductDomain(starter);
        return domain_modelMapper.productDomainToProductModel(productDomain);
    }

    @Override
    public List<ProductModel> getAllProducts() {
        Iterable<Product> iterableProducts = productRepository.findAll();
        List<ProductDomain> products = new ArrayList<>();
        iterableProducts.forEach((product) -> products.add(domain_entityMapper.productToProductDomain(product)));
        return products.stream().map(productDomain -> domain_modelMapper.productDomainToProductModel(productDomain)).collect(Collectors.toList());
    }

    @Override
    public String deleteProduct(int productId) {
        productRepository.deleteById(productId);
        return "Successful delete";
    }

    @Override
    public String modifyProduct(int productId, ProductModel productModel) {
        ProductDomain oldDomain = domain_entityMapper.productToProductDomain(productRepository.findById(productId).get());
        ProductDomain newDomain = domain_modelMapper.productModelToProductDomain(productModel);

        if (!newDomain.getName().equals(oldDomain.getName()))
            oldDomain.setName(newDomain.getName());

        if (!newDomain.getDescription().equals(oldDomain.getDescription()))
            oldDomain.setDescription(newDomain.getDescription());

        if (newDomain.getPrice() != oldDomain.getPrice())
            oldDomain.setPrice(newDomain.getPrice());

        if (newDomain.getStock() != oldDomain.getStock())
            oldDomain.setStock(newDomain.getStock());

        if (!newDomain.getCategory().equals(oldDomain.getCategory()))
            oldDomain.setCategory(newDomain.getCategory());

        if (!newDomain.getSeller().equals(oldDomain.getSeller()))
            oldDomain.setSeller(newDomain.getSeller());

        productRepository.save(domain_entityMapper.productDomainToProduct(oldDomain));
        return String.format("Successful modified product with %s id ", productId);
    }
}
