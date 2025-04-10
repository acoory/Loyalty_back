package com.example.Loyalty.domain.entities;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "enterprises")
public class Enterprise extends BaseModel {

    private String name;

}
