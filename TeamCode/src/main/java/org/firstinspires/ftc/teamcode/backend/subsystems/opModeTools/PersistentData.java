package org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PersistentData is a utility class designed to manage the persistent storage of
 * key-value data. It uses serialization to store and retrieve
 * data from a file and ensures thread-safety with the use of a {@link ConcurrentHashMap}
 */
public class PersistentData {
    private static final String FILE = "data.dat";
    private static Map<String, DataObject> data;


    /**
     * Constructor for PersistentData. It attempts to load data from the specified
     * file. If the file does not exist, a new {@link ConcurrentHashMap} is created
     */
    public PersistentData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(FILE)))) {
            data = (ConcurrentHashMap) ois.readObject();
        } catch (FileNotFoundException e) {
            data = new ConcurrentHashMap<>(); // File doesn't exist, initialize new HashMap
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Log error
            data = new ConcurrentHashMap<>();
        }
    }

    /**
     * Adds a new key-value pair to the data store
     *
     * @param name The key under which the data will be stored
     * @param dataObject The data object to be stored
     */
    public static void addData(String name, DataObject dataObject) {
        data.put(name, dataObject);
    }

    /**
     * Replaces the existing data object associated with the given key
     * If the key does not exist, an exception is thrown
     *
     * @param name The key whose associated data is to be replaced
     * @param dataObject The new data object to be stored
     * @throws IllegalArgumentException if the key or data object is null, or if
     *         the key does not already exist in the map
     */
    public static void setData(String name, DataObject dataObject) {
        if (name == null || dataObject == null)
            throw new IllegalArgumentException("Name or DataObject cannot be null");
        if (data.replace(name, dataObject) == null)
            throw new IllegalArgumentException(name + " isn't a value stored");
    }

    /**
     * Retrieves the data object associated with the specified key
     *
     * @param name The key whose associated data is to be retrieved
     * @return The data object associated with the key, or null if no data exists for the key
     * @throws ClassCastException if the retrieved data cannot be cast to the expected type
     */
    public static <T> T getData(String name) { // can cause errors, but if it does that's not my fault
        return (T) data.get(name);
    }

    /**
     * A type-safe version of {@link #getData(String)} that ensures the retrieved
     * data is of the expected type
     *
     * @param name The key whose associated data is to be retrieved
     * @param clazz The class type expected for the data
     * @return The data object cast to the specified type
     * @throws ClassCastException if the stored data is not of the expected type
     */
    public static <T> T getData(String name, Class<T> clazz) { // safe version of getData, preferable
        Object obj = data.get(name);
        if (clazz.isInstance(obj)) {
            return clazz.cast(obj);
        } else {
            throw new ClassCastException("Stored data is not of type " + clazz.getName());
        }
    }

    /**
     * Registers a shutdown hook to ensure data is saved when the program terminates
     * This method should be called once during initialization to ensure data persistence
     */
    public static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(PersistentData::saveData));
    }

    /**
     * Saves the current state of the data store to the specified file
     *
     * @return {@code true} if the data was saved successfully, {@code false} otherwise
     */
    public static boolean saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(FILE)))) {
            oos.writeObject(data);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * A wrapper class that holds data of a generic type
     * This class is serializable and allows for easy storage of objects in the data map
     *
     * @param <T> The type of the data to be stored
     */
    public class DataObject<T> {
        private T data;

        /**
         * Constructs a new DataObject to hold the specified data
         *
         * @param data The data to be stored in this object
         */
        public DataObject(T data) {
            this.data = data;
        }

        /**
         * Retrieves the data stored in this object
         *
         * @return The stored data
         */
        public T getData() {
            return data;
        }
    }
}


