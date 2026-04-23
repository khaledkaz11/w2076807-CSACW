package com.khaledfaraj.csacw.config;

import com.khaledfaraj.csacw.mappers.GlobalExceptionMapper;
import com.khaledfaraj.csacw.mappers.LinkedResourceNotFoundExceptionMapper;
import com.khaledfaraj.csacw.mappers.RoomNotEmptyExceptionMapper;
import com.khaledfaraj.csacw.mappers.SensorUnavailableExceptionMapper;
import com.khaledfaraj.csacw.resources.DiscoveryResource;
import com.khaledfaraj.csacw.resources.RoomResource;
import com.khaledfaraj.csacw.resources.SensorResource;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import com.khaledfaraj.csacw.filters.LoggingFilter;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/v1")
public class SmartCampusApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(DiscoveryResource.class);
        classes.add(RoomResource.class);
        classes.add(SensorResource.class);
        classes.add(RoomNotEmptyExceptionMapper.class);
        classes.add(LinkedResourceNotFoundExceptionMapper.class);
        classes.add(SensorUnavailableExceptionMapper.class);
        classes.add(GlobalExceptionMapper.class);
        classes.add(LoggingFilter.class);
        return classes;
    }
}