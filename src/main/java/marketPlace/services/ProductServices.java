package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.environment.mapperInterface.Repository_ServicesMapperInterface;
import marketPlace.environment.mapperInterface.Service_ControllerMapperInterface;
import marketPlace.repository.Product;
import marketPlace.repositoryInterface.ProductRepository;
import marketPlace.repositoryInterface.SellerRepository;
import marketPlace.services.domain.ProductDomain;
import marketPlace.servicesInterface.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServices implements ProductServiceInterface {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private Service_ControllerMapperInterface service_controllerMapper;

    @Autowired
    private Repository_ServicesMapperInterface repository_serviceMapper;

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
