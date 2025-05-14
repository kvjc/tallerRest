package com.example.demo1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.demo1.model.Order;
import com.example.demo1.repository.IOrderRepository;
import com.example.demo1.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceImplTest {

    private IOrderRepository orderRepository;
    private OrderServiceImpl orderService;

    @BeforeEach
    void setup() {
        orderRepository = mock(IOrderRepository.class);
        orderService = new OrderServiceImpl(orderRepository);
    }

    // Test 1: Guardar pedido → retorna pedido guardado
    @Test
    void saveOrder_ReturnsSavedOrder() {
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setStatus("NEW");
        order.setTotalAmount(BigDecimal.valueOf(100.0));
        // Para simplificar, se deja null el usuario y los detalles del pedido
        order.setUser(null);
        order.setOrderDetails(null);

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setOrderDate(order.getOrderDate());
        savedOrder.setStatus(order.getStatus());
        savedOrder.setTotalAmount(order.getTotalAmount());
        savedOrder.setUser(order.getUser());
        savedOrder.setOrderDetails(order.getOrderDetails());

        when(orderRepository.save(order)).thenReturn(savedOrder);

        Order result = orderService.save(order);
        assertNotNull(result, "El pedido guardado no debe ser nulo");
        assertEquals(1L, result.getId(), "El ID del pedido no coincide");
        assertEquals("NEW", result.getStatus(), "El estado del pedido no coincide");

        verify(orderRepository, times(1)).save(order);
    }

    // Test 2: Buscar pedido por ID existente → retorna pedido
    @Test
    void findById_ExistingOrder_ReturnsOrder() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setOrderDate(LocalDate.now());
        order.setStatus("NEW");
        order.setTotalAmount(BigDecimal.valueOf(100.0));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.findById(orderId);
        assertTrue(result.isPresent(), "El pedido no fue encontrado");
        assertEquals(orderId, result.get().getId(), "El ID del pedido no coincide");
    }

    // Test 3: Consultar todos los pedidos → retorna lista de pedidos
    @Test
    void findAll_ReturnsListOfOrders() {
        Order o1 = new Order(1L, LocalDate.now(), "NEW", BigDecimal.valueOf(100.0), null, null);
        Order o2 = new Order(2L, LocalDate.now(), "PROCESSING", BigDecimal.valueOf(150.0), null, null);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(o1, o2));

        List<Order> orders = orderService.findAll();
        assertEquals(2, orders.size(), "El tamaño de la lista de pedidos no coincide");
    }

    // Test 4: Actualizar pedido existente → retorna pedido actualizado
    @Test
    void updateOrder_ExistingOrder_UpdatesOrder() {
        Long orderId = 1L;
        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        existingOrder.setOrderDate(LocalDate.now());
        existingOrder.setStatus("NEW");
        existingOrder.setTotalAmount(BigDecimal.valueOf(100.0));

        Order updateInfo = new Order();
        updateInfo.setStatus("COMPLETED");
        updateInfo.setTotalAmount(BigDecimal.valueOf(200.0));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

        Order updatedOrder = new Order();
        updatedOrder.setId(orderId);
        updatedOrder.setOrderDate(existingOrder.getOrderDate());
        updatedOrder.setStatus("COMPLETED");
        updatedOrder.setTotalAmount(BigDecimal.valueOf(200.0));

        when(orderRepository.save(existingOrder)).thenReturn(updatedOrder);

        Order result = orderService.update(orderId, updateInfo);
        assertEquals("COMPLETED", result.getStatus(), "El estado del pedido no se actualizó correctamente");
        assertEquals(BigDecimal.valueOf(200.0), result.getTotalAmount(), "El monto total no se actualizó correctamente");
    }

    // Test 5: Eliminar pedido con ID válido → elimina correctamente
    @Test
    void deleteOrder_ValidId_DeletesOrder() {
        Long orderId = 1L;
        doNothing().when(orderRepository).deleteById(orderId);

        orderService.deleteById(orderId);
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
