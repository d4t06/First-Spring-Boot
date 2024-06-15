package com.example.demo.storage.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.storage.dto.StorageDto;
import com.example.demo.storage.entity.Storage;

@Component
public class StorageDtoToStorage implements Converter<StorageDto, Storage>{

    @Override
    public Storage convert(StorageDto source) {
        Storage storage = new Storage();
        
        storage.setStorage(source.storage());
        storage.setStorage_ascii(source.storage_ascii());
        storage.setProductAscii(source.product_ascii());

        return storage;
    }
    
}
