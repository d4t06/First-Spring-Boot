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
        
        storage.setStorage_name(source.storage_name());
        storage.setStorage_name_ascii(source.storage_name_ascii());
        storage.setProductId(source.product_id());

        return storage;
    }
    
}
