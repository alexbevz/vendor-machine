package ru.bevz.vendor_machine.component.finance.physical;

import ru.bevz.vendor_machine.component.finance.Token;
import ru.bevz.vendor_machine.component.finance.exception.InvalidTokenException;

public interface TokenAccepter {

    void acceptToken(Token token)
        throws InvalidTokenException;
}
