package ru.bevz.vendor_machine.simple_model.finance;

import java.math.BigDecimal;
import ru.bevz.vendor_machine.component.finance.Token;

public class RubMoneyToken implements Token {

    private final String currency = "RUB";
    private final BigDecimal nominalValue;

    public RubMoneyToken(BigDecimal nominalValue) {
        this.nominalValue = nominalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RubMoneyToken that = (RubMoneyToken) o;

        return nominalValue != null ? nominalValue.compareTo(that.nominalValue) == 0
            : that.nominalValue == null;
    }

    @Override
    public int hashCode() {
        int result = currency.hashCode();
        result = 31 * result + (nominalValue != null ? nominalValue.hashCode() : 0);
        return result;
    }

    @Override
    public BigDecimal getNominalValue() {
        return nominalValue;
    }

    @Override
    public String getCurrency() {
        return currency;
    }
}
