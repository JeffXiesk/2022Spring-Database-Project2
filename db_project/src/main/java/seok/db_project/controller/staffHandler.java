package seok.db_project.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import seok.db_project.entity.model;
import seok.db_project.entity.staff;
import seok.db_project.repository.staffRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/staff")
@CrossOrigin
public class staffHandler {
    @Autowired
    private staffRepository staffRepository;

    @GetMapping("/findAll")
    public List<staff> findAll(){
        return staffRepository.findAll();
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<staff> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return staffRepository.findAll(pageable);
    }

    @PostMapping("/insert")
    public String save(@RequestBody staff staff1){
        staff result = staffRepository.save(staff1);
        return "success";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        staffRepository.deleteAll();
        return "success";
    }


    @PostMapping("/init")
    public String init() {
        staffRepository.deleteAll();
        List<staff> list = new ArrayList<>();
        String[] FILE_HEADER = {"id", "name","age","gender","number","supply_center","mobile_number","type"};
        String filepath = "src/main/resources/static/data/staff.csv";
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();
        try (BufferedReader in = new BufferedReader(new FileReader(filepath))){
            Iterable<CSVRecord> records = format.parse(in);
            for (CSVRecord record : records){
                staff temp = new staff();
                temp.setId(Integer.parseInt(record.get("id")));
                temp.setName(record.get("name"));
                temp.setAge(Integer.parseInt(record.get("age")));
                temp.setGender(record.get("gender"));
                temp.setNumber(Integer.parseInt(record.get("number")));
                temp.setSupply_center(record.get("supply_center"));
                temp.setMobile_number(record.get("mobile_number"));
                temp.setType(record.get("type"));
                list.add(temp);
            }
            staffRepository.saveAll(list);
        } catch (IOException e) {
            return "fail";
        }
        return "success";
    }

    @GetMapping("/findbyId")
    public staff findbyId(@RequestParam Integer i){
        Optional<staff> result = staffRepository.findById(i);
        return result.orElse(null);
    }

    @GetMapping("/findbyName")
    public staff findbyName(@RequestParam String name){
        return staffRepository.findByName(name);
    }

    @GetMapping("/findbyNumber")
    public staff findbyName(@RequestParam Integer number){
        return staffRepository.findByNumber(number);
    }

    @DeleteMapping("/deleteById")
    @Transactional
    public String deleteById(@RequestParam Integer i){
        staffRepository.deleteById(i);
        return "success";
    }

    @DeleteMapping("/deleteByName")
    @Transactional
    public String deleteByName(@RequestParam String name){
        staffRepository.deleteByName(name);
        return "success";
    }

    @GetMapping("/getAllStaffCount")
    public List<Object[]> getAllStaffCount(){
        List<Object[]> s = staffRepository.getAllStaffCount();
        return s;
    }


}
