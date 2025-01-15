package com.example.Loyalty.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "client")
public class Client {

        @Id
        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private String enterpriseId;

    public Client(String firstName, String lastName, String email, String enterpriseId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enterpriseId = enterpriseId;
        this.id = UUID.randomUUID().toString();
    }

        public String getId() {
            return id;
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

        public String getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(String enterpriseId) {
            this.enterpriseId = enterpriseId;
        }
}
