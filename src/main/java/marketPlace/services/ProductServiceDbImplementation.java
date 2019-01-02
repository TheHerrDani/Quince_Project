package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.environment.mapper.ProductMapper;
import marketPlace.repository.Entity.Product;
import marketPlace.repository.ProductRepository;
import marketPlace.services.domain.ProductDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Synchronization;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceDbImplementation implements ProductService {

    private ProductRepository productRepository;

    private ProductMapper productMapper;

    private SellerServiceDbImplementation sellerServiceDbImplementation;

    @Autowired
    public ProductServiceDbImplementation(ProductRepository productRepository, ProductMapper productMapper, SellerServiceDbImplementation sellerServiceDbImplementation) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.sellerServiceDbImplementation = sellerServiceDbImplementation;
    }

    public String saveProduct(ProductModel productModel) {
        if (sellerServiceDbImplementation.sellerIsNotExist(productModel.getSellerId()))
            throw new IllegalArgumentException("There is none seller with this sellerId");

        ProductDomain productDomain = productMapper.productModelToProductDomain(productModel);
        productDomain.setNumberOfSales(0);
        productDomain.setNumberOfViewed(0);
        productRepository.save(productMapper.productDomainToNewProduct(productDomain));
        return "Saved Product Successfully";
    }

    public ProductModel getProductById(int productId) {
        if (productIsNotExist(productId))
            throw new IllegalArgumentException("There is none product with this productId");

        Product starter = productRepository.findById(productId).get();
        viewProduct(starter);
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
        if (productIsNotExist(productId))
            throw new IllegalArgumentException("There is none product with this productId");
        productRepository.deleteById(productId);
        return "Successful delete";
    }

    @Override
    public String modifyProduct(int productId, ProductModel productModel) {
        if (productIsNotExist(productId))
            throw new IllegalArgumentException("There is none product with this productId");

        Product starterProduct = productRepository.findById(productId).get();

        ProductDomain oldProductDomain = productMapper.productToProductDomain(starterProduct);

        ProductDomain newProductDomain = productMapper.productModelToProductDomain(productModel);

        if (newProductDomain.getName() != null && !newProductDomain.getName().equals(oldProductDomain.getName()))
            oldProductDomain.setName(newProductDomain.getName());

        if (newProductDomain.getDescription() != null && !newProductDomain.getDescription().equals(oldProductDomain.getDescription()))
            oldProductDomain.setDescription(newProductDomain.getDescription());

        if (newProductDomain.getPrice() > 0 && newProductDomain.getPrice() != oldProductDomain.getPrice())
            oldProductDomain.setPrice(newProductDomain.getPrice());

        if (newProductDomain.getStock() > 0 && newProductDomain.getStock() != oldProductDomain.getStock())
            oldProductDomain.setStock(newProductDomain.getStock());

        if (newProductDomain.getCategory() != null && !newProductDomain.getCategory().equals(oldProductDomain.getCategory()))
            oldProductDomain.setCategory(newProductDomain.getCategory());

        productRepository.save(productMapper.productDomainToProduct(oldProductDomain));
        return String.format("Successful modified product with %s id ", productId);
    }

    @Override
    public synchronized String buyingProductById(int productId, int amount) {
        if (productIsNotExist(productId))
            throw new IllegalArgumentException("There is none product with this productId");
        Product product = productRepository.findById(productId).get();
        if (product.getStock() >= amount) {
            product.decreaseStock(amount);
        } else {
            throw new IllegalArgumentException("There is none product in stock");
        }
        product.addNumberOfSales(amount);
        productRepository.save(product);

        return "Product was succesfully bougth";
    }

    // Helper methods
    private void viewProduct(Product product) {
        int newValue = product.getNumberOfViewed() + 1;
        product.setNumberOfViewed(newValue);
        productRepository.save(product);
    }

    public boolean productIsNotExist(int productId) {
        return !productRepository.existsById(productId);
    }
}
