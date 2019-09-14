/*
 * Copyright (c) 2018 Nathan Jenne
 */

package com.floorsix.preferences;

import com.floorsix.json.*;

public class Datastore extends AbstractDatastore
{
  private Datastore(String path, String filename)
  {
    initialize(path, filename);
  }

  public void set(Json value)
  {
    root.set(value);
  }

  public Json get(String key)
  {
    return root.get(key);
  }
}

