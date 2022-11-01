package ru.bevz.vendor_machine.simple_model;

import java.math.BigDecimal;
import java.util.Set;
import ru.bevz.vendor_machine.component.Inventory;
import ru.bevz.vendor_machine.component.VendorMachineFactory;
import ru.bevz.vendor_machine.component.finance.BoxOffice;
import ru.bevz.vendor_machine.component.finance.physical.TokenStorage;
import ru.bevz.vendor_machine.component.finance.virtual.MoneyDepositor;
import ru.bevz.vendor_machine.component.finance.virtual.PaymentTerminal;
import ru.bevz.vendor_machine.simple_model.finance.physical.ChangeTokenStorage;
import ru.bevz.vendor_machine.simple_model.finance.physical.MachineBoxOffice;
import ru.bevz.vendor_machine.simple_model.finance.physical.MachineTokenStorage;
import ru.bevz.vendor_machine.simple_model.finance.virtual.BusinessAccount;
import ru.bevz.vendor_machine.simple_model.finance.virtual.MachinePaymentTerminal;

public class SimpleVendorMachineFactory implements VendorMachineFactory {

    private final Set<BigDecimal> acceptedNominalValues = Set.of(
        new BigDecimal(5),
        new BigDecimal(10),
        new BigDecimal(50),
        new BigDecimal(100),
        new BigDecimal(200),
        new BigDecimal(500)
    );
    private final BoxOffice boxOffice = new MachineBoxOffice();
    private final TokenStorage machineTokenStorage = new MachineTokenStorage();
    private final TokenStorage changeTokenStorage = new ChangeTokenStorage();

    private final MoneyDepositor businessAccount = new BusinessAccount();

    private final PaymentTerminal paymentTerminal = new MachinePaymentTerminal();

    private final Inventory productInventory = new ProductInventory();

    private final String currency = "RUB";

    @Override
    public Set<BigDecimal> getAcceptedNominalValues() {
        return acceptedNominalValues;
    }

    @Override
    public BoxOffice getBoxOffice() {
        return boxOffice;
    }

    @Override
    public TokenStorage getMachineTokenStorage() {
        return machineTokenStorage;
    }

    @Override
    public TokenStorage getChangeTokenStorage() {
        return changeTokenStorage;
    }

    @Override
    public MoneyDepositor getBusinessAccount() {
        return businessAccount;
    }

    @Override
    public PaymentTerminal getPaymentTerminal() {
        return paymentTerminal;
    }

    @Override
    public Inventory getInventory() {
        return productInventory;
    }

    @Override
    public String getCurrency() {
        return currency;
    }


}
