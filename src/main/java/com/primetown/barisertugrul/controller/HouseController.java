package com.primetown.barisertugrul.controller;

import com.primetown.barisertugrul.exception.HouseNotFoundException;
import com.primetown.barisertugrul.model.House;
import com.primetown.barisertugrul.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("primetown")
public class HouseController {

    @Autowired
    private HouseRepository houseRepository;

    //Get all houses
    @GetMapping("/houses")
    public List<House> getAllHouses(){ return houseRepository.findAll();}

    //Create house
    @PostMapping("/houses")
    public House createHouse(@Validated @RequestBody House house){
        return houseRepository.save(house);
    }

    //Get house by id
    @GetMapping("/houses/{id}")
    public ResponseEntity<House> getHouseById(@PathVariable(value = "id") Long houseId)
            throws HouseNotFoundException {
            House house = houseRepository.findById(houseId)
                    .orElseThrow(() -> new HouseNotFoundException("House not found for this id :: " + houseId));
            return ResponseEntity.ok().body(house);
    }

    //Update house by id
    @PutMapping("/houses/{id}")
    public ResponseEntity<House> updateHouse(@PathVariable(value = "id") Long houseId, @RequestBody House houseDetails)
        throws HouseNotFoundException {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new HouseNotFoundException("House not found for this id :: " + houseId));

        house.setOwner(houseDetails.getOwner());
        house.setYearOfConstruction(houseDetails.getYearOfConstruction());
        house.setNumber(houseDetails.getNumber());
        houseRepository.save(house);
        return ResponseEntity.ok().body(house);
    }


    //Delete house
    @DeleteMapping("/houses/{id}")
    public ResponseEntity<House> deleteHouse(@PathVariable(value = "id") Long houseId)
            throws HouseNotFoundException {
            houseRepository.findById(houseId)
                    .orElseThrow(() -> new HouseNotFoundException("House not found for this id :: " + houseId));

            houseRepository.deleteById(houseId);
            return ResponseEntity.ok().build();
    }

}
