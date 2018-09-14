/*
 * Copyright (c) 2018 Nathan Jenne
 */

package com.floorsix.preferences;

import com.floorsix.json.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Preferences extends AbstractDatastore
{
  private static final String prefsFolder = "Preferences-java";

  public static Preferences getUserPreferences(Class c)
  {
    String prefsPath = getDatastorePath(prefsFolder);

    String filename = c.getName() + fileExtension;

    return new Preferences(prefsPath, filename);
  }

  private Preferences(String path, String filename)
  {
    initialize(path, filename);
  }

  public boolean commit()
  {
    return save();
  }

  public void setString(String key, String value)
  {
    root.set(key, value);
  }

  public void setDouble(String key, double value)
  {
    root.set(key, value);
  }

  public void setList(String key, List<String> value)
  {
    JsonArray array = root.setArray(key);

    for (String s : value)
    {
      array.add(s);
    }
  }

  public String getString(String key, String defaultValue)
  {
    Json json = root.get(key);

    if (json instanceof JsonString)
    {
      return ((JsonString)json).get();
    }
    else
    {
      return defaultValue;
    }
  }

  public double getDouble(String key, double defaultValue)
  {
    Json json = root.get(key);

    if (json instanceof JsonNumber)
    {
      return ((JsonNumber)json).get();
    }
    else
    {
      return defaultValue;
    }
  }

  public List<String> getList(String key)
  {
    ArrayList<String> list = new ArrayList<String>();

    Json json = root.get(key);

    if (json instanceof JsonArray)
    {
      List<Json> l = ((JsonArray)json).getArray();

      for (Json j : l)
      {
        if (j instanceof JsonString)
        {
          list.add(((JsonString)j).get());
        }
      }
    }

    return list;
  }

  @Override
  public String toString()
  {
    return root.toJson();
  }
}

