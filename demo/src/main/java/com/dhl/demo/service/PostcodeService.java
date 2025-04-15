package com.dhl.demo.service;

import com.dhl.demo.entity.Postcodelatlng;
import com.dhl.demo.model.PostcodeDistanceRequest;
import com.dhl.demo.model.PostcodeDistanceRequestResponse;
import com.dhl.demo.repository.PostcodeLattitudeLongitudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PostcodeService {

        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PostcodeService.class);

        @Autowired
        PostcodeLattitudeLongitudeRepository postcodeLattitudeLongitudeRepository;

        private static final MathContext MC = new MathContext(10, RoundingMode.HALF_UP);

        public boolean isValidPostcode(String postcode) {
                logger.debug("Validating postcode: {}", postcode);
                Postcodelatlng result = postcodeLattitudeLongitudeRepository.findByPostcode(postcode);
                if(result == null) {
                        return false;
                }
                return true;
        }

        /**
         * This method calculates the distance between two postcodes using the Haversine formula.
         * The method checks if the postcodes are valid and if the unit of measure is valid.
         * If any of the postcodes are invalid, it returns an error message.
         * If the unit of measure is not provided, it defaults to "KM".
         * It supports multiple postcodes and calculates the distance between them.
         * The distance is calculated based on the unit of measure provided in the request.
         * The distance is rounded to 2 decimal places.
         * @param distanceRequest The request object containing the postcodes and unit of measure
         * @return A response object containing the calculated distance or an error message
         */
        public PostcodeDistanceRequestResponse calculatePostcodeDistance(PostcodeDistanceRequest distanceRequest) {

                PostcodeDistanceRequestResponse response = new PostcodeDistanceRequestResponse();
                BigDecimal distance = BigDecimal.ZERO;
                for(String postcode : distanceRequest.getPostcodes()) {
                        if(!isValidPostcode(postcode)) {
                                response.setStatus(PostcodeDistanceRequestResponse.ERROR);
                                response.setMessage("Invalid postcode: " + postcode);
                                return response;
                        }
                }

                //check for Unit of measure
                if(distanceRequest.getUnitOfMeasure() == null || distanceRequest.getUnitOfMeasure().isEmpty()) {
                        distanceRequest.setUnitOfMeasure("KM");
                }

                //loop through the postcodes
                for(int i = 0; i < distanceRequest.getPostcodes().size() - 1; i++) {
                        Postcodelatlng origin = postcodeLattitudeLongitudeRepository.findByPostcode(distanceRequest.getPostcodes().get(i));
                        Postcodelatlng destination = postcodeLattitudeLongitudeRepository.findByPostcode(distanceRequest.getPostcodes().get(i + 1));
                        if(distanceRequest.getUnitOfMeasure().equalsIgnoreCase("KM")) {
                                logger.debug("Calculating distance in KM");
                                distance = distance.add(haversine(new BigDecimal(origin.getLatitude()), new BigDecimal(origin.getLongitude()), new BigDecimal(destination.getLatitude()), new BigDecimal(destination.getLongitude()), new BigDecimal(PostcodeDistanceRequest.RADIUS_KM)));
                        } else if(distanceRequest.getUnitOfMeasure().equalsIgnoreCase("MILES")) {
                                distance = distance.add(haversine(new BigDecimal(origin.getLatitude()), new BigDecimal(origin.getLongitude()), new BigDecimal(destination.getLatitude()), new BigDecimal(destination.getLongitude()), new BigDecimal(PostcodeDistanceRequest.RADIUS_MILES)));
                        } else if(distanceRequest.getUnitOfMeasure().equalsIgnoreCase("METERS")) {
                                distance = distance.add(haversine(new BigDecimal(origin.getLatitude()), new BigDecimal(origin.getLongitude()), new BigDecimal(destination.getLatitude()), new BigDecimal(destination.getLongitude()), new BigDecimal(PostcodeDistanceRequest.RADIUS_METERS)));
                        } else if(distanceRequest.getUnitOfMeasure().equalsIgnoreCase("YARDS")) {
                                distance = distance.add(haversine(new BigDecimal(origin.getLatitude()), new BigDecimal(origin.getLongitude()), new BigDecimal(destination.getLatitude()), new BigDecimal(destination.getLongitude()), new BigDecimal(PostcodeDistanceRequest.RADIUS_YARDS)));
                        } else if(distanceRequest.getUnitOfMeasure().equalsIgnoreCase("FEET")) {
                                distance = distance.add(haversine(new BigDecimal(origin.getLatitude()), new BigDecimal(origin.getLongitude()), new BigDecimal(destination.getLatitude()), new BigDecimal(destination.getLongitude()), new BigDecimal(PostcodeDistanceRequest.RADIUS_FEET)));
                        } else if(distanceRequest.getUnitOfMeasure().equalsIgnoreCase("NAUTICAL_MILES")) {
                                distance = distance.add(haversine(new BigDecimal(origin.getLatitude()), new BigDecimal(origin.getLongitude()), new BigDecimal(destination.getLatitude()), new BigDecimal(destination.getLongitude()), new BigDecimal(PostcodeDistanceRequest.RADIUS_NAUTICAL_MILES)));
                        } else {
                                response.setStatus(PostcodeDistanceRequestResponse.ERROR);
                                response.setMessage("Invalid unit of measure: " + distanceRequest.getUnitOfMeasure());
                                return response;
                        }
                }
                logger.debug("Distance 2: {}", distance);
                response.setStatus(PostcodeDistanceRequestResponse.SUCCESS);
                response.setMessage("Distance calculated successfully");
                response.setDistance(distance.toString());
                response.setUnitsOfMeasure(distanceRequest.getUnitOfMeasure());

                return response;
        }

        // Helper methods needed
        private BigDecimal toRadians(BigDecimal deg) {
                return deg.multiply(BigDecimal.valueOf(Math.PI))
                        .divide(BigDecimal.valueOf(180), 10, RoundingMode.HALF_UP);
        }

        private BigDecimal sin(BigDecimal x) {
                return BigDecimal.valueOf(Math.sin(x.doubleValue()));
        }

        private BigDecimal cos(BigDecimal x) {
                return BigDecimal.valueOf(Math.cos(x.doubleValue()));
        }


        public BigDecimal haversine(BigDecimal originLat, BigDecimal originLong, BigDecimal destinationLat, BigDecimal destinationLong,
                                    BigDecimal radius) {
                logger.debug("Radius: {}", radius);
                // Convert to radians
                BigDecimal latDistance = toRadians(destinationLat.subtract(originLat));
                BigDecimal lonDistance = toRadians(destinationLong.subtract(originLong));
                BigDecimal lat1 = toRadians(originLat);
                BigDecimal lat2 = toRadians(destinationLat);

                // Calculate haversine formula
                BigDecimal a = sin(latDistance.divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP))
                        .pow(2)
                        .add(
                                cos(lat1)
                                        .multiply(cos(lat2))
                                        .multiply(
                                                sin(lonDistance.divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP))
                                                        .pow(2)
                                        )
                        );

                BigDecimal c = BigDecimal.valueOf(2)
                        .multiply(BigDecimal.valueOf(
                                Math.atan2(
                                        Math.sqrt(a.doubleValue()),
                                        Math.sqrt(BigDecimal.ONE.subtract(a).doubleValue())
                                )
                        ));


                logger.debug("Radius: {}", radius);
                logger.debug("A: {}", a);
                logger.debug("C: {}", c);
                logger.debug("Radius * C: {}", radius.multiply(c));
                logger.debug("Rounded: {}", radius.multiply(c).round(MC));

                BigDecimal distance = radius.multiply(c).round(MC);

                return distance;
        }





}
