package ru.bevz.vendor_machine.simple_model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import ru.bevz.vendor_machine.component.Inventory;
import ru.bevz.vendor_machine.component.Product;
import ru.bevz.vendor_machine.component.VendorMachineController;
import ru.bevz.vendor_machine.component.VendorMachineFactory;
import ru.bevz.vendor_machine.component.finance.BoxOffice;
import ru.bevz.vendor_machine.component.finance.Token;
import ru.bevz.vendor_machine.component.finance.physical.TokenStorage;
import ru.bevz.vendor_machine.component.finance.virtual.MoneyCreditor;
import ru.bevz.vendor_machine.component.finance.virtual.MoneyDepositor;
import ru.bevz.vendor_machine.component.finance.virtual.PaymentTerminal;

public class SimpleVendorMachineController implements VendorMachineController {

    private final VendorMachineFactory vendorMachineFactory = FactoryProvider.getInstance()
        .getVendorMachineFactory();

    private final PaymentTerminal paymentTerminal = vendorMachineFactory.getPaymentTerminal();
    private final BoxOffice boxOffice = vendorMachineFactory.getBoxOffice();
    private final TokenStorage changeTokenStorage = vendorMachineFactory.getChangeTokenStorage();
    private final MoneyDepositor businessAccount = vendorMachineFactory.getBusinessAccount();

    private final Inventory inventory = vendorMachineFactory.getInventory();

    @Override
    public void putTokenToBoxOffice(Token token) {
        boxOffice.acceptToken(token);
    }

    public Product addProductToCart(Product product) {
        boxOffice.addProductToCart(product);
        return product;
    }

    public BigDecimal getProductPrice(Product product) {
        return product.getPrice();
    }

    public List<Product> payByCard(MoneyCreditor moneyCreditor) {
        returnMoneyTokens();
        paymentTerminal.pay(moneyCreditor, businessAccount, boxOffice.getAmountToPaid());

        List<Product> products = emptyCart();

        for (Product product : products) {
            inventory.dropProduct(product);
        }

        return products;
    }

    public List<Product> payByCash() {
        boxOffice.pay();
        returnMoneyTokens();
        List<Product> products = emptyCart();

        for (Product product : products) {
            inventory.dropProduct(product);
        }

        return products;
    }

    public BigDecimal getAmountToPaid() {
        return boxOffice.getAmountToPaid();
    }

    public BigDecimal getClientCashBalance() {
        return boxOffice.getCurrentClientBalance();
    }

    public void cancelTransaction() {
        emptyCart();
        returnMoneyTokens();
    }

    public List<Token> takeMoneyFromChangeCashStorage() {
        BigDecimal amount = changeTokenStorage.getTokens()
            .stream()
            .map(Token::getNominalValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return changeTokenStorage.throwTokens(amount);
    }

    private void returnMoneyTokens() {
        for (Token token : boxOffice.throwTokens(boxOffice.getCurrentClientBalance())) {
            changeTokenStorage.acceptToken(token);
        }
    }

    private List<Product> emptyCart() {
        List<Product> products = new ArrayList<>();
        for (Product product : boxOffice.getProducts()) {
            products.add(product);
            boxOffice.removeProductFromCart(product);
        }
        return products;
    }

}
