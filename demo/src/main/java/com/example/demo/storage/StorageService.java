package com.example.demo.storage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.color.ColorRepository;
import com.example.demo.color.entity.Color;
import com.example.demo.combine.CombineRepository;
import com.example.demo.combine.converter.CombineToCombineDto;
import com.example.demo.combine.dto.CombineDto;
import com.example.demo.combine.entity.Combine;
import com.example.demo.storage.converter.StorageDtoToStorage;
import com.example.demo.storage.converter.StorageToStorageDto;
import com.example.demo.storage.dto.StorageDto;
import com.example.demo.storage.dto.StorageResponseDto;
import com.example.demo.storage.entity.DefaultStorageCombine;
import com.example.demo.storage.entity.Storage;
import com.example.demo.system.MyResponse;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
public class StorageService {
    private final StorageRepository storageRepository;
    private final StorageDtoToStorage storageDtoToStorage;
    private final StorageToStorageDto storageToStorageDto;
    private final ColorRepository colorRepository;
    private final CombineRepository combineRepository;
    private final CombineToCombineDto combineToCombineDto;
    private final DefaultStorageCombineRepository defaultStorageCombineRepository;

    public StorageService(
            StorageRepository storageRepository,
            StorageDtoToStorage storageDtoToStorage,
            ColorRepository colorRepository,
            CombineRepository combineRepository,
            CombineToCombineDto combineToCombineDto,
            DefaultStorageCombineRepository defaultStorageCombineRepository,
            StorageToStorageDto storageToStorageDto) {

        this.storageRepository = storageRepository;
        this.storageDtoToStorage = storageDtoToStorage;
        this.storageToStorageDto = storageToStorageDto;

        this.colorRepository = colorRepository;
        this.combineRepository = combineRepository;
        this.combineToCombineDto = combineToCombineDto;
        this.defaultStorageCombineRepository = defaultStorageCombineRepository;
    }

    public MyResponse add(StorageDto storageDto) {
        Storage storage = this.storageDtoToStorage.convert(storageDto);
        Storage newStorage = this.storageRepository.save(storage);

        // create default storage combine
        DefaultStorageCombine defaultStorageCombine = new DefaultStorageCombine();
        defaultStorageCombine.setStorageId(newStorage.getId());
        DefaultStorageCombine newDefaultCombine = this.defaultStorageCombineRepository.save(defaultStorageCombine);

        // insert default combine object to new storage
        newStorage.setDefaultStorageCombine(newDefaultCombine);
        StorageDto newStorageDto = this.storageToStorageDto.convert(newStorage);

        // find all colors of product and create combine
        List<Color> productColors = this.colorRepository.findByProductAscii(storageDto.product_ascii());
        if (!productColors.isEmpty()) {
            List<CombineDto> newCombinesDto = productColors.stream().map(color -> {
                Combine combine = new Combine();

                combine.setColor_id(color.getId());
                combine.setStorage_id(newStorage.getId());
                combine.setPrice(0);
                combine.setQuantity(0);
                combine.setProductAscii(storageDto.product_ascii());

                Combine newCombine = this.combineRepository.save(combine);
                return this.combineToCombineDto.convert(newCombine);
            }).toList();

            StorageResponseDto data = new StorageResponseDto(newStorageDto, newCombinesDto);

            return new MyResponse(true, "add storage successful", 200, data);

        }

        StorageResponseDto data = new StorageResponseDto(newStorageDto, new ArrayList<>());

        return new MyResponse(true, "add storage successful", 200, data);
    }

    public void update(Long id, StorageDto storageDto) {
        this.storageRepository.findById(id).map(oldStorage -> {

            oldStorage.setStorage(storageDto.storage());
            oldStorage.setStorage_ascii(storageDto.storage_ascii());

            Storage newStorage = this.storageRepository.save(oldStorage);

            return newStorage;
        }).orElseThrow(() -> new ObjectNotFoundException(""));
    }

    public void delete(Long id) {
        this.storageRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Brand not found"));

        this.storageRepository.deleteById(id);
    }
}
