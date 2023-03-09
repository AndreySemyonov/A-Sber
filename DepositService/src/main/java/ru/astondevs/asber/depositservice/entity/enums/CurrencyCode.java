package ru.astondevs.asber.depositservice.entity.enums;

import ru.astondevs.asber.depositservice.entity.Account;
import ru.astondevs.asber.depositservice.entity.CardProduct;
import ru.astondevs.asber.depositservice.entity.Operation;
import ru.astondevs.asber.depositservice.entity.Product;

/**
 * Enum that uses in {@link Account}, {@link Product}, {@link CardProduct}, {@link Operation}.
 */
public enum CurrencyCode {
    EUR,
    RUB,
    USD
}
