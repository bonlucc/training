package com.example.demo.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Endpoint(id = "features")
public class FeatureEndpoint {
    private final Map<String, Feature> featureMap = new ConcurrentHashMap<>();

    public FeatureEndpoint() {
        featureMap.put("Department", new Feature(true));
        featureMap.put("User", new Feature(false));
        featureMap.put("Authentication", new Feature(false));
    }

    @ReadOperation
    public Map<String, Feature> features(){
        return featureMap;
    }

    /*
    @WriteOperation
    public Feature setDepartmentFalse(@Selector String arg0){
        Feature property = featureMap.get(arg0);
        property.setEnabled(false);
        return property;
    }
    */

    @ReadOperation
    public Feature feature(@Selector String featureName){
        return featureMap.get(featureName);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class Feature {
        private boolean isEnabled;

    }

}
