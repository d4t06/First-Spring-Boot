package com.example.demo.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
import com.example.demo.slider.entity.Slider;
import com.example.demo.slider.entity.SliderImage;
import com.example.demo.slider.repository.SliderImageRepository;
import com.example.demo.slider.repository.SliderRepository;
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

   private final SliderRepository sliderRepository;

   private final SliderImageRepository sliderImageRepository;

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
         ProductSliderRepository productSliderRepository,
         DefaultStorageRepository defaultStorageRepository,
         DefaultStorageCombineRepository defaultStorageCombineRepository,
         ProductDescRepository productDescRepository,
         SliderRepository sliderRepository,
         SliderImageRepository sliderImageRepository,
         ProductToSearchProductDtoLess productToSearchProductDtoLess,
         ProductAttributeRepository productAttributeRepository) {
      this.productRepository = productRepository;
      this.colorRepository = colorRepository;
      this.storageRepository = storageRepository;
      this.productAttributeRepository = productAttributeRepository;
      this.combineRepository = combineRepository;
      this.imageService = imageService;
      this.productSliderRepository = productSliderRepository;
      this.defaultStorageRepository = defaultStorageRepository;
      this.defaultStorageCombineRepository = defaultStorageCombineRepository;
      this.productDescRepository = productDescRepository;
      this.productToSearchProductDtoLess = productToSearchProductDtoLess;
      this.sliderRepository = sliderRepository;
      this.sliderImageRepository = sliderImageRepository;
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

         // save colors
         List<Color> newColorEntities = json.colors().stream().map(cl -> {

            Color colorData = new Color();
            colorData.setProductId(newProduct.getId());
            colorData.setColor_name(cl);
            colorData.setColor_name_ascii(convertEng.convert(cl));

            return colorData;
         }).toList();

         // save storages
         List<Storage> newStorageEntities = json.storages().stream().map(s -> {

            Storage storageData = new Storage();
            storageData.setProductId(newProduct.getId());
            storageData.setStorage_name(s);
            storageData.setStorage_name_ascii(convertEng.convert(s));
            return storageData;
         }).toList();

         // save attributes
         List<ProductAttribute> newProductAttributeEntities = json.attributes().stream().map(attr -> {
            ProductAttribute data = new ProductAttribute();

            data.setProduct_id(newProduct.getId());
            data.setValue(attr.value());
            data.setCategory_attribute_id(attr.category_attribute_id());

            return data;

         }).toList();

         /* stage 1 */
         System.out.println((new Date().getTime() - start.getTime()) / 1000 + "s" + ": Stage 1");

         CompletableFuture<ProductDesc> descFuture = CompletableFuture.supplyAsync(() -> {
            return this.productDescRepository.save(productDescEntity);
         });

         CompletableFuture<List<ProductAttribute>> productAttributeFuture = CompletableFuture.supplyAsync(() -> {
            return this.productAttributeRepository.saveAll(newProductAttributeEntities);
         });

         CompletableFuture<List<Color>> colorFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println((new Date().getTime() - start.getTime()) / 1000 + "s" + ": Save color");
            return this.colorRepository.saveAll(newColorEntities);

         });

         CompletableFuture<List<Storage>> storageFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println((new Date().getTime() - start.getTime()) / 1000 + "s" + ": Save storage");

            return this.storageRepository.saveAll(newStorageEntities);
         });

         CompletableFuture<Void> stage1Future = CompletableFuture.allOf(descFuture, colorFuture, storageFuture,
               productAttributeFuture);

         stage1Future.get();

         List<Color> newColors = colorFuture.get();
         List<Storage> newStorages = storageFuture.get();

         // save combines
         List<Combine> newCombineEntities = new ArrayList<>();
         for (Color cl : newColors) {

            for (Storage sr : newStorages) {

               Combine data = new Combine();

               data.setProductId(newProduct.getId());
               data.setColor_id(cl.getId());
               data.setStorage_id(sr.getId());
               data.setPrice(json.price());
               data.setQuantity(1);

               newCombineEntities.add(data);
            }

         }

         // save images for sliders
         List<String> imagesList = new ArrayList<>();
         imagesList.add(json.image());
         imagesList.addAll(json.sliders());

         /** Stage 2 */

         System.out.println((new Date().getTime() - start.getTime()) / 1000 + "s" + ": Stage 2, Upload image");

         CompletableFuture<List<Combine>> combineFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println((new Date().getTime() - start.getTime()) / 1000 + "s" + ": Save combine");

            return this.combineRepository.saveAll(newCombineEntities);
         });

         CompletableFuture<List<Image>> imageFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println((new Date().getTime() - start.getTime()) / 1000 + "s" + ": Upload image");

            return imagesList.parallelStream().map(url -> {
               try {
                  return this.imageService.saveFromUrl(url);
               } catch (Exception e) {
                  throw new Error(e.getMessage());
               }
            }).toList();

         });

         CompletableFuture<Void> stage2Future = CompletableFuture.allOf(combineFuture, imageFuture);
         stage2Future.get();

         List<Combine> newCombines = combineFuture.get();
         List<Image> imageResponses = imageFuture.get();

         System.out.println((new Date().getTime() - start.getTime()) / 1000 + "s" + ": Save product image");

         // save product image
         if (json.image() != null) {
            newProduct.setImage_url(imageResponses.get(0).getImage_url());
            this.productRepository.save(newProduct);
         }

         System.out.println((new Date().getTime() - start.getTime()) / 1000 + "s" + ": Save slider");

         // save slider
         List<Slider> newSliderEntities = newColors.stream().map(cl -> {

            Slider data = new Slider();

            data.setName(newProduct.getProduct_name_ascii() + " " + cl.getColor_name_ascii());

            return data;

         }).toList();

         List<Slider> newSliders = this.sliderRepository.saveAll(newSliderEntities);

         List<ProductSlider> newProductSliderEntities = new ArrayList<>();

         for (int i = 0; i < newSliders.size(); i++) {
            Color cl = newColors.get(i);
            Slider sd = newSliders.get(i);

            ProductSlider data = new ProductSlider();

            data.setColor_id(cl.getId());
            data.setSlider_id(sd.getId());

            newProductSliderEntities.add(data);
         }

         this.productSliderRepository.saveAll(newProductSliderEntities);

         // save slider images
         List<SliderImage> sliderImageEntities = imageResponses.stream().map(i -> {
            SliderImage data = new SliderImage();

            data.setImage_id(i.getId());
            data.setSlider_id(newSliders.get(0).getId());

            return data;
         }).toList();

         // save default combine
         DefaultStorage defaultStorageEntity = new DefaultStorage();
         defaultStorageEntity.setProductId(newProduct.getId());

         defaultStorageEntity.setStorage_id(newStorages.get(0).getId());

         // save default storage combine
         DefaultStorageCombine defaultStorageCombineEntity = new DefaultStorageCombine();
         defaultStorageCombineEntity.setCombine_id(newCombines.get(0).getId());
         defaultStorageCombineEntity.setStorageId(newStorages.get(0).getId());

         /** Stage 3 */

         System.out.println((new Date().getTime() - start.getTime()) / 1000 + "s" + ": Stage 3");

         CompletableFuture<List<SliderImage>> sliderImageFuture = CompletableFuture.supplyAsync(() -> {
            return this.sliderImageRepository.saveAll(sliderImageEntities);
         });

         CompletableFuture<DefaultStorage> defaultStorageFuture = CompletableFuture.supplyAsync(() -> {
            return this.defaultStorageRepository.save(defaultStorageEntity);
         });

         CompletableFuture<DefaultStorageCombine> defaultStorageCombineFuture = CompletableFuture.supplyAsync(() -> {
            return this.defaultStorageCombineRepository.save(defaultStorageCombineEntity);
            // return this.defaultStorageRepository.save(defaultStorageEntity);
         });

         CompletableFuture<Void> stage3Future = CompletableFuture.allOf(sliderImageFuture, defaultStorageFuture,
               defaultStorageCombineFuture,
               productAttributeFuture);

         stage3Future.get();

         System.out.println((new Date().getTime() - start.getTime()) / 1000 + "s" + ": Import finish");

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
