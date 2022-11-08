package ru.bevz.vendor_machine.simple_model.finance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import ru.bevz.vendor_machine.component.finance.Token;
import ru.bevz.vendor_machine.component.finance.exception.NotContainTokensCombinationException;

public class TokenUtil {

    public static List<Token> collectRubMoneyTokensToList(
        Map<Token, AtomicInteger> tokenQuantityMap) {

        List<Token> rubMoneyTokens = new ArrayList<>();
        for (Entry<Token, AtomicInteger> entry : tokenQuantityMap.entrySet()) {
            for (int i = 0; i < entry.getValue().intValue(); i++) {
                rubMoneyTokens.add(entry.getKey());
            }
        }
        return rubMoneyTokens;
    }

    public static Map<Token, AtomicInteger> getRubMoneyTokensCombinationByAmount(
        Map<Token, AtomicInteger> tokenQuantityMap, BigDecimal amount) {
        SortedMap<Token, AtomicInteger> sortedTokenQuantityMap = new TreeMap<>(
            (o1, o2) -> o2.getNominalValue().compareTo(o1.getNominalValue())
        );
        sortedTokenQuantityMap.putAll(tokenQuantityMap);

        Map<Token, AtomicInteger> combinationMap = new HashMap<>();

        for (Entry<Token, AtomicInteger> entry : sortedTokenQuantityMap.entrySet()) {
            int quotient = amount.divide(entry.getKey().getNominalValue(), RoundingMode.DOWN)
                .intValue();
            if (quotient == 0) {
                continue;
            }
            if (quotient >= entry.getValue().get()) {
                quotient = entry.getValue().get();
            }

            amount = amount.subtract(
                entry.getKey().getNominalValue().multiply(new BigDecimal(quotient)));

            combinationMap.put(entry.getKey(), new AtomicInteger(quotient));

        }
        if (amount.compareTo(new BigDecimal(0)) != 0) {
            throw new NotContainTokensCombinationException();
        }

        return combinationMap;
    }

}
