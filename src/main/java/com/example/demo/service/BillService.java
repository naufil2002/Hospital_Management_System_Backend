package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Bill;
import com.example.demo.repository.BillRepository;

@Service
public class BillService {

    public static final Logger logger = LoggerFactory.getLogger(BillService.class);  // Changed from PatientService to BillService

    @Autowired
    private BillRepository billrepository;

    // Changed return type from Page<Bill> to List<Bill> to support JOIN FETCH in repository
    public List<Bill> getAllBills() {
        try {
            System.out.println("Into service layer");
            return billrepository.findAllBillsWithDetails();  // Changed to use custom query with JOIN FETCH
        } catch (Exception e) {
            System.out.println("Error msg" + e.getMessage());
            logger.error("An error occurred during fetching all the Bills: {}", e.getMessage());
            return null;
        }
    }

    public Bill createBill(Bill bill) {
        try {
            billrepository.save(bill);
            return bill;
        } catch (Exception e) {
            System.out.println("Error msg" + e.getMessage());
            logger.error("An error occurred during creating the Bills: {}", e.getMessage());
            return null;
        }
    }

    public Bill getBillById(Long id) {
        try {
            Optional<Bill> bill = billrepository.findById(id);
            return bill.orElse(null);
        } catch (Exception e) {
            System.out.println("Error msg" + e.getMessage());
            logger.error("An error occurred during fetching the Bills by Id {} : {}", id, e.getMessage());
            return null;
        }
    }

    public Bill updateBill(Long id, Bill updatedBill) {
        try {
            Optional<Bill> existingBill = billrepository.findById(id);
            if (existingBill.isPresent()) {
                Bill b = existingBill.get();
                b.setAmount(updatedBill.getAmount());
                b.setStatus(updatedBill.getStatus());
                b.setAppointment(updatedBill.getAppointment());  // Changed: Correctly updating appointment reference
                billrepository.save(b);

                return b;  // Changed: Return updated entity from DB
            } else {
                logger.error("Bill with ID {} not found", id);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error msg" + e.getMessage());
            logger.error("An error occurred during updating the Bills by Id {} : {}", id, e.getMessage());
            return null;
        }
    }

    public void deleteBill(Long id) {
        try {
            logger.info("Deleting bill with id : {}", id);
            billrepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Error msg" + e.getMessage());
            logger.error("An error occurred during deleting the Bills by Id {} : {}", id, e.getMessage());
        }
    }
}
