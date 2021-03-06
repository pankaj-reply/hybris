package com.worldpay.facades.order.converters.populators;

import de.hybris.platform.commercefacades.user.converters.populator.AddressReversePopulator;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * {@inheritDoc}
 */
public class WorldpayAddressEmailReversePopulator extends AddressReversePopulator {

    /**
     * {@inheritDoc}
     */
    @Override
    public void populate(final AddressData source, final AddressModel target) throws ConversionException {
        super.populate(source, target);
        target.setEmail(source.getEmail());
    }
}
