package edu.mum.cs.onlinemarketplace.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double totalPrice;

    private boolean active;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinTable
    private List<Product> productList;

    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "cart")
    private User buyer;
    public void calculateTotalPrice(){
        this.totalPrice = productList.stream().mapToDouble(Product::getPrice).sum();
    }

    @Override
    public String toString() {
//        return "Cart{" +
////                "id=" + id +
////                ", totalPrice=" + totalPrice +
////                ", active=" + active +
////                ", productList=" + productList +
////                ", buyer=" + buyer +
////                '}';
        return "price";
    }


}
