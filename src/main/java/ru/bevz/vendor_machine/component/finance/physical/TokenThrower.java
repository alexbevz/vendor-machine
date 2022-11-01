package ru.bevz.vendor_machine.component.finance.physical;

import java.math.BigDecimal;
import java.util.List;
import ru.bevz.vendor_machine.component.finance.Token;
import ru.bevz.vendor_machine.component.finance.exception.InsufficientFundsException;
import ru.bevz.vendor_machine.component.finance.exception.NotContainTokensCombinationException;

public interface TokenThrower {

    List<Token> throwTokens(BigDecimal amount)
        throws InsufficientFundsException, NotContainTokensCombinationException;
}
