package com.snake.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.snake.game.util.Vector;

public class InputBox {
  Vector location, size;
  ArrayList<Character> value = new ArrayList<Character>();
  boolean canType, oneChar, hasBack, disabled;

  // type = 0 er for strings, type = 1 er for tal og type = 2 er for passwords
  String combValue;
  int type;
  float intValue;
  ArrayList<Integer> num = new ArrayList<Integer>();
  boolean justClicked = false; 

  BitmapFont inputFont;

  String defaultString = "Type here";
  String defaultDisplayString;
  String unit = "";
  int framesSinceActive = 0;



  InputBox() {
    this(new Vector(100, 100), new Vector(150, 150), 0);
  }

  InputBox(Vector location, Vector size, int type) {
    this(location, size, type, "Type here");
  }
  
  InputBox(Vector location, Vector size, int type, String defaultString) {
    this(location, size, type, defaultString, "");
  }

  InputBox(Vector location, Vector size, int type, String defaultString, String unit) {
    this(location, size, type, defaultString, unit, false);
  }
  InputBox(Vector location, Vector size, int type, String defaultString, String unit, boolean manualSize) {
    this(location, size, type, defaultString, unit, false,  createFont("./font/pixel.ttf", 16, false));
  }
  InputBox(Vector location, Vector size, int type, String defaultString, String unit, boolean manualSize, BitmapFont customFont) {
    this.location = location;
    this.size = size;
    this.type = type;
    this.defaultString = defaultString;
    this.unit = unit;
    oneChar = true;
    combValue = "";
    defaultDisplayString = defaultString;

    this.inputFont = customFont;
    if (!manualSize) {
      //this.size = new Vector(textWidth(defaultString) + 10, size.y);
    }

    insNum();
  }

  void insNum() {
    for (int i = 0; i < 11; i++) {
      num.add(i);
    }
  }

  void display() {
    fill(0);
    rect(location.x - 3, location.y - 3, size.x + 6, size.y + 6);
    fill(255);
    rect(location.x, location.y, size.x, size.y);
    fill(0);
    textFont(inputFont);
    if (combValue.length() > 0) {
      text(combValue, location.x + 5, location.y + size.y/2);

    } else {
      text(defaultDisplayString, location.x + 5, location.y + size.y/2);
    }

    //Display Unit
    if (combValue.length() > 0 || canType) {
      text(unit, location.x + 5 + textWidth(combValue) + 5, location.y + size.y/2);
    }

    //Display cursor
    if (canType && framesSinceActive % 30 < 15) {
      rect(location.x + 5 + textWidth(combValue), location.y + size.y/2, 2, 20);
    }
    
  }

  void update() {
    writing();
  }

  boolean mouseInBox() {
    if (v >= location.x && mouseX <= location.x + size.x) {
      if (mouseY >= location.y && mouseY <= location.y + size.y) {
        return true;
      }
    }
    return false;
  }

  void writing() {
    
    if (keyPressed == false) {
      oneChar = true;
    }

    if (mouseInBox() && mousePressed && !disabled) {
      canType = true;
      if (justClicked == false) {
        defaultDisplayString = "";
        justClicked = true;
      }
      
    } else if (mouseInBox() == false && mousePressed && !disabled) {
      canType = false;
      if (justClicked) {
        defaultDisplayString = defaultString;
        justClicked = false;
      }
    }

    if (canType) {
      framesSinceActive++;

      if (keyPressed == true && oneChar) {
        if (key == BACKSPACE && value.size() > 0) {
          oneChar = false;
          value.remove(value.size() - 1);
          combValue = getValueString(value);
        }

        if (key != BACKSPACE) {
          value.add(key);
          oneChar = false;
          combValue = getValueString(value);
          try {
            if ((key == '.' && value.contains('.') == false) || key != '.') {
              if (type == 1 && num.contains(Integer.parseInt(String.valueOf(key)))) {
                for (int i = 0; i < combValue.length(); i++) {
                }
                intValue = Float.parseFloat(combValue);
              }
            }
          }
          catch(Exception e) {
            value.remove(value.size() - 1);
            combValue = getValueString(value);
          }
        }
      }
    }
  }

  String getValueString(ArrayList<Character> values)
  {
    StringBuilder builder = new StringBuilder(values.size());
    for (Character ch : values)
    {
      builder.append(ch);
    }
    return builder.toString();
  }
}