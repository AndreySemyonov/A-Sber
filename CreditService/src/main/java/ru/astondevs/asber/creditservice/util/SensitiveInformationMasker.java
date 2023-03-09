package ru.astondevs.asber.creditservice.util;

/**
 * Class that helps to mask sensitive information.
 */
public class SensitiveInformationMasker {

    /**
     * Returns masked string
     *
     * @param unmasked string containing sensitive information
     * @return masked string information
     */
    public static String getMaskedString(String unmasked) {
        return String.format("**%s", unmasked.substring(unmasked.length() - 4));
    }

    private SensitiveInformationMasker() {
    }
}
