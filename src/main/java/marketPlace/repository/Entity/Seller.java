package marketPlace.repository.Entity;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sellerId;

    @Length(min=1)
    private String firstName;

    @Length(min=1)
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "sellerId")
    private List<Product> products = new CopyOnWriteArrayList<>();

    @Email
    private String email;

    @ElementCollection
    @CollectionTable(
            name="RATING",
            joinColumns=@JoinColumn(name="sellerId")
    )
    @Column(name="Rating")
    private List<Integer> ratings;

    public Integer getSellerId() {
        return sellerId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public boolean addProducts(Product product) {
        return products.add(product);
    }

    public void addManyProducts(List<Product> products){
        this.products.addAll(products);
    }

    public List<Integer> getRatings() {
        return new ArrayList<>(ratings);
    }

    public void addRatings(Integer rating) {
        this.ratings.add(rating);
    }
}