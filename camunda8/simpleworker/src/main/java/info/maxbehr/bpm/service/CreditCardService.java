package info.maxbehr.bpm.service;

public class CreditCardService {

    public String chargeCreditCard(final String reference,
                                 final Double amount,
                                 final String cardNumber,
                                 final String cardExpiryDate,
                                 final String cardCvc) {
        System.out.println("Starting transaction " + reference);
        System.out.println("Card Number " + cardNumber);
        System.out.println("Card Expiry Date " + cardExpiryDate);
        System.out.println("Card CVC " + cardCvc);
        System.out.println("Amount " + amount);

        final String confirmation = String.valueOf(System.currentTimeMillis());
        System.out.println("Successful Transaction " + confirmation);
        return confirmation;
    }
}
