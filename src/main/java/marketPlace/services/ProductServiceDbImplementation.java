package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.environment.mapper.Domain_EntityMapper;
import marketPlace.environment.mapper.Domain_ModelMapper;
import marketPlace.repository.Product;
import marketPlace.repository.ProductRepository;
import marketPlace.services.domain.ProductDomain;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceDbImplementation implements ProductService {

    private ProductRepository productRepository;

    private Domain_ModelMapper service_controllerMapper;

    private Domain_EntityMapper repository_serviceMapper;

    public ProductServiceDbImplementation(ProductRepository productRepository, Domain_ModelMapper service_controllerMapper, Domain_EntityMapper repository_serviceMapper) {
        this.productRepository = productRepository;
        this.service_controllerMapper = service_controllerMapper;
        this.repository_serviceMapper = repository_serviceMapper;
    }

    public String saveProduct(ProductModel productModel) {
        ProductDomain productDomain = service_controllerMapper.productModelToProductDomain(productModel);
        productRepository.save(repository_serviceMapper.productDomainToNewProduct(productDomain));
        return "Saved ProductDomain";
    }

    public ProductModel getProductById(int productId) {
        Product starter = productRepository.findById(productId).get();
        ProductDomain productDomain = repository_serviceMapper.productToProductDomain(starter);
        return service_controllerMapper.productDomainToProductModel(productDomain);
    }

    @Override
    public List<ProductModel> getAllProducts() {
        Iterable<Product> iterableProducts = productRepository.findAll();
        List<ProductDomain> products = new ArrayList<>();
        iterableProducts.forEach((product) -> repository_serviceMapper.productToProductDomain(product));
        return products.stream().map(productDomain -> service_controllerMapper.productDomainToProductModel(productDomain)).collect(Collectors.toList());
    }

    @Override
    public String deleteProduct(int productId) {
        productRepository.deleteById(productId);
        return "Successful delete";
    }
}
