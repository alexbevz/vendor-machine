package ru.bevz.vendor_machine.component.finance;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import ru.bevz.vendor_machine.component.Product;
import ru.bevz.vendor_machine.component.finance.exception.InsufficientFundsException;
import ru.bevz.vendor_machine.component.finance.physical.TokenAccepter;
import ru.bevz.vendor_machine.component.finance.physical.TokenThrower;

public interface BoxOffice extends TokenAccepter, TokenThrower {

    BigDecimal getCurrentClientBalance();

    BigDecimal getAmountToPaid();

    void pay() throws InsufficientFundsException;

    void addProductToCart(Product product);

    void removeProductFromCart(Product product);

    List<Product> getProducts();

    Set<BigDecimal> getAcceptedNominalValues();
}
