package com.fsse2401.project_harry.api;

import com.fsse2401.project_harry.config.EnvConfig;
import com.fsse2401.project_harry.data.product.domainObject.ProductResponseData;
import com.fsse2401.project_harry.data.product.dto.response.ProductResponseDto;
import com.fsse2401.project_harry.data.product.dto.response.GetAllProductResponseDto;
import com.fsse2401.project_harry.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public/product")
@CrossOrigin({EnvConfig.DEV_BASE_URL, EnvConfig.PROD_S3_BASE_URL, EnvConfig.PROD_BASE_URL})
public class ProductApi {
    private final ProductService productService;

    @Autowired
    public ProductApi(ProductService productservice) {
        this.productService = productservice;
    }

    @GetMapping
    public List<GetAllProductResponseDto> getAllProducts() {
        List<GetAllProductResponseDto> responseDtoList = new ArrayList<>();

        for (ProductResponseData data : productService.getAllProducts()) {
            responseDtoList.add(new GetAllProductResponseDto(data));
        }
        return responseDtoList;
    }

    @GetMapping("/{pid}")
    public ProductResponseDto getProductByPid(@PathVariable Integer pid) {
        ProductResponseData responseData = productService.getByPid(pid);
        return new ProductResponseDto(responseData);
    }
}

