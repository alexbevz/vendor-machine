package ru.bevz.vendor_machine.simple_model.finance.virtual;

import java.math.BigDecimal;
import ru.bevz.vendor_machine.component.finance.virtual.MoneyDepositor;

public class BusinessAccount implements MoneyDepositor {

    private BigDecimal amount = new BigDecimal(0);


    @Override
    public void deposit(BigDecimal value) {
        amount = amount.add(value);
    }
}
