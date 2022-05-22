package seok.db_project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import seok.db_project.entity.contract;
import seok.db_project.entity.enterprise;
import seok.db_project.repository.allorderRepository;
import seok.db_project.repository.contractRepository;

import java.util.List;

@RestController
@RequestMapping("/contract")
@CrossOrigin
public class contractHandler {
    @Autowired
    private contractRepository contractRepository;

    @Autowired
    private allorderRepository allorderRepository;

    @PostMapping("/add/contractnum")
    public String saveallcontract(){
        contractRepository.deleteAll();
        List<String[]> contract_list = allorderRepository.findAllInfo();

        for (int i = 0; i < contract_list.size(); i++) {
            contract temp = new contract();
            temp.setId(i+1);
            temp.setContract_num(contract_list.get(i)[0]);
            temp.setEnterprise(contract_list.get(i)[1]);
            temp.setManager(contract_list.get(i)[2]);
            temp.setSupply_center(contract_list.get(i)[3]);
            contractRepository.save(temp);
        }
        return "success";
    }

    @GetMapping("/findAll")
    public List<contract> findAll(){
        return contractRepository.findAll();
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<contract> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return contractRepository.findAll(pageable);
    }

    @GetMapping("/getContractCount")
    public int getContractCount(){
        return contractRepository.getContractCount();
    }

    @GetMapping("/getContractInfo1")
    public List<String[]> getContractInfo_1(String contract_num){
        return contractRepository.getContractInfo_1(contract_num);
    }

    @GetMapping("/getContractInfo2")
    public List<Object[]> getContractInfo_2(String contract_num){
        return contractRepository.getContractInfo_2(contract_num);
    }
}
