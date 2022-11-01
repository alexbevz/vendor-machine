package ru.bevz.vendor_machine.component.finance.physical;

import java.util.List;
import ru.bevz.vendor_machine.component.finance.Token;

public interface TokenStorage extends TokenAccepter, TokenThrower {

    List<Token> getTokens();
}
