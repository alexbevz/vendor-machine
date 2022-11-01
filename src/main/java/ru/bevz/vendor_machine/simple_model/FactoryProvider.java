package ru.bevz.vendor_machine.simple_model;

import ru.bevz.vendor_machine.component.VendorMachineFactory;

public class FactoryProvider {

    private final static FactoryProvider INSTANCE = new FactoryProvider();
    private final VendorMachineFactory vendorMachineFactory = new SimpleVendorMachineFactory();


    private FactoryProvider() {

    }

    public static FactoryProvider getInstance() {
        return INSTANCE;
    }

    public VendorMachineFactory getVendorMachineFactory() {
        return vendorMachineFactory;
    }
}
