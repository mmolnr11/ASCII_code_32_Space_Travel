package com.codecool.spacetravel.controller.collectdata;

import com.codecool.spacetravel.DAO.QueryHandler;
import com.codecool.spacetravel.model.Customer;
import com.codecool.spacetravel.model.CustomerLegitimacy;
import com.codecool.spacetravel.model.RoomReservation;
import com.codecool.spacetravel.service.CustomerDataValidator;
import com.codecool.spacetravel.service.Password;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerDataHandler{

    private QueryHandler queryHandler;
    private CustomerDataValidator customerDataValidator;
    private Password password;

    public CustomerDataHandler(QueryHandler queryHandler, CustomerDataValidator customerDataValidator,Password password) {
        this.queryHandler = queryHandler;
        this.customerDataValidator = customerDataValidator;
        this.password =password;
    }

    public boolean saveCustomerDatas(Customer customer) {
        boolean savingSucceeded = false;


        String passwordStr = customer.getPassword();
        String hashPassword = password.hashPassword(passwordStr);
        customer.setPassword(hashPassword);
        customer.setLegitimacy(CustomerLegitimacy.USER);

        try {
            queryHandler.saveNewCustomer(customer);
            savingSucceeded = true;
        } catch (Exception e){
            System.out.println("SAVING FAILED: " + e.getMessage());
        }
        return savingSucceeded;
    }

    public Model collectCustomerRegistrationData(Customer customer,
                                               String confirm,
                                               Model model) {
        List<String> errorMessages;
        boolean savingSucceeded = false;
        boolean savingTried = false;

        errorMessages = customerDataValidator.validateRegistrationDatas(customer, confirm);

        if (errorMessages.size() == 0){
            savingTried = true;
            savingSucceeded = saveCustomerDatas(customer);
        }

        model.addAttribute("customer", customer);
        model.addAttribute("errors", errorMessages);
        model.addAttribute("savingsucceeded", savingSucceeded);
        model.addAttribute("savingtried", savingTried);

        return model;
    }

    public Model collectLoginData(@RequestParam Map<String,String> allRequestParams,
                                Model model) {
        List<String> errorMessages = new ArrayList();
        Map<String, String> customerDatas = new HashMap<>();
        Customer customer = null;
        if (allRequestParams.size() > 0){
            customerDatas.put("email", allRequestParams.get("email"));
            customerDatas.put("password", allRequestParams.get("password"));

            Map<String, Object> errorMessagesAndCustomer = customerDataValidator.validateLoginDatas(customerDatas);
            errorMessages = (List<String>) errorMessagesAndCustomer.get("errors");
            customer = (Customer) errorMessagesAndCustomer.get("customer");

        }

        model.addAttribute("customer", customerDatas);
        model.addAttribute("errors", errorMessages);
        model.addAttribute("validcustomer", customer);

        return model;
    }

    public boolean checkUserIsAdmin(long customerId) {

        Customer customer = queryHandler.getCustomerById(customerId);

        if (customer.getLegitimacy()== CustomerLegitimacy.ADMIN) {
            return true;

        }
        return false;
    }

    public boolean checkUserIsLoggedUserOrAdmin(long customerId) {

        Customer customer = queryHandler.getCustomerById(customerId);

        if (customer.getLegitimacy()== CustomerLegitimacy.ADMIN || customer.getLegitimacy()== CustomerLegitimacy.USER ) {
            return true;

        }
        return false;
    }

}
