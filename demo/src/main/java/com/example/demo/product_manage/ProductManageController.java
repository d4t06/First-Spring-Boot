package com.example.demo.product_manage;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.color.ColorService;
import com.example.demo.color.dto.ColorDto;
import com.example.demo.combine.CombineService;
import com.example.demo.combine.dto.CombineDto;
import com.example.demo.default_storage.DefaultStorageService;
import com.example.demo.default_storage.dto.DefaultStorageDto;
import com.example.demo.description.ProductDescService;
import com.example.demo.description.dto.ProductDescDto;
import com.example.demo.product_attribute.ProductAttributeService;
import com.example.demo.product_attribute.dto.ProductAttributeDto;
import com.example.demo.storage.StorageService;
import com.example.demo.storage.dto.DefaultStorageCombineDto;
import com.example.demo.storage.dto.StorageDto;
import com.example.demo.system.MyResponse;

@RestController
@RequestMapping("/product-management")
public class ProductManageController {

    private final StorageService storageService;
    private final ColorService colorService;
    private final CombineService combineService;
    private final ProductAttributeService productAttributeService;
    private final ProductDescService productDescService;
    private final DefaultStorageService defaultStorageService;

    public ProductManageController(StorageService storageService,
            ColorService colorService,
            ProductAttributeService productAttributeService,
            ProductDescService productDescService,
            DefaultStorageService defaultStorageService,
            CombineService combineService) {
        this.colorService = colorService;
        this.storageService = storageService;
        this.combineService = combineService;
        this.productAttributeService = productAttributeService;
        this.productDescService = productDescService;
        this.defaultStorageService = defaultStorageService;
    }

    // // *** /products
    // router.post("/products", ManageProductController.addOne);
    // router.put("/products/:id", ManageProductController.updateOne);
    // router.delete("/products/:id", ManageProductController.deleteOne);

    // >>>>>>>>>>>>>>>
    @PostMapping("/attributes")
    public MyResponse addAttribute(@RequestBody ProductAttributeDto body) {
        return this.productAttributeService.create(body);
    }

    @PutMapping("/attributes/{id}")
    public MyResponse updateAttribute(
            @PathVariable Long id, @RequestBody ProductAttributeDto body) {
        return this.productAttributeService.update(id, body);
    }

    // >>>>>>>>>>>>>>>

    @PostMapping("/colors")
    public MyResponse addColor(@RequestBody ColorDto body) {
        return this.colorService.add(body);
    }

    @PutMapping("/colors/{id}")
    public MyResponse updateColor(
            @PathVariable Long id, @RequestBody ColorDto colorDto) {
        this.colorService.update(id, colorDto);
        return new MyResponse(true, "edit color successful", 200);
    }

    @DeleteMapping("/colors/{id}")
    public MyResponse deleteColor(@PathVariable Long id) {
        this.colorService.delete(id);
        return new MyResponse(true, "delete color successful", 200);
    }

    // >>>>>>>>>>>>>>>
    @PutMapping("/descriptions/{productId}")
    public MyResponse updateDescription(@RequestBody ProductDescDto body, @PathVariable Long productId) {
        return this.productDescService.update(productId, body);
    }

    // >>>>>>>>>>>>>>>
    @PostMapping("/storages")
    public MyResponse addStorage(@RequestBody StorageDto body) {
        return this.storageService.add(body);
    }

    @PutMapping("/storages/{id}")
    public MyResponse updateStorage(
            @PathVariable Long id,
            @RequestBody StorageDto body) {
        this.storageService.update(id, body);
        return new MyResponse(true, "update storage successful", 200);
    }

    @DeleteMapping("/storages/{id}")
    public MyResponse deleteStorage(@PathVariable Long id) {
        this.storageService.delete(id);
        return new MyResponse(true, "delete storage successful", 200);
    }

    // >>>>>>>>>>>>>>>
    @PutMapping("/combines/{id}")
    public MyResponse updateCombine(@PathVariable Long id, @RequestBody CombineDto combineDto) {
        return this.combineService.update(id, combineDto);
    }

    // >>>>>>>>>>>>>>>
    @PutMapping("/default-combines/{storageId}")
    public MyResponse updateDefaultCombine(@PathVariable Long storageId,
            @RequestBody DefaultStorageCombineDto defaultCombine) {
        return this.combineService.updateDefaultCombine(storageId, defaultCombine);
    }

    @PutMapping("/default-storages")
    public MyResponse updateDefaultStorage(
            @RequestBody DefaultStorageDto body) {
        return this.defaultStorageService.update(body);
    }

}
