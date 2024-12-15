package com.example.demo.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.demo.color.ColorRepository;
import com.example.demo.color.entity.Color;
import com.example.demo.combine.CombineRepository;
import com.example.demo.combine.entity.Combine;
import com.example.demo.default_storage.DefaultStorageRepository;
import com.example.demo.default_storage.entity.DefaultStorage;
import com.example.demo.description.ProductDescRepository;
import com.example.demo.description.entity.ProductDesc;
import com.example.demo.image.ImageService;
import com.example.demo.image.entity.Image;
import com.example.demo.product.converter.ProductToSearchProductDtoLess;
import com.example.demo.product.dto.JsonProductDto;
import com.example.demo.product.dto.SearchProductDto;
import com.example.demo.product.entity.Product;
import com.example.demo.product_attribute.ProductAttributeRepository;
import com.example.demo.product_attribute.entity.ProductAttribute;
import com.example.demo.product_slider.ProductSliderRepository;
import com.example.demo.product_slider.entity.ProductSlider;
import com.example.demo.slider.SliderService;
import com.example.demo.slider.dto.SliderDto;
import com.example.demo.slider.dto.SliderImageDto;
import com.example.demo.slider.entity.Slider;
import com.example.demo.storage.DefaultStorageCombineRepository;
import com.example.demo.storage.StorageRepository;
import com.example.demo.storage.entity.DefaultStorageCombine;
import com.example.demo.storage.entity.Storage;
import com.example.demo.system.ConvertEng;
import com.example.demo.system.MyResponse;

@Service
public class ProductManagementService {

   Date start = new Date();

   private final ProductRepository productRepository;

   private final ColorRepository colorRepository;

   private final StorageRepository storageRepository;

   private final CombineRepository combineRepository;

   private final ImageService imageService;

   private final SliderService sliderService;

   private final ProductSliderRepository productSliderRepository;

   private final ProductAttributeRepository productAttributeRepository;

   private final DefaultStorageRepository defaultStorageRepository;

   private final DefaultStorageCombineRepository defaultStorageCombineRepository;

   private final ProductDescRepository productDescRepository;

   private final ProductToSearchProductDtoLess productToSearchProductDtoLess;

   public ProductManagementService(ProductRepository productRepository,
         ColorRepository colorRepository,
         StorageRepository storageRepository,
         CombineRepository combineRepository,
         ImageService imageService,
         SliderService sliderService,
         ProductSliderRepository productSliderRepository,
         DefaultStorageRepository defaultStorageRepository,
         DefaultStorageCombineRepository defaultStorageCombineRepository,
         ProductDescRepository productDescRepository,
         ProductToSearchProductDtoLess productToSearchProductDtoLess,
         ProductAttributeRepository productAttributeRepository) {
      this.productRepository = productRepository;
      this.colorRepository = colorRepository;
      this.storageRepository = storageRepository;
      this.productAttributeRepository = productAttributeRepository;
      this.combineRepository = combineRepository;
      this.imageService = imageService;
      this.sliderService = sliderService;
      this.productSliderRepository = productSliderRepository;
      this.defaultStorageRepository = defaultStorageRepository;
      this.defaultStorageCombineRepository = defaultStorageCombineRepository;
      this.productDescRepository = productDescRepository;
      this.productToSearchProductDtoLess = productToSearchProductDtoLess;
   }

