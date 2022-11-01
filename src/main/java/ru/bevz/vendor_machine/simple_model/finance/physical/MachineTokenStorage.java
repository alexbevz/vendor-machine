package ru.bevz.vendor_machine.simple_model.finance.physical;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import ru.bevz.vendor_machine.component.finance.Token;
import ru.bevz.vendor_machine.component.finance.exception.InsufficientFundsException;
import ru.bevz.vendor_machine.component.finance.exception.InvalidTokenException;
import ru.bevz.vendor_machine.component.finance.exception.NotContainTokensCombinationException;
import ru.bevz.vendor_machine.component.finance.physical.TokenStorage;
import ru.bevz.vendor_machine.simple_model.finance.TokenUtil;

public class MachineTokenStorage implements TokenStorage {

    private final Map<Token, AtomicInteger> rubMoneyTokenQuantityMap = new HashMap<>();

    private BigDecimal amount = new BigDecimal(0);

    @Override
    public void acceptToken(Token token) throws InvalidTokenException {
        AtomicInteger quantity = rubMoneyTokenQuantityMap.get(token);
        if (quantity != null) {
            quantity.incrementAndGet();
        } else {
            rubMoneyTokenQuantityMap.put(token, new AtomicInteger(1));
        }
        amount = amount.add(token.getNominalValue());
    }

    @Override
    public List<Token> getTokens() {
        return TokenUtil.collectRubMoneyTokensToList(rubMoneyTokenQuantityMap);
    }

    @Override
    public List<Token> throwTokens(final BigDecimal amount)
        throws InsufficientFundsException, NotContainTokensCombinationException {
        if (this.amount.compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }

        Map<Token, AtomicInteger> tokensCombination = TokenUtil.getRubMoneyTokensCombinationByAmount(
            rubMoneyTokenQuantityMap, amount);

        for (Entry<Token, AtomicInteger> entry : tokensCombination.entrySet()) {
            AtomicInteger quantity = rubMoneyTokenQuantityMap.get(entry.getKey());
            quantity.set(quantity.get() - entry.getValue().get());
        }

        return TokenUtil.collectRubMoneyTokensToList(tokensCombination);
    }


}
