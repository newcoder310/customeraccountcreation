package com.api.accounts.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "service.uri")
public class RestClientConfig {

    private String customerServiceHostPort;

    private String transactionServiceHostPort;
}
