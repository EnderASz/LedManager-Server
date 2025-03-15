package com.enderasz.ledmanager.server.domain;

public class ConstLightConfig implements LightConfig {
    private int red;
    private int green;
    private int blue;

    public ConstLightConfig() {
        this(0,0,0);
    }

    public ConstLightConfig(final int red, final int green, final int blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }


    public int getRed() {
        return red;
    }

    public void setRed(final int red) {
        if(red < 0 || red > 255) {
            throw new IllegalArgumentException("Invalid red value: " + red);
        }
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(final int green) {
        if(red < 0 || red > 255) {
            throw new IllegalArgumentException("Invalid red value: " + red);
        }
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(final int blue) {
        if(red < 0 || red > 255) {
            throw new IllegalArgumentException("Invalid red value: " + red);
        }
        this.blue = blue;
    }

    @Override
    public String toPlainText() {
        StringBuilder sb = new StringBuilder();
        sb.append(red);
        sb.append("\t");
        sb.append(green);
        sb.append("\t");
        sb.append(blue);
        return sb.toString();
    }

    @Override
    public String toString() {
        return super.toString() + "{" + "red=" + red + ", green=" + green + ", blue=" + blue + '}';
    }
}
