package marketPlace.services.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SellerDomain {
    private Integer sellerId;

    private String firstName;

    private String lastName;

    private List<ProductDomain> products = new ArrayList<>();

    private String email;

    private List<Integer> ratings = new CopyOnWriteArrayList<>();

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

    public List<ProductDomain> getProducts() {
        return new ArrayList<>(products);
    }

    public void addManyProducts(List<ProductDomain> products) {
       this.products.addAll(products);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getRatings() {
        return new ArrayList<>(ratings);
    }

    public void addRatings(Integer rating) {
        this.ratings.add(rating);
    }

    public void addManyRatings(List<Integer> ratings) {
        this.ratings.addAll(ratings);
    }
}
