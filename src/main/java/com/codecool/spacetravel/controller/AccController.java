package com.codecool.spacetravel.controller;

import com.codecool.spacetravel.Model.Accomodation;
import com.codecool.spacetravel.Model.Planet;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccController {
    public static ModelAndView renderAcc(Request req, Response res, int planetId, EntityManager em) {

        Planet planet = QueryController.getPlanet(planetId,em);

        List<Accomodation> accList = QueryController.getAccByPlanetId(planetId, em);
        //System.out.println("getPlanetsBySolarSystemId - controller");

        Map params = new HashMap<>();
        params.put("accomodations", accList);
        params.put("planet", planet);
        return new ModelAndView(params, "accomodation");
    }
}