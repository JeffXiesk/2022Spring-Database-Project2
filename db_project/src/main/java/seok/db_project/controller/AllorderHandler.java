package seok.db_project.controller;

import antlr.collections.impl.LList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import seok.db_project.entity.*;
import seok.db_project.repository.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/allorder")
@CrossOrigin
public class AllorderHandler {
    @Autowired
    private allorderRepository allorderRepository;

    @Autowired
    private productRepository productRepository;

    @Autowired
    private staffRepository staffRepository;

    @Autowired
    private enterpriseRepository enterpriseRepository;


    @GetMapping("/findAll")
    public List<allorder> findAll() {
        return allorderRepository.findAll();
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<allorder> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return allorderRepository.findAll(pageable);
    }

    @PostMapping("/placeOrder")
    public String placeOrder() {
        allorderRepository.deleteAll();
//        List<contract> list = new ArrayList<>();
        String[] FILE_HEADER = {"contract_num", "enterprise", "product_model", "quantity", "contract_manager", "contract_date", "estimated_delivery_date", "lodgement_date", "salesman_num", "contract_type"};
        String filepath = "src/main/resources/static/data/final/task2.csv";
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();
        int cnt = 1;
        try (BufferedReader in = new BufferedReader(new FileReader(filepath))) {
            Iterable<CSVRecord> records = format.parse(in);
            String strId, strEnterprise, strProduct_model, strQuantity, strContract_manager, strContract_date, strEstimated_delivery_date, strLodgement_date, strSalesman_num, strContract_type;
            for (CSVRecord record : records) {
                strId = record.get("contract_num");
                strEnterprise = record.get("enterprise");
                strProduct_model = record.get("product_model");
                strQuantity = record.get("quantity");
                strContract_manager = record.get("contract_manager");
                strContract_date = record.get("contract_date");
                strEstimated_delivery_date = record.get("estimated_delivery_date");
                strLodgement_date = record.get("lodgement_date");
                strSalesman_num = record.get("salesman_num");
                strContract_type = record.get("contract_type");
                allorder temp = new allorder();

                staff salesman = staffRepository.findByNumber(Integer.parseInt(strSalesman_num));
                if (!salesman.getType().equals("Salesman")) {
                    continue;
                }
                enterprise enterprise = enterpriseRepository.findByName(strEnterprise);
                List<product> res = productRepository.findAllBySupply_centerAndAndProduct_model(enterprise.getSupply_center(), strProduct_model);
                if (res.size() == 0) continue;
                int total_quantity = res.get(0).getRemain();
                int remain = total_quantity - Integer.parseInt(strQuantity);
                if (remain < 0)
                    continue;

                temp.setId(cnt++);
                temp.setContract_num(strId);
                temp.setEnterprise(strEnterprise);
                temp.setProduct_model(strProduct_model);
                temp.setQuantity(Integer.parseInt(strQuantity));
                temp.setContract_manager(Integer.parseInt(strContract_manager));
                temp.setContract_date(strContract_date);
                temp.setEstimated_delivery_date(strEstimated_delivery_date);
                temp.setLodgement_date(strLodgement_date);
                temp.setSalesman_num(Integer.parseInt(strSalesman_num));
                temp.setContract_type(strContract_type);
                productRepository.updateRemainOfSupply_centerAndProduct_model(remain, enterprise.getSupply_center(), strProduct_model);
                allorderRepository.save(temp);
//                list.add(temp);
            }
//            contractRepository.saveAll(list);
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @PostMapping("/updateOrder")
    public String updateOrder() {
        String filepath = "src/main/resources/static/data/final/task3_update.tsv";
        try (BufferedReader in = new BufferedReader(new FileReader(filepath))) {
            in.readLine();
            String line = in.readLine();
            while (line != null) {
                String[] temp = line.split("\t");
                allorder res = allorderRepository.findOrder(Integer.parseInt(temp[2]), temp[0], temp[1]);
                if (res == null) {
                    line = in.readLine();
                    continue;
                }
                int before = res.getQuantity();
                int now = Integer.parseInt(temp[3]);
                if (now == before) {
                    line = in.readLine();
                    continue;
                }
                int compare = before - now;
                enterprise enterprise = enterpriseRepository.findByName(res.getEnterprise());
                String su_ce = enterprise.getSupply_center();
                List<product> product_list = productRepository.findAllBySupply_centerAndAndProduct_model(su_ce, res.getProduct_model());
                productRepository.updateRemainOfSupply_centerAndProduct_model(product_list.get(0).getRemain() + compare, su_ce, res.getProduct_model());
                if (now == 0) {
                    allorderRepository.deleteOrder(res.getContract_num(), res.getProduct_model(), res.getSalesman_num());
                } else {
                    allorderRepository.updateQuantityOfSalesmenAndModel(res.getQuantity(), res.getSalesman_num(), res.getContract_num(), res.getProduct_model());
                }
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @PostMapping("/deleteOrder")
    public String deleteOrder() {
        String filepath = "src/main/resources/static/data/final/task4_delete.tsv";
        try (BufferedReader in = new BufferedReader(new FileReader(filepath))) {
            in.readLine();
            String line = in.readLine();
            while (line != null) {
                String[] temp = line.split("\t");
                List<allorder> find_res = allorderRepository.findByContract_numAndSalesman_num(temp[0], Integer.parseInt(temp[1]));
                int seq = Integer.parseInt(temp[2]);
                if (find_res.size() < seq) {
                    line = in.readLine();
                    continue;
                }

                find_res.sort((a, b) -> {
                    String stra = a.getEstimated_delivery_date().replaceAll("-", "");
                    String strb = b.getEstimated_delivery_date().replaceAll("-", "");
                    int inta = Integer.parseInt(stra);
                    int intb = Integer.parseInt(strb);
                    if (inta == intb) {
                        return a.getProduct_model().compareTo(b.getProduct_model());
                    } else
                        return inta > intb ? 1 : -1;
                });

                allorder now = find_res.get(seq - 1);
                String contract_num = now.getContract_num();
                String product_model = now.getProduct_model();
                int salesman = now.getSalesman_num();
                allorderRepository.deleteOrder(contract_num, product_model, salesman);

                enterprise enterprise = enterpriseRepository.findByName(now.getEnterprise());
                String su_ce = enterprise.getSupply_center();

                List<product> product_list = productRepository.findAllBySupply_centerAndAndProduct_model(su_ce, product_model);
                int quantity = now.getQuantity();
                productRepository.updateRemainOfSupply_centerAndProduct_model(product_list.get(0).getRemain() + quantity, su_ce, product_model);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @GetMapping("/getOrderCount")
    public int getOrderCount() {
        return allorderRepository.getOrderCount();
    }

    @GetMapping("/advancequery")
    public List<allorder> advancequery(String c_n, String enterprise, String p_m, String manager, String salesman) {
        List<allorder> allorders = allorderRepository.findAll();
        List<allorder> result = new ArrayList<>();
        for (allorder entry : allorders) {
            if (!c_n.equals("")) {
                if (!entry.getContract_num().equals(c_n))
                    continue;
            }
            if (!enterprise.equals("")) {
                if (!entry.getEnterprise().equals(enterprise))
                    continue;
            }
            if (!p_m.equals("")) {
                if (!entry.getProduct_model().equals(p_m))
                    continue;
            }
            if (!manager.equals("")) {
                if (!manager.equals(entry.getContract_manager().toString())) {
                    staff staff = staffRepository.findByName(manager);
                    if (staff == null) continue;
                    if (!staff.getNumber().equals(entry.getContract_manager())) {
                        continue;
                    }
                }
            }
            if (!salesman.equals("")) {
                if (!salesman.equals(entry.getSalesman_num().toString())) {
                    staff staff = staffRepository.findByName(salesman);
                    if (staff == null) continue;
                    if (!staff.getNumber().equals(entry.getSalesman_num())) {
                        continue;
                    }
                }
            }
            result.add(entry);
        }
        return result;
    }

}
