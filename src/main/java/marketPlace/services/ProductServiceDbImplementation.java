package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.environment.mapper.Domain_EntityMapper;
import marketPlace.environment.mapper.Domain_ModelMapper;
import marketPlace.repository.Entity.Product;
import marketPlace.repository.ProductRepository;
import marketPlace.services.domain.ProductDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
