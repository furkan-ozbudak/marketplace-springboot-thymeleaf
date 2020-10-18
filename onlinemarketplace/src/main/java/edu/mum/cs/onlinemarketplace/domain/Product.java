package edu.mum.cs.onlinemarketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private  String description;
    private double price;
    private int quantiy;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private User seller;
    private Integer points;
    private LocalDate createDate;
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "product")
    private List<Review>reviewList;
    private String productImage;
    private Boolean enable;
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                '}';
    }
}
