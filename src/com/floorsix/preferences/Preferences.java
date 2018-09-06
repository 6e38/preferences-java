
package com.floorsix.preferences;

import java.io.File;

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

  private Preferences(String path, String filename)
  {
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
  }

  public double getDouble(String key)
  {
  }
}

