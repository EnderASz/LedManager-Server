package com.enderasz.ledmanager.server.domain;

public class FlashingLightConfig implements LightConfig {
    private int red;
    private int green;
    private int blue;
    private int timeLength;
    private int timeInterval;

    public FlashingLightConfig() {
        this(0,0,0, 1000, 1000);
    }

    public FlashingLightConfig(final int red, final int green, final int blue, final int timeLength, final int timeInterval) {
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

    public int getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(final int timeLength) {
        this.timeLength = timeLength;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(final int timeInterval) {
        this.timeInterval = timeInterval;
    }

    @Override
    public String toPlainText() {
        StringBuilder sb = new StringBuilder();
        sb.append(red);
        sb.append("\t");
        sb.append(green);
        sb.append("\t");
        sb.append(blue);
        sb.append("\t");
        sb.append(timeLength);
        sb.append("\t");
        sb.append(timeInterval);
        return sb.toString();
    }

    @Override
    public String toString() {
        return super.toString() + "{" + "red=" + red + ", green=" + green + ", blue=" + blue + ", timeLength=" + timeLength + ", timeInterval=" + timeInterval + '}';
    }
}
