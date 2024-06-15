package com.example.demo.combine;

import org.springframework.stereotype.Service;
import com.example.demo.combine.converter.CombineDtoToCombine;
import com.example.demo.combine.converter.CombineToCombineDto;
import com.example.demo.combine.dto.CombineDto;
import com.example.demo.combine.entity.Combine;
import com.example.demo.storage.DefaultStorageCombineRepository;
import com.example.demo.storage.dto.DefaultStorageCombineDto;
import com.example.demo.system.MyResponse;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
public class CombineService {

    private final CombineRepository combineRepository;
    private final CombineDtoToCombine combineDtoToCombine;
    private final CombineToCombineDto combineToCombineDto;
    private final DefaultStorageCombineRepository defaultStorageCombineRepository;

    public CombineService(CombineRepository combineRepository,
            CombineDtoToCombine combineDtoToCombine,
            DefaultStorageCombineRepository defaultStorageCombineRepository,
            CombineToCombineDto combineToCombineDto) {

        this.combineDtoToCombine = combineDtoToCombine;
        this.combineToCombineDto = combineToCombineDto;
        this.combineRepository = combineRepository;
        this.defaultStorageCombineRepository = defaultStorageCombineRepository;
    }

    public CombineDto add(CombineDto combineDto) {
        Combine combine = this.combineDtoToCombine.convert(combineDto);
        Combine newCombine = this.combineRepository.save(combine);

        return this.combineToCombineDto.convert(newCombine);
    }

    public MyResponse update(Long id, CombineDto combineDto) {
        Combine newCombine = this.combineRepository.findById(id).map(oldCombine -> {

            oldCombine.setPrice(combineDto.price());
            oldCombine.setQuantity(combineDto.quantity());

            // List<Combine> combines =
            // this.combineRepository.findByStorageId(oldCombine.getStorage_id());
            // long defaultStorageCombineId = oldCombine.getId();
            // if (!combines.isEmpty()) {

            // int MAX_VAL = 999999999;
            // for (Combine cb : combines) {
            // if (cb.getPrice() < MAX_VAL) {
            // MAX_VAL = cb.getPrice();
            // defaultStorageCombineId = cb.getId();
            // }
            // }
            // }

            // DefaultStorageCombine defaultStorage = this.defaultStorageCombineRepository
            // .findByStorageId(oldCombine.getId()).map(
            // defaultCombine -> {
            // defaultCombine.setCombine_id(defaultStorageCombineId);
            // return this.defaultStorageCombineRepository.save(defaultCombine);
            // })
            // .orElseThrow(() -> new ObjectNotFoundException(""));

            return this.combineRepository.save(oldCombine);
        }).orElseThrow(() -> new ObjectNotFoundException(""));

        CombineDto newCombineDto = this.combineToCombineDto.convert(newCombine);
        return new MyResponse(true, "update combine successful", 200, newCombineDto);
    }

    public void delete(Long id) {
        this.combineRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Brand not found"));

        this.combineRepository.deleteById(id);
    }

    public MyResponse updateDefaultCombine(Long storageId, DefaultStorageCombineDto defaultCombine) {
        this.defaultStorageCombineRepository.findByStorageId(storageId)
                .map(oldDefault -> {
                    oldDefault.setCombine_id(defaultCombine.combine_id());
                    return this.defaultStorageCombineRepository.save(oldDefault);
                })
                .orElseThrow(() -> new ObjectNotFoundException(""));

        return new MyResponse(true, "update default combine successful", 200);
    }
}
