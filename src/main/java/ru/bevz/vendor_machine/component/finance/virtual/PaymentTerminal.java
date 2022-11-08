package ru.bevz.vendor_machine.component.finance.virtual;

import java.math.BigDecimal;
import ru.bevz.vendor_machine.component.finance.exception.InsufficientFundsException;

public interface PaymentTerminal {

    void pay(MoneyCreditor creditor, MoneyDepositor depositor, BigDecimal value)
        throws InsufficientFundsException;
}
