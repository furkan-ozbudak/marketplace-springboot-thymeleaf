package edu.mum.cs.onlinemarketplace.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    @Size(min=2, max=2, message = "Length of the state should be 2." )
    private String state;
    @Size(min=5, max=5, message = "Length of the ZIP code should be 5." )
    private String zipCode;
    private LocalDate createDate;
    private String status;

}
