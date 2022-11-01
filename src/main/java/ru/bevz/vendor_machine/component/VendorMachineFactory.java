package ru.bevz.vendor_machine.component;

import java.math.BigDecimal;
import java.util.Set;
import ru.bevz.vendor_machine.component.finance.BoxOffice;
import ru.bevz.vendor_machine.component.finance.physical.TokenStorage;
import ru.bevz.vendor_machine.component.finance.virtual.MoneyDepositor;
import ru.bevz.vendor_machine.component.finance.virtual.PaymentTerminal;

public interface VendorMachineFactory {

    Set<BigDecimal> getAcceptedNominalValues();

    TokenStorage getMachineTokenStorage();

    BoxOffice getBoxOffice();

    TokenStorage getChangeTokenStorage();

    MoneyDepositor getBusinessAccount();

    PaymentTerminal getPaymentTerminal();

    Inventory getInventory();

    String getCurrency();
}
