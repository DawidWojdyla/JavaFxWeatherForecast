package it.dawidwojdyla.view;

/**
 * Created by Dawid on 2021-01-20.
 */
public class RGBColorGenerator {

    private static final int MAX_COLOR_VALUE = 255;
    private static final int MIN_COLOR_VALUE = 0;

    public static String generateCssStyleColorFromTemperature(int temperature) {
        double red;
        double green;
        double blue;
        double factor = 96 - 0.75 * (40 + temperature);

        if (factor <= 66) {
            red = MAX_COLOR_VALUE;
            green = 99.4708025861 * Math.log(factor) - 161.1195681661;

            if (factor <= 19) {
                blue = MIN_COLOR_VALUE;
            } else {
                blue = factor - 10;
                blue = 138.5177312231 * Math.log(blue) - 305.0447927307;
            }
        } else {

            red = factor - 60;
            red = 329.698727466 * Math.pow(red, -0.1332047592);

            green = factor - 60;
            green = 288.1221695283 * Math.pow(green, -0.0755148492);

            blue = MAX_COLOR_VALUE;
        }

        red = adjustToMinMaxColorValues(red);
        green = adjustToMinMaxColorValues(green);
        blue = adjustToMinMaxColorValues(blue);

        return "rgb(" + red + ", " + green + ", " + blue + ")";
    }

    private static double adjustToMinMaxColorValues(double colorValue) {
        if (colorValue < MIN_COLOR_VALUE || Double.isNaN(colorValue)) {
            colorValue = MIN_COLOR_VALUE;
        } else if (colorValue > MAX_COLOR_VALUE) {
            colorValue = MAX_COLOR_VALUE;
        }
        return colorValue;
    }
}
