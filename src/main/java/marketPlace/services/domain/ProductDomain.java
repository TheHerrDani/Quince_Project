package marketPlace.services.domain;

import marketPlace.repository.Entity.ProductCategory;
import marketPlace.repository.Entity.Seller;
import org.springframework.stereotype.Component;


@Component
public class ProductDomain implements Comparable<ProductDomain> {

    private Integer productId;

    private String name;

    private String description;

    private double price;

    private ProductCategory category;

    private Seller seller;

    private int stock;

    private int numberOfSales;

    private int numberOfViewed;

    private double salesValue;

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getNumberOfSales() {
        return numberOfSales;
    }

    public void setNumberOfSales(int numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    public int getNumberOfViewed() {
        return numberOfViewed;
    }

    public void setNumberOfViewed(int numberOfViewed) {
        this.numberOfViewed = numberOfViewed;
    }

    public double getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(double salesValue) {
        this.salesValue = salesValue;
    }

    @Override
    public int compareTo(ProductDomain otherProductDomain) {
        return this.getStock() - otherProductDomain.getStock();
    }
}
