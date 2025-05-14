package com.example.demo1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.demo1.model.OrderDetail;
import com.example.demo1.repository.IOrderDetailRepository;
import com.example.demo1.service.impl.OrderDetailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderDetailServiceImplTest {

    private IOrderDetailRepository orderDetailRepository;
    private OrderDetailServiceImpl orderDetailService;

    @BeforeEach
    void setup() {
        orderDetailRepository = mock(IOrderDetailRepository.class);
        orderDetailService = new OrderDetailServiceImpl(orderDetailRepository);
    }

    // Test 1: Guardar OrderDetail → retorna OrderDetail guardado
    @Test
    void saveOrderDetail_ReturnsSavedOrderDetail() {
        OrderDetail detail = new OrderDetail();
        detail.setQuantity(2);
        detail.setPrice(BigDecimal.valueOf(19.99));
        // Para simplificar, dejamos en null las relaciones (order y product)
        detail.setOrder(null);
        detail.setProduct(null);

        OrderDetail savedDetail = new OrderDetail();
        savedDetail.setId(1L);
        savedDetail.setQuantity(detail.getQuantity());
        savedDetail.setPrice(detail.getPrice());
        savedDetail.setOrder(detail.getOrder());
        savedDetail.setProduct(detail.getProduct());

        when(orderDetailRepository.save(detail)).thenReturn(savedDetail);

        OrderDetail result = orderDetailService.save(detail);
        assertNotNull(result, "El detalle del pedido guardado no debe ser nulo");
        assertEquals(1L, result.getId(), "El ID del detalle no coincide");
        assertEquals(2, result.getQuantity(), "La cantidad no coincide");
        assertEquals(BigDecimal.valueOf(19.99), result.getPrice(), "El precio no coincide");

        verify(orderDetailRepository, times(1)).save(detail);
    }

    // Test 2: Buscar OrderDetail por ID existente → retorna OrderDetail
    @Test
    void findById_ExistingOrderDetail_ReturnsOrderDetail() {
        Long detailId = 1L;
        OrderDetail detail = new OrderDetail();
        detail.setId(detailId);
        detail.setQuantity(3);
        detail.setPrice(BigDecimal.valueOf(29.99));

        when(orderDetailRepository.findById(detailId)).thenReturn(Optional.of(detail));

        Optional<OrderDetail> result = orderDetailService.findById(detailId);
        assertTrue(result.isPresent(), "El detalle del pedido no fue encontrado");
        assertEquals(detailId, result.get().getId(), "El ID del detalle no coincide");
    }

    // Test 3: Consultar todos los OrderDetails → retorna lista de detalles
    @Test
    void findAll_ReturnsListOfOrderDetails() {
        OrderDetail d1 = new OrderDetail(1L, 2, BigDecimal.valueOf(19.99), null, null);
        OrderDetail d2 = new OrderDetail(2L, 1, BigDecimal.valueOf(9.99), null, null);

        when(orderDetailRepository.findAll()).thenReturn(Arrays.asList(d1, d2));

        List<OrderDetail> details = orderDetailService.findAll();
        assertEquals(2, details.size(), "El tamaño de la lista de detalles no coincide");
    }

    // Test 4: Actualizar OrderDetail existente → retorna OrderDetail actualizado
    @Test
    void updateOrderDetail_ExistingOrderDetail_UpdatesOrderDetail() {
        Long detailId = 1L;
        OrderDetail existingDetail = new OrderDetail();
        existingDetail.setId(detailId);
        existingDetail.setQuantity(2);
        existingDetail.setPrice(BigDecimal.valueOf(19.99));

        OrderDetail updateInfo = new OrderDetail();
        updateInfo.setQuantity(3);
        updateInfo.setPrice(BigDecimal.valueOf(24.99));

        when(orderDetailRepository.findById(detailId)).thenReturn(Optional.of(existingDetail));

        // Simulamos que al guardar se retorna el objeto actualizado
        OrderDetail updatedDetail = new OrderDetail();
        updatedDetail.setId(detailId);
        updatedDetail.setQuantity(updateInfo.getQuantity());
        updatedDetail.setPrice(updateInfo.getPrice());
        updatedDetail.setOrder(existingDetail.getOrder());
        updatedDetail.setProduct(existingDetail.getProduct());

        when(orderDetailRepository.save(existingDetail)).thenReturn(updatedDetail);

        OrderDetail result = orderDetailService.update(detailId, updateInfo);
        assertEquals(3, result.getQuantity(), "La cantidad no se actualizó correctamente");
        assertEquals(BigDecimal.valueOf(24.99), result.getPrice(), "El precio no se actualizó correctamente");
    }

    // Test 5: Eliminar OrderDetail con ID válido → elimina correctamente
    @Test
    void deleteOrderDetail_ValidId_DeletesOrderDetail() {
        Long detailId = 1L;
        doNothing().when(orderDetailRepository).deleteById(detailId);

        orderDetailService.deleteById(detailId);
        verify(orderDetailRepository, times(1)).deleteById(detailId);
    }
}
