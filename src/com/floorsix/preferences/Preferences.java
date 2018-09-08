
package com.floorsix.preferences;

import com.floorsix.json.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Preferences
{
  private static final String prefsFolder = ".java-user-prefs";
  private static final String prefsSuffix = ".json";

  public static Preferences getUserPreferences(Class c)
  {
    String className = c.getName();

    String filename = className + prefsSuffix;

    String homePath = System.getProperty("user.home");

    String prefsPath = homePath + File.separator + prefsFolder;

    File prefsFolder = new File(prefsPath);

    if (!prefsFolder.exists())
    {
      prefsFolder.mkdir();
    }

    return new Preferences(prefsPath, filename);
  }

  private File f;
  private JsonObject root;

  private Preferences(String path, String filename)
  {
    f = new File(path + File.separator + filename);

    if (f.exists())
    {
      try
      {
        FileInputStream in = new FileInputStream(f);
        root = JsonParser.parse(in);

        try
        {
          in.close();
        }
        catch (IOException e)
        {
          // failed to close, ignore
        }
      }
      catch (InvalidJsonException e)
      {
        root = new JsonObject(null);
      }
      catch (FileNotFoundException e)
      {
        root = new JsonObject(null);
      }
    }
    else
    {
      root = new JsonObject(null);
    }
  }

  public boolean delete()
  {
    boolean success;

    success = f.delete();

    return success;
  }

  public boolean commit()
  {
    boolean success;

    try
    {
      FileWriter out = new FileWriter(f);
      String json = root.toJson();
      out.write(json, 0, json.length());
      out.close();
      success = true;
    }
    catch (IOException e)
    {
      success = false;
    }

    return success;
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

