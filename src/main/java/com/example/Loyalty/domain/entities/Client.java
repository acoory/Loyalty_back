package com.example.Loyalty.domain.entities;

public class Client {

        private int id;
        private String name;
        public Client() {}

        public Client(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
}
