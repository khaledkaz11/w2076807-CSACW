package com.khaledfaraj.csacw.data;

import com.khaledfaraj.csacw.models.Room;
import com.khaledfaraj.csacw.models.Sensor;
import com.khaledfaraj.csacw.models.SensorReading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {
    public static final Map<String, Room> rooms = new HashMap<>();
    public static final Map<String, Sensor> sensors = new HashMap<>();
    public static final Map<String, List<SensorReading>> sensorReadings = new HashMap<>();

    static {
        Room room1 = new Room("LIB-301", "Library Quiet Study", 40, new ArrayList<>());
        rooms.put(room1.getId(), room1);
    }
}