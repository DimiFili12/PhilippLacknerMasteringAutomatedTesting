package com.plcoding.testingcourse.part6

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrderServiceTest {
    private lateinit var orderService: OrderService
    private lateinit var auth: FirebaseAuth
    private lateinit var emailClient: EmailClient

    @BeforeEach
    fun setUp() {
        val firebaseUser = mockk<FirebaseUser>(relaxed = true) {
            every { isAnonymous } returns false
        }
        auth = mockk(relaxed = true) {
            every { currentUser } returns firebaseUser
        }
        emailClient = mockk(relaxed = true)
        orderService = OrderService(
            auth = auth,
            emailClient = emailClient
        )
    }

    @Test
    fun `Send email to authorized clients, with correct recipient and product name`() {
        val customer  = Customer(
            id = 1,
            email = "test@example.com"
        )

        orderService.placeOrder(
            customerEmail = customer.email,
            productName = "Book"
        )

        verify {
            emailClient.send(
                email = Email(
                    subject = "Order Confirmation",
                    content = "Thank you for your order of Book.",
                    recipient = customer.email
                )
            )
        }
    }
}