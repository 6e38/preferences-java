/*
 * Copyright (c) 2018 Nathan Jenne
 */

package com.floorsix.preferences;

import com.floorsix.json.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Test
{
  public static void main(String[] args)
  {
    Test test = new Test();
    test.runTests();
  }

  private void runTests()
  {
    try
    {
      invokeTests();
      System.out.println("\nPASS: all tests succeeded");
    }
    catch (AssertionError e)
    {
      System.out.print("\nTest failure");

      if (e.getMessage() != null)
      {
        System.out.print(": " + e.getMessage());
      }

      System.out.println();

      for (StackTraceElement trace : e.getStackTrace())
      {
        System.out.println(">> " + trace);
      }

      System.out.println("\nFAIL");
    }
  }

  private void invokeTests() throws AssertionError
  {
    Class c = this.getClass();
    Method[] methods = c.getDeclaredMethods();

    for (Method m : methods)
    {
      if (m.getName().matches("^test.*"))
      {
        System.out.print("Running: " + m.getName() + " ");

        try
        {
          m.invoke(this);
          System.out.println("PASS");
        }
        catch (IllegalAccessException e)
        {
          System.out.println("FAIL");
          System.out.println(e);
        }
        catch (InvocationTargetException e)
        {
          System.out.println("FAIL");
          if (e.getCause() != null && e.getCause() instanceof AssertionError)
          {
            throw (AssertionError)e.getCause();
          }
          else
          {
            System.out.println(e.getCause());
          }
        }
      }
    }
  }

  private void testPreferences()
  {
    Preferences p = Preferences.getUserPreferences(Test.class);
    p.delete();

    p = Preferences.getUserPreferences(Test.class);
    assert p.toString().equals("{}");
    p.commit();

    p = Preferences.getUserPreferences(Test.class);
    assert p.toString().equals("{}");
    p.setString("k1", "s1");
    assert p.toString().equals("{\n\"k1\": \"s1\"\n}");
    p.commit();

    p = Preferences.getUserPreferences(Test.class);
    assert p.getString("k1", "x").equals("s1");
    assert p.getString("k2", "x").equals("x");
    p.setString("k1", "s3");
    p.setString("k2", "s2");
    p.commit();

    p = Preferences.getUserPreferences(Test.class);
    assert p.getString("k1", "x").equals("s3");
    assert p.getString("k2", "x").equals("s2");
    p.setDouble("k3", 3);
    p.setDouble("k1", 1);
    p.commit();

    p = Preferences.getUserPreferences(Test.class);
    assert p.getString("k1", "x").equals("x");
    assert p.getString("k2", "x").equals("s2");
    assert (int)p.getDouble("k1", -1) == 1;
    assert (int)p.getDouble("k3", -1) == 3;
    assert (int)p.getDouble("k2", -1) == -1;
    ArrayList<String> list = new ArrayList<String>();
    list.add("s1");
    list.add("s2");
    list.add("s3");
    p.setList("k4", list);
    p.commit();

    p = Preferences.getUserPreferences(Test.class);
    List<String> l = p.getList("k4");
    assert l != null;
    assert p.getList("k5") != null;
    assert p.getList("k5").size() == 0;
    assert l.size() == 3;
    assert l.get(0).equals("s1");
    assert l.get(1).equals("s2");
    assert l.get(2).equals("s3");
  }

  private void testDatastore1()
  {
    Datastore d = Datastore.getDatastore(getClass(), "stuff");
    d.delete();
    d.save();
    assert d.toString().equals("{}") : d;
    d.set(new JsonString("k1", "v1"));
    assert d.get("k1") instanceof JsonString;
    assert ((JsonString)d.get("k1")).get().equals("v1");
    d.save();
  }
}

