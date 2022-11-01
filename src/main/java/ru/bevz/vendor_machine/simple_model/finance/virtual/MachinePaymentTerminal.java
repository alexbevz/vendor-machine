package ru.bevz.vendor_machine.simple_model.finance.virtual;

import java.math.BigDecimal;
import ru.bevz.vendor_machine.component.finance.exception.InsufficientFundsException;
import ru.bevz.vendor_machine.component.finance.virtual.MoneyCreditor;
import ru.bevz.vendor_machine.component.finance.virtual.MoneyDepositor;
import ru.bevz.vendor_machine.component.finance.virtual.PaymentTerminal;

public class MachinePaymentTerminal implements PaymentTerminal {

    @Override
    public void pay(MoneyCreditor creditor, MoneyDepositor depositor, BigDecimal value)
        throws InsufficientFundsException {
        depositor.deposit(creditor.credit(value));
    }
}
