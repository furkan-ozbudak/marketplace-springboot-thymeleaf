package edu.mum.cs.onlinemarketplace.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty()
    @Size(min=6, max = 50)
    private String description;
    private String status;
    @NotNull
    private LocalDate createDate;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private User user;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Product product;
}
