package org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools;

import static java.lang.Math.round;

import androidx.annotation.ColorInt;

import org.firstinspires.ftc.teamcode.backend.subsystems.Constants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * EaseCommands Object, Group of methods that are fundamental and may be useful in making programming easier
 */
public class EaseCommands {
    private static final Constants c = new Constants();
    // literally just anything to make life easier
    // if imported static you can just use it in your code
    // just call import static org.firstinspires.ftc.teamcode.subsystems.ease_commands.*;

    public enum Colors {
        RED, GREEN, BLUE,
        YELLOW, CYAN, MAGENTA, ORANGE, PURPLE,
        BLACK, WHITE, GRAY, LIGHT_GRAY, DARK_GRAY, SILVER,
        PINK, BROWN, MAROON, OLIVE, ARMY_GREEN, TEAL, NAVY
    }

    /**
     * Colors to use for color detection code
     */
    public static final HashMap<Colors, int[]> basicColors = new HashMap<>();

    static {
        // primary Colors
        basicColors.put(Colors.RED, new int[]{255, 0, 0});
        basicColors.put(Colors.GREEN, new int[]{0, 255, 0});
        basicColors.put(Colors.BLUE, new int[]{0, 0, 255});
        // secondary Colors
        basicColors.put(Colors.YELLOW, new int[]{255, 255, 0});
        basicColors.put(Colors.CYAN, new int[]{0, 255, 255});
        basicColors.put(Colors.MAGENTA, new int[]{255, 0, 255});
        basicColors.put(Colors.ORANGE, new int[]{255, 165, 0});
        basicColors.put(Colors.PURPLE, new int[]{128, 0, 128});
        // neutrals
        basicColors.put(Colors.BLACK, new int[]{0, 0, 0});
        basicColors.put(Colors.WHITE, new int[]{255, 255, 255});
        basicColors.put(Colors.GRAY, new int[]{128, 128, 128});
        basicColors.put(Colors.LIGHT_GRAY, new int[]{211, 211, 211});
        basicColors.put(Colors.DARK_GRAY, new int[]{169, 169, 169});
        basicColors.put(Colors.SILVER, new int[]{192, 192, 192});
        // other Colors
        basicColors.put(Colors.PINK, new int[]{255, 192, 203});
        basicColors.put(Colors.BROWN, new int[]{165, 42, 42});
        basicColors.put(Colors.MAROON, new int[]{128, 0, 0});
        basicColors.put(Colors.OLIVE, new int[]{128, 128, 0});
        basicColors.put(Colors.ARMY_GREEN, new int[]{0, 128, 0});
        basicColors.put(Colors.TEAL, new int[]{0, 128, 128});
        basicColors.put(Colors.NAVY, new int[]{0, 0, 128});
    }

    // math based functions

    /**
     * Returns the sum of the list of values
     *
     * @param i Iterable object containing numbers
     * @return Sum of list
     */
    public static <T extends Number> double sumAll(Iterable<T> i) {
        double o = 0;
        for (T x : i) {
            o += x.doubleValue();
        }
        return o;
    }

    /**
     * Returns the smallest value of the list of values
     *
     * @param i Iterable comparable object containing numbers
     * @return Smallest value of the list
     */
    public static <T extends Number & Comparable<T>> T minAll(Iterable<T> i) {
        Iterator<T> iterator = i.iterator();
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException("Iterable is empty");
        }
        T o = iterator.next();
        for (T value : i) {
            if (value.compareTo(o) < 0) {
                o = value;
            }
        }
        return o;
    }

    /**
     * Returns the largest value of the list of values
     *
     * @param i Iterable comparable object containing numbers
     * @return Largest value of the list
     */
    public static <T extends Number & Comparable<T>> T maxAll(Iterable<T> i) {
        Iterator<T> iterator = i.iterator();
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException("Iterable is empty");
        }

        T o = iterator.next();
        for (T value : i) {
            if (value.compareTo(o) > 0) {
                o = value;
            }
        }
        return o;
    }

    /**
     * Returns the first index of a given value in a list
     *
     * @param flag What we want to find the index of, must be comparable
     * @param arr  Iterable list containing elements
     * @return Index of the value in the list, or -1 if not found
     */
    public static <T extends Comparable<T>> Optional<Integer> findInArr(T flag, Iterable<T> arr) {
        int i = 0;
        for (T x : arr) {
            if (x.compareTo(flag) == 0) {
                return Optional.of(i);
            }
            i++;
        }
        return Optional.empty();
    }

    /**
     * Method to calculate the similarity of an RGBA color to basic colors.
     *
     * @param r Red component (0-255)
     * @param g Green component (0-255)
     * @param b Blue component (0-255)
     * @return The name of the closest basic color
     */
    public static Colors findClosestColor(@ColorInt int r, @ColorInt int g, @ColorInt int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException("RGB values must be between 0 and 255.");
        }
        double minDistance = Double.MAX_VALUE;
        Colors closestColor = null;

        for (Map.Entry<Colors, int[]> entry : basicColors.entrySet()) {
            int[] color = entry.getValue();
            double distance = Math.sqrt( // euclidean distance
                    Math.pow(r - color[0], 2) +
                            Math.pow(g - color[1], 2) +
                            Math.pow(b - color[2], 2)
            );

            if (distance < minDistance) {
                minDistance = distance;
                closestColor = entry.getKey();
            }
        }

        return closestColor;
    }

    /**
     * Tells you how close to a given color RGB values are
     *
     * @param targetRGB Given color values, insert own or use the basicColors HashMap
     * @param r         The value of red [0-255]
     * @param b         The value of blue [0-255]
     * @param g         The value of green [0-255]
     * @return Similarity
     */
    public static double calculateColorSimilarity(int[] targetRGB, @ColorInt int r, @ColorInt int g, @ColorInt int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException("RGB values must be between 0 and 255.");
        }
        return Math.sqrt(Math.pow(r - targetRGB[0], 2) + Math.pow(g - targetRGB[1], 2) + Math.pow(b - targetRGB[2], 2));
    }

    /**
     * Returns a value of ticks to travel based of the conversion factor defined in Constants class
     *
     * @param inches The number you want to travel in inches
     * @return Integer of ticks to get to target distance
     */
    public static int inTT_dt(double inches) {
        return (int) round(c.conversion_factor_dt * inches);
    }

    public static <T> T autoCast(Object obj) {
        return (T) obj;
    }
}
