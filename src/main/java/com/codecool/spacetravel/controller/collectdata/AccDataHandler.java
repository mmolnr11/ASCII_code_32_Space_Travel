package com.codecool.spacetravel.controller.collectdata;

import com.codecool.spacetravel.DAO.QueryHandler;
import com.codecool.spacetravel.model.Accomodation;
import com.codecool.spacetravel.model.AmenityType;
import com.codecool.spacetravel.model.Planet;
import com.codecool.spacetravel.repository.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AccDataHandler {

    @Autowired
    private QueryHandler queryHandler;

    public void collectAccData(Model model,
                                long planetId,
                                HttpServletRequest httpServletRequest){

        Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");

        Planet planet = queryHandler.getPlanet(planetId);

        List<Accomodation> accList = queryHandler.getAccByPlanetId(planetId);
        for (Accomodation accomodation : accList
             ) {
            System.out.println(accomodation.getName());
        }

        model.addAttribute("loggedIn", customerId != null);
        model.addAttribute("customername", customerName);
        model.addAttribute("accomodations", accList);
        model.addAttribute("planet", planet);

    }

    public void collectAccRegistrationData(Model model, HttpServletRequest httpServletRequest) {
        Integer userId = (Integer) httpServletRequest.getSession().getAttribute("user_id");
        List<Planet> allPlanet = queryHandler.getAllPlanet();
        List<AmenityType> amenityTypes = queryHandler.getAllAmenity();

        model.addAttribute("loggedIn", userId != null);
        model.addAttribute("planets", allPlanet);
        model.addAttribute("amenityTypes", amenityTypes);
        model.addAttribute("accomodation", new Accomodation());

    }

}

