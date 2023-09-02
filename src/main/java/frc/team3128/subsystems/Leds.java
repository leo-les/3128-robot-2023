package frc.team3128.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.CANdleControlFrame;
import com.ctre.phoenix.led.CANdleStatusFrame;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.RgbFadeAnimation;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.led.LarsonAnimation.BounceMode;
import com.ctre.phoenix.led.TwinkleAnimation.TwinklePercent;
import com.ctre.phoenix.led.TwinkleOffAnimation.TwinkleOffPercent;
import java.awt.Color;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team3128.Constants.LedConstants;

public class Leds extends SubsystemBase {
    private final CANdle m_candle = new CANdle(LedConstants.CANDLE_ID);

    private static Leds instance;

    private int rgb;
    private int red;
    private int green;
    private int blue;
    private int m_rainbowFirstPixelHue = 10;
    private int hue;

    public static Leds getInstance() {
        if (instance == null) {
            instance = new Leds();
        }
        return instance;
    }

    public enum Colors {
        OFF(0,0,0),
        CONE(255,125,0),
        CUBE(255,0,200),
        HOLDING(255,255,255),

        AUTO(255,0,0),
        DRIVER(0,255,0);
        
        private final int r;
        private final int b;
        private final int g;

        Colors(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

    }

    public Leds() {
        configCandle();
    }

    private void configCandle() {
        CANdleConfiguration config = new CANdleConfiguration();
        config.stripType = LEDStripType.RGB;
        config.brightnessScalar = 1;
        m_candle.configAllSettings(config);
    }

    //Set Underglow Leds
    public void setUnderglowLeds(Colors color) {
        m_candle.setLEDs(color.r,color.g,color.b,LedConstants.WHITE_VALUE,LedConstants.STARTING_ID,LedConstants.UNDERGLOW_COUNT);
    }

    //Set Elevator Leds
    public void setElevatorLeds(Colors color) {
        m_candle.setLEDs(color.r,color.g,color.b,LedConstants.WHITE_VALUE,LedConstants.UNDERGLOW_COUNT+1,LedConstants.ELEVATOR_COUNT);
    }

    //Set All Leds
    public void setAllLeds(Colors color) {
        m_candle.setLEDs(color.r,color.g,color.b,LedConstants.WHITE_VALUE,LedConstants.STARTING_ID,LedConstants.UNDERGLOW_COUNT + LedConstants.ELEVATOR_COUNT);
    }

    public void rainbow() {
        for (int i = 0; i < LedConstants.UNDERGLOW_COUNT; i++) {
            hue = (m_rainbowFirstPixelHue + (i * 180 / LedConstants.UNDERGLOW_COUNT)) % 180;
            rgb = hsvToRgb(hue, 255, 128);
            red = (rgb>>16)&0xFF;
            green = (rgb>>8)&0xFF;
            blue = rgb&0xFF;    
            m_candle.setLEDs(red,green,blue,LedConstants.WHITE_VALUE,LedConstants.STARTING_ID+i,1);
        }
        m_rainbowFirstPixelHue += 3;
        m_rainbowFirstPixelHue %= 180;
    }

    public int hsvToRgb(int hue, int saturation, int brightness) {
        return Color.HSBtoRGB(hue, saturation, brightness);
    } 

    
    

}