package ru.astondevs.asber.moneytransferservice.config;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import ru.astondevs.asber.moneytransferservice.service.DepositServiceClient;

@Configuration
public class MockConfig {

    @MockBean
    private DepositServiceClient depositServiceClient;
}
