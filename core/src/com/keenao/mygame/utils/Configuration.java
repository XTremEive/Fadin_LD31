package com.keenao.mygame.utils;

import java.util.HashMap;

public class Configuration {
    public static Configuration parse(String input)
    {
        // Create object
        Configuration configuration = new Configuration();

        // Parse input
        for(String line : input.split("\n"))
        {
            line = line.trim();

            String[] lineComponents = line.split(" ");

            configuration.set(lineComponents[0], Integer.parseInt(lineComponents[1]));
        }

        // Return
        return configuration;
    }

    private HashMap<String, Integer> values;
    private HashMap<String, Integer> defaultValues;

    public Configuration() {
        this.values = new HashMap<>();
        this.defaultValues = new HashMap<>();
    }

    public void setDefault(String key, int value)
    {
        defaultValues.put(key, value);
    }

    public void set(String key, int value)
    {
        values.put(key, value);
    }

    public int get(String key)
    {
        return values.containsKey(key) ? values.get(key) : getDefault(key);
    }

    public int getDefault(String key)
    {
        return defaultValues.containsKey(key) ? defaultValues.get(key) : 0;
    }
}
