package ru.bevz.vendor_machine.simple_model.finance.physical;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import ru.bevz.vendor_machine.component.Product;
import ru.bevz.vendor_machine.component.VendorMachineFactory;
import ru.bevz.vendor_machine.component.finance.BoxOffice;
import ru.bevz.vendor_machine.component.finance.Token;
import ru.bevz.vendor_machine.component.finance.exception.InsufficientFundsException;
import ru.bevz.vendor_machine.component.finance.exception.InvalidTokenException;
import ru.bevz.vendor_machine.component.finance.physical.TokenStorage;
import ru.bevz.vendor_machine.simple_model.FactoryProvider;

public class MachineBoxOffice implements BoxOffice {

    private final VendorMachineFactory vendorMachineFactory = FactoryProvider.getInstance()
        .getVendorMachineFactory();
    private final Set<BigDecimal> acceptedNominalValues = vendorMachineFactory.getAcceptedNominalValues();
    private final TokenStorage machineTokenStorage = vendorMachineFactory.getMachineTokenStorage();
    private final String currency = vendorMachineFactory.getCurrency();
    private final List<Product> products = new ArrayList<>();
    private BigDecimal currentClientBalance = BigDecimal.ZERO;

    @Override
    public void acceptToken(Token token)
        throws InvalidTokenException {
        if (isAcceptedToken(token)) {
            machineTokenStorage.acceptToken(token);
            currentClientBalance = currentClientBalance.add(token.getNominalValue());
        } else {
            throw new InvalidTokenException();
        }
    }

    @Override
    public List<Token> throwTokens(BigDecimal amount) {
        List<Token> rubMoneyTokens = machineTokenStorage.throwTokens(amount);
        if (currentClientBalance.compareTo(BigDecimal.ZERO) > 0) {
            if (currentClientBalance.compareTo(amount) <= 0) {
                currentClientBalance = BigDecimal.ZERO;
            } else {
                currentClientBalance = currentClientBalance.subtract(amount);
            }
        }
        return rubMoneyTokens;
    }

    @Override
    public BigDecimal getCurrentClientBalance() {
        return currentClientBalance;
    }

    @Override
    public BigDecimal getAmountToPaid() {
        return products.stream()
            .map(Product::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void pay() throws InsufficientFundsException {
        BigDecimal amountToPaid = getAmountToPaid();
        if (amountToPaid.compareTo(currentClientBalance) <= 0) {
            currentClientBalance = currentClientBalance.subtract(amountToPaid);
            for (Product product : getProducts()) {
                removeProductFromCart(product);
            }
        } else {
            throw new InsufficientFundsException();
        }
    }

    @Override
    public void addProductToCart(Product product) {
        products.add(product);
    }

    @Override
    public void removeProductFromCart(Product product) {
        products.remove(product);
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Set<BigDecimal> getAcceptedNominalValues() {
        return acceptedNominalValues;
    }

    private boolean isAcceptedToken(Token nominalValue) {
        return nominalValue.getCurrency().equals(currency) && acceptedNominalValues.stream()
            .anyMatch(nv -> nv.compareTo(nominalValue.getNominalValue()) == 0);
    }
}
