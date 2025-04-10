package com.example.Loyalty.payloads;

import com.example.Loyalty.domain.entities.Client;
import com.example.Loyalty.domain.entities.Enterprise;
import lombok.Data;

@Data
public class ClientControllerCreateRequest {
    Client client;
    Enterprise enterprise;
}
