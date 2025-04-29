package com.rohlikgroup.casestudy.mapper;

import com.rohlikgroup.casestudy.dto.ProductDto;
import com.rohlikgroup.casestudy.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product map(ProductDto productDto);

    ProductDto map(Product product);
}
