package ru.astondevs.asber.infoservice.service;

/**
 * Performs scheduled calls to outer api and sends new data to kafka listeners.
 */
public interface ExchangeRateUpdater {

    /**
     * Method that sends exchange rate to Kafka.
     */
    void updateExchangeRates();

}
