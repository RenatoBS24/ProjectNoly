package com.projectnoly.DTO;

import java.util.List;

public record MenuResponseDto
        (Long id,
         String name,
         String description,
         double price,
         String routeImage,
         Long idCategory,
         List<MenuIngredientDto> menuIngredientDtoList,
         boolean available
        ) {
}
