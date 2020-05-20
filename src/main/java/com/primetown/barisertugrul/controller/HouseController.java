package com.primetown.barisertugrul.controller;

import com.primetown.barisertugrul.exception.HouseNotFoundException;
import com.primetown.barisertugrul.model.House;
import com.primetown.barisertugrul.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("primetown")
public class HouseController {

    //Service folgt auch in dieser Datei ab

    @Autowired
    private HouseRepository houseRepository;

    private Calendar calendar;

    //Get all houses
    @GetMapping("/houses")
    public List<House> getAllHouses(){ return houseRepository.findAll();}

    //Create house
    @PostMapping("/houses")
    public House createHouse(@Validated @RequestBody House house){
        if((house.getYearOfConstruction() >= 1800 && house.getYearOfConstruction() <= calendar.getInstance().get(Calendar.YEAR) && house.isPrime(house.getNumber()))){
            return houseRepository.save(house);
        }
            return null;
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

        if((house.getYearOfConstruction() >= 1800 && house.getYearOfConstruction() <= calendar.getInstance().get(Calendar.YEAR) && house.isPrime(house.getNumber()))) {
            houseRepository.save(house);
            return ResponseEntity.ok().body(house);
        } return null;
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
