package ru.bevz.vendor_machine.component.finance.virtual;

import java.math.BigDecimal;
import ru.bevz.vendor_machine.component.finance.exception.InsufficientFundsException;

public interface MoneyCreditor {

    BigDecimal credit(BigDecimal value) throws InsufficientFundsException;

}
