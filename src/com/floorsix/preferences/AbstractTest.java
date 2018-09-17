/*
 * Copyright (c) 2018 Nathan Jenne
 */

package com.floorsix.preferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractTest
{
  protected void test()
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
    catch (Exception e)
    {
      System.out.println("System failure: " + e);
      System.out.println("\nFAIL");
    }
  }

  private void invokeTests() throws Exception
  {
    Class c = getClass();
    Method[] methods = c.getDeclaredMethods();

    for (Method m : methods)
    {
      if (m.getName().matches("^test.+"))
      {
        System.out.print("Running: " + m.getName() + " ");

        m.setAccessible(true);

        try
        {
          m.invoke(this);
          System.out.println("PASS");
        }
        catch (IllegalAccessException e)
        {
          System.out.println("FAIL");
          throw e;
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
            throw e;
          }
        }
      }
    }
  }
}

