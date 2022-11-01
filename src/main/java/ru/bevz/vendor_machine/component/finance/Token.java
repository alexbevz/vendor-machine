package ru.bevz.vendor_machine.component.finance;

import java.math.BigDecimal;

public interface Token {

    BigDecimal getNominalValue();

    String getCurrency();
}
