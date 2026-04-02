package com.plcoding.testingcourse.shopping.domain

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource


internal class ShoppingCartTest {

    private lateinit var cart: ShoppingCart

    @BeforeEach
    fun setUp() {
        cart = ShoppingCart()
    }

    @Test
    fun `Add multiple products, total price is correct`() {
        // GIVEN
        val product = Product(
            id = 0,
            name = "ice-cream",
            price = 5.0
        )
        cart.addProduct(product = product, quantity = 4)

        // ACTION
        val priceSum = cart.getTotalCost()

        // ASSERTION
        assertThat(priceSum).isEqualTo(20.0)
    }

    @RepeatedTest(100)
    fun `Add product with negative quantity, throws Exception`() {
        val product = Product(
            id = 0,
            name = "ice-cream",
            price = 5.0
        )
        assertFailure {
            cart.addProduct(product = product, quantity = -1)
        }
    }

    @ParameterizedTest
    @ValueSource(
        ints = [1, 2, 3, 4, 5]
    )
    fun `Add multiple products, total price is correct - ValueSource`(quantity: Int) {
        // GIVEN
        val product = Product(
            id = 0,
            name = "ice-cream",
            price = 5.0
        )
        cart.addProduct(product = product, quantity = quantity)

        // ACTION
        val priceSum = cart.getTotalCost()

        // ASSERTION
        assertThat(priceSum).isEqualTo(quantity * product.price)
    }

    @ParameterizedTest
    @CsvSource(
        "3, 15.0",
        "0, 0.0",
        "6, 30.0",
        "20, 100.0"
    )
    fun `Add multiple products, total price is correct - CsvSource`(
        quantity: Int,
        expectedPriceSum: Double
    ) {
        // GIVEN
        val product = Product(
            id = 0,
            name = "ice-cream",
            price = 5.0
        )
        cart.addProduct(product = product, quantity = quantity)

        // ACTION
        val priceSum = cart.getTotalCost()

        // ASSERTION
        assertThat(priceSum).isEqualTo(quantity * product.price)
    }

    // instead of testing the private fun isValidProduct(),
    // we can test a public fun that makes use of the private (like the following).
    // otherwise, we have to make the private -> public
    // and use @OpenForTesting
    // but not suggested
    @Test
    fun `isValidProduct returns invalid for not existing product`() {
        val product = Product(
            id = 1234,
            name = "ice-cream",
            price = 5.0
        )
        cart.addProduct(product = product, quantity = 4)

        val totalPriceSum = cart.getTotalCost()

        assertThat(totalPriceSum).isEqualTo(0.0)
    }
}