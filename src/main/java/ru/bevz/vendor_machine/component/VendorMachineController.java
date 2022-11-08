package ru.bevz.vendor_machine.component;

import java.math.BigDecimal;
import java.util.List;
import ru.bevz.vendor_machine.component.finance.Token;
import ru.bevz.vendor_machine.component.finance.virtual.MoneyCreditor;

public interface VendorMachineController {

    void putTokenToBoxOffice(Token token);

    Product addProductToCart(Product product);

    BigDecimal getProductPrice(Product product);

    List<Product> payByCard(MoneyCreditor moneyCreditor);

    List<Product> payByCash();

    BigDecimal getAmountToPaid();

    BigDecimal getClientCashBalance();

    void cancelTransaction();

    List<Token> takeMoneyFromChangeCashStorage();
}
