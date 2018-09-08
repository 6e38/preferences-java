
package com.floorsix.preferences;

import com.floorsix.json.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Preferences
{
  static final String prefsFolder = ".java-user-prefs";
  static final String prefsSuffix = ".json";

  static Preferences getUserPreferences(Class c)
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
        root = JsonParser.parse(new FileInputStream(f));

        System.out.println("Read file: " + root);
      }
      catch (InvalidJsonException e)
      {
        root = new JsonObject(null);

        System.out.println("Could not read file: " + root);
      }
      catch (FileNotFoundException e)
      {
        root = new JsonObject(null);
      }
    }
    else
    {
      root = new JsonObject(null);

      System.out.println("No file: " + root);
    }
  }

  public void commit()
  {
  }

  public void set(String key, String value)
  {
  }

  public void set(String key, double value)
  {
  }

  public String get(String key)
  {
    return null;
  }

  public double getDouble(String key)
  {
    return 0;
  }
}

