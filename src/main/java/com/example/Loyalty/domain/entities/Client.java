package com.example.Loyalty.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "clients")
public class Client extends BaseModel {

    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "enterprise_id", referencedColumnName = "id")
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    private Enterprise enterprise;

}
