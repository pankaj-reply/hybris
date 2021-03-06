package com.worldpay.facades.order.impl;

import com.worldpay.core.checkout.WorldpayCheckoutService;
import com.worldpay.facades.order.WorldpayPaymentCheckoutFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.order.CartService;

import java.util.Optional;

/**
 * Worldpay checkout facade to ensure Worldpay details are included in correct place
 */
public class DefaultWorldpayPaymentCheckoutFacade implements WorldpayPaymentCheckoutFacade {
    private final CheckoutFacade checkoutFacade;
    private final WorldpayCheckoutService worldpayCheckoutService;
    private final CartService cartService;
    private final CustomerAccountService customerAccountService;

    public DefaultWorldpayPaymentCheckoutFacade(final CheckoutFacade checkoutFacade, final WorldpayCheckoutService worldpayCheckoutService, final CartService cartService, final CustomerAccountService customerAccountService) {
        this.checkoutFacade = checkoutFacade;
        this.worldpayCheckoutService = worldpayCheckoutService;
        this.cartService = cartService;
        this.customerAccountService = customerAccountService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBillingDetails(final AddressData addressData) {
        final CartModel cartModel = getCart();
        if (cartModel != null && addressData != null) {
            Optional.ofNullable(customerAccountService.getAddressForCode((CustomerModel) cartModel.getUser(), addressData.getId()))
                    .ifPresent(addressModel -> worldpayCheckoutService.setPaymentAddress(cartModel, addressModel));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasBillingDetails() {
        final CartModel cartModel = getCart();
        return cartModel != null && cartModel.getPaymentAddress() != null;
    }

    protected CartModel getCart() {
        return checkoutFacade.hasCheckoutCart() ? cartService.getSessionCart() : null;
    }
}