   public Product jsonImport(JsonProductDto json) throws Error {

      try {

         Date start = new Date();

         ConvertEng convertEng = new ConvertEng();

         Product productData = new Product();

         Specification<Product> spec = Specification.where(ProductSpecs.hasName(convertEng.convert(json.name())));
         List<Product> founded = this.productRepository.findAll(spec);

         if (!founded.isEmpty())
            return null;

         productData.setBrandId(json.brand_id());
         productData.setCategoryId(json.category_id());
         productData.setProduct_name(json.name());
         productData.setProduct_name_ascii(convertEng.convert(json.name()));
         productData.setImage_url("");

         // save product
         Product newProduct = this.productRepository.save(productData);

         // save description
         ProductDesc productDescEntity = new ProductDesc();

         productDescEntity.setProductId(newProduct.getId());
         productDescEntity.setContent(json.description());

         this.productDescRepository.save(productDescEntity);

         // save colors
         List<Color> newColors = json.colors().stream().map(cl -> {

            Color colorData = new Color();
            colorData.setProductId(newProduct.getId());
            colorData.setColor_name(cl);
            colorData.setColor_name_ascii(convertEng.convert(cl));

            return this.colorRepository.save(colorData);
         }).toList();

         Thread.sleep(0);

         // save storages
         List<Storage> newStorages = json.storages().stream().map(s -> {

            Storage storageData = new Storage();
            storageData.setProductId(newProduct.getId());
            storageData.setStorage_name(s);
            storageData.setStorage_name_ascii(convertEng.convert(s));
            return this.storageRepository.save(storageData);
         }).toList();

         // save attributes
         json.attributes().stream().map(attr -> {
            ProductAttribute data = new ProductAttribute();

            data.setProduct_id(newProduct.getId());
            data.setValue(attr.value());
            data.setCategory_attribute_id(attr.category_attribute_id());

            return this.productAttributeRepository.save(data);

         }).toList();

         // save combines
         List<Combine> newCombines = new ArrayList<>();
         for (Color cl : newColors) {

            for (Storage sr : newStorages) {

               Combine data = new Combine();

               data.setProductId(newProduct.getId());
               data.setColor_id(cl.getId());
               data.setStorage_id(sr.getId());
               data.setPrice(json.price());
               data.setQuantity(1);

               Combine newCombine = this.combineRepository.save(data);
               newCombines.add(newCombine);
            }

         }

         // save images for sliders

         List<String> imagesList = new ArrayList<>();
         imagesList.add(json.image());
         imagesList.addAll(json.sliders());

         List<Image> imageResponses = imagesList.parallelStream().map(url -> {
            try {
               return this.imageService.saveFromUrl(url);
            } catch (Exception e) {
               throw new Error(e.getMessage());
            }
         }).toList();

         // save product image
         if (json.image() != null) {
            newProduct.setImage_url(imageResponses.get(0).getImage_url());
            this.productRepository.save(newProduct);
         }

         for (Color cl : newColors) {

            // save slider
            Slider newSlider = this.sliderService.create(new SliderDto(null,
                  new String(newProduct.getProduct_name_ascii() + " " + cl.getColor_name_ascii()), null));

            ProductSlider data = new ProductSlider();

            data.setColor_id(cl.getId());
            data.setSlider_id(newSlider.getId());

            // save product slider
            this.productSliderRepository.save(data);

            for (Image image : imageResponses) {

               if (image.getId() == imageResponses.get(0).getId())
                  continue;

               SliderImageDto sliderImageData = new SliderImageDto(null, newSlider.getId(),
                     image.getId(), null,
                     null);
               // save slider image
               this.sliderService.createSliderImage(sliderImageData);
            }

         }

         // save default combine
         DefaultStorage defaultStorageEntity = new DefaultStorage();
         defaultStorageEntity.setProductId(newProduct.getId());
         defaultStorageEntity.setStorage_id(newStorages.get(0).getId());

         this.defaultStorageRepository.save(defaultStorageEntity);

         // save default storage combine
         DefaultStorageCombine defaultStorageCombineEntity = new DefaultStorageCombine();
         defaultStorageCombineEntity.setCombine_id(newCombines.get(0).getId());
         defaultStorageCombineEntity.setStorageId(newStorages.get(0).getId());

         this.defaultStorageCombineRepository.save(defaultStorageCombineEntity);

         Date end = new Date();

         System.out.println("Import finish after: " + (end.getTime() - start.getTime()) / 1000 + "s");

         return newProduct;

      }

      catch (Exception e) {
         // TODO: handle exception
         throw new Error(e.getMessage());

      }

   }

   public MyResponse searchLess(String key) {

      Specification<Product> spec = Specification.where(ProductSpecs.containName(key));

      List<Product> products = this.productRepository.findAll(spec);
      List<SearchProductDto> dto = products.stream().map(p -> this.productToSearchProductDtoLess.convert(p)).toList();

      return new MyResponse(true, "search product successful", 200, dto);

   }

}
