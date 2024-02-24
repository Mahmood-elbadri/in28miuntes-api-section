package com.in28minutes.springboot.fullrestapi.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {
    @GetMapping("/filter")
    public MappingJacksonValue applyFilterForSingleBean() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3");
        return applyFilter(someBean, "SomeBeanFilter", "value2");
    }

    @GetMapping("/filter-list")
    public MappingJacksonValue applyFilterForList() {
        List<SomeBean> someBeans = Arrays.asList(
                new SomeBean("value1", "value2", "value3"),
                new SomeBean("value1", "value2", "value3"),
                new SomeBean("value1", "value2", "value3")
        );
        return applyFilter(someBeans, "SomeBeanFilter", "value1", "value2");
    }


    public MappingJacksonValue applyFilter(Object object, String classFilterName, String... fields) {
        try {
            MappingJacksonValue jacksonValue = new MappingJacksonValue(object);
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter(classFilterName, filter);
            jacksonValue.setFilters(filterProvider);
            return jacksonValue;
        } catch (Exception ex) {
            // Handle or log the exception
            ex.printStackTrace();
            return new MappingJacksonValue(object);
        }
    }



}
