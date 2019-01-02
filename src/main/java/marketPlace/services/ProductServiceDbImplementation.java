package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.environment.mapper.ProductMapper;
import marketPlace.environment.mapper.SellerMapper;
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

    private ProductMapper productMapper;

    @Autowired
    public ProductServiceDbImplementation(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public String saveProduct(ProductModel productModel) {
        ProductDomain productDomain = productMapper.productModelToProductDomain(productModel);
        productRepository.save(productMapper.productDomainToNewProduct(productDomain));
        return "Saved Product Successfully";
    }

    public ProductModel getProductById(int productId) {
        Product starter = productRepository.findById(productId).get();
        ProductDomain productDomain = productMapper.productToProductDomain(starter);
        return productMapper.productDomainToProductModel(productDomain);
    }

    @Override
    public List<ProductModel> getAllProducts() {
        Iterable<Product> iterableProducts = productRepository.findAll();
        List<ProductDomain> products = new ArrayList<>();
        iterableProducts.forEach((product) -> products.add(productMapper.productToProductDomain(product)));
        return products.stream().map(productDomain -> productMapper.productDomainToProductModel(productDomain)).collect(Collectors.toList());
    }

    @Override
    public String deleteProduct(int productId) {
        productRepository.deleteById(productId);
        return "Successful delete";
    }

    @Override
    public String modifyProduct(int productId, ProductModel productModel) {
        ProductDomain oldDomain = productMapper.productToProductDomain(productRepository.findById(productId).get());
        ProductDomain newDomain = productMapper.productModelToProductDomain(productModel);

        if (newDomain.getName() != null && !newDomain.getName().equals(oldDomain.getName()))
            oldDomain.setName(newDomain.getName());

        if (newDomain.getDescription() != null && !newDomain.getDescription().equals(oldDomain.getDescription()))
            oldDomain.setDescription(newDomain.getDescription());

        if (newDomain.getPrice() < 0 && newDomain.getPrice() != oldDomain.getPrice())
            oldDomain.setPrice(newDomain.getPrice());

        if (newDomain.getStock() < 0 && newDomain.getStock() != oldDomain.getStock())
            oldDomain.setStock(newDomain.getStock());

        if (newDomain.getCategory() != null && !newDomain.getCategory().equals(oldDomain.getCategory()))
            oldDomain.setCategory(newDomain.getCategory());

        if (newDomain.getSeller() != null && !newDomain.getSeller().equals(oldDomain.getSeller()))
            oldDomain.setSeller(newDomain.getSeller());

        productRepository.save(productMapper.productDomainToProduct(oldDomain));
        return String.format("Successful modified product with %s id ", productId);
    }
}
