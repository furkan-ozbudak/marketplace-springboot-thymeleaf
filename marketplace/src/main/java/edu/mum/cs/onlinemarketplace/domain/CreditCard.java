package edu.mum.cs.onlinemarketplace.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Size(min=5, max=5, message="Length of the expiration date should be 5.")
    private String validDate;
    @NotNull
    private Long number;

    @OneToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "creditCard")
    private User user;
}
