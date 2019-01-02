package marketPlace.repository.Entity;

import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;

    @NotNull
    @Length(min = 1)
    private String name;

    @Nullable
    private String description;

    @NotNull
    private double price;

    @Nullable
    private ProductCategory category;

    @NotNull
    private int sellerId;

    @NotNull
    private int stock;

    @NotNull
    private int numberOfSales;

    @NotNull
    private int numberOfViewed;

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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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
}