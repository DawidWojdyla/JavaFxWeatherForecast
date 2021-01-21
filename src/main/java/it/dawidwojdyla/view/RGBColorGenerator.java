package it.dawidwojdyla.view;

/**
 * Created by Dawid on 2021-01-20.
 */
public class RGBColorGenerator {

    public static String generateColorFromTemperature(int temperature) {
        double red;
        double green;
        double blue;
        double factor = 96 - 0.75 * (40 + temperature);

        if (factor <= 66) {
            red = 255;
            green = 99.4708025861 * Math.log(factor) - 161.1195681661;

            if (factor <= 19) {
                blue = 0;
            } else {
                blue = factor - 10;
                blue = 138.5177312231 * Math.log(blue) - 305.0447927307;
            }
        } else {

            red = factor - 60;
            red = 329.698727466 * Math.pow(red, -0.1332047592);

            green = factor - 60;
            green = 288.1221695283 * Math.pow(green, -0.0755148492);

            blue = 255;
        }

        if (red < 0) {
            red = 0;
        } else if(red > 255) {
            red = 255;
        }

        if (green < 0 || Double.isNaN(green)) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }

        if (blue < 0 || Double.isNaN(blue)) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }

        return "rgb(" + (int) red + ", " + (int) green + ", " + (int) blue + ")";
    }
}
