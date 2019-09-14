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
  private static Preferences s_prefs = null;

  public static synchronized Preferences getUserPreferences(Class c)
  {
    if (s_prefs != null)
    {
      return s_prefs;
    }

    String prefsPath = getDatastorePath(prefsFolder);

    String filename = c.getName() + fileExtension;

    s_prefs = new Preferences(prefsPath, filename);

    String relocatedPath = s_prefs.getString("__relocated__", null);
    if (relocatedPath != null)
    {
      s_prefs = new Preferences(relocatedPath, filename);
    }

    return s_prefs;
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

  public void setInt(String key, int value)
  {
    root.set(key, value);
  }

  public void setBoolean(String key, boolean value)
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

  public int getInt(String key, int defaultValue)
  {
    Json json = root.get(key);

    if (json instanceof JsonNumber)
    {
      return (int)((JsonNumber)json).get();
    }
    else
    {
      return defaultValue;
    }
  }

  public boolean getBoolean(String key, boolean defaultValue)
  {
    Json json = root.get(key);

    if (json instanceof JsonBoolean)
    {
      return ((JsonBoolean)json).get();
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
}

