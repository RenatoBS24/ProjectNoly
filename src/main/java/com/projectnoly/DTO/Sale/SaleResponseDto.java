package com.projectnoly.DTO.Sale;

import java.time.LocalDateTime;
import java.util.List;

public record SaleResponseDto(Long id, LocalDateTime date, Double total, List<PaymentMethodDto> paymentMethodDtoList, Long idEmployee, String employeeName) {
}
