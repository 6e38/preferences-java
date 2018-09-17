/*
 * Copyright (c) 2018 Nathan Jenne
 */

package com.floorsix.preferences;

import com.floorsix.json.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

abstract class AbstractDatastore
{
  static final String linuxDatastoreFolder = ".datastore-java";
  static final String fileExtension = ".json";

  static String getDatastorePath(String subpath)
  {
    String storePath = System.getProperty("user.home");

    String os = System.getProperty("os.name");

    if (os.matches("^Windows.*"))
    {
      storePath = System.getenv("LOCALAPPDATA");
    }
    else
    {
      storePath += File.separator + linuxDatastoreFolder;
    }

    if (subpath != null)
    {
      storePath += File.separator + subpath;
    }

    File folder = new File(storePath);

    if (!folder.exists())
    {
      folder.mkdirs();
    }

    return storePath;
  }

  protected File f;
  protected JsonObject root;

  protected void initialize(String path, String filename)
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
    root.clear();
    return f.delete();
  }

  public boolean save()
  {
    boolean success;

    try
    {
      FileOutputStream out = new FileOutputStream(f);
      root.toJson(out);
      out.close();
      success = true;
    }
    catch (IOException e)
    {
      success = false;
    }

    return success;
  }

  @Override
  public String toString()
  {
    return root.toString();
  }
}

