package com.example.demo.default_storage;

import org.springframework.stereotype.Service;

// import com.example.demo.default_storage.converter.DefaultStorageToDefaultStorageDto;
import com.example.demo.default_storage.dto.DefaultStorageDto;
// import com.example.demo.default_storage.entity.DefaultStorage;
import com.example.demo.system.MyResponse;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
public class DefaultStorageService {

    private DefaultStorageRepository defaultStorageRepository;

    // private DefaultStorageToDefaultStorageDto defaultStorageToDefaultStorageDto;

    public DefaultStorageService(DefaultStorageRepository defaultStorageRepository

    ) {
        this.defaultStorageRepository = defaultStorageRepository;
        // this.defaultStorageToDefaultStorageDto = defaultStorageToDefaultStorageDto;
    }

    public MyResponse update(DefaultStorageDto dto) {
        this.defaultStorageRepository.findById(dto.product_ascii()).map(old -> {
            old.setStorage_id(dto.storage_id());

            return this.defaultStorageRepository.save(old);
        })
                .orElseThrow(() -> new ObjectNotFoundException(""));

        return new MyResponse(true, "Update default product storage successful", 200);
    }
}
