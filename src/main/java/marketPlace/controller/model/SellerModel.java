package marketPlace.controller.model;

import marketPlace.repository.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SellerModel {
    private Integer sellerId;

    private String firstName;

    private String lastName;

    private List<ProductModel> products = new CopyOnWriteArrayList<>();

    private String email;

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProductModel> getProducts() {
        return new ArrayList<>(products);
    }

    public void addManyProducts(List<ProductModel> products) {
        products.addAll(products);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
