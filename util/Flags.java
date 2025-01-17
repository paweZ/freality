package util;

/**
 * Helper class to assemble flags in bulk then check them once.
 * Usage:
 *
 *   // Required flags have no default specified.
 *   String first = Flags.get("first");
 *   String last = Flags.get("last");
 *   // Optional flags have are called with a default value, which can
 *   // be null.
 *   String middle = Flags.getOpt("middle", null);
 *   if (!Flags.check("Must specify first and last name.")) { return; }
 *
 * NOTE: See static init for shorthand definitions.
 *
 * @author Pablo Mayrgundter
 */
public class Flags {

  static boolean flagsOk = true;

  public static void clear() {
    flagsOk = true;
  }

  public static boolean check(String msg) {
    if (flagsOk) {
      return true;
    }
    System.err.println(msg);
    return false;
  }

  static String propName(Class clazz, String name) {
    return clazz.getName() + "." + name;
  }

  // Boolean.
  public static boolean bool(Class clazz, String name) {
    return Boolean.getBoolean(propName(clazz, name));
  }

  // String flags.

  public static String get(String name) {
    String val = System.getProperty(name);
    if (val == null) {
      flagsOk = false;
    }
    return null;
  }

  public static String get(String name, String defaultVal) {
    return System.getProperty(name, defaultVal);
  }

  // Int flags.

  public static int getInt(String name) {
    return Integer.parseInt(get(name, "0"));
  }

  public static int getInt(String name, int defaultVal) {
    String strVal = System.getProperty(name);
    if (strVal != null) {
      return Integer.parseInt(strVal);
    }
    return defaultVal;
  }

  // Boolean flags.

  public static boolean getBool(String name) {
    return Boolean.parseBoolean(get(name, "false"));
  }

  public static boolean getBool(String name, boolean defaultVal) {
    String strVal = System.getProperty(name);
    if (strVal != null) {
      return Boolean.parseBoolean(strVal);
    }
    return defaultVal;
  }

  // Double flags.

  public static double getDouble(String name) {
    return Double.parseDouble(get(name, "0"));
  }

  public static double getDouble(String name, double defaultVal) {
    String strVal = System.getProperty(name);
    if (strVal != null) {
      return Double.parseDouble(strVal);
    }
    return defaultVal;
  }

  Class clazz;

  public Flags(Class clazz) {
    this.clazz = clazz;
  }

  public <T> T get(String propName, T defVal) {
    return get(propName, null, defVal);
  }

  /**
   * Return type inference is done on the default value.  For
   * instance, if you want a long value, the default value must be
   * or be cast to a long.
   */
  @SuppressWarnings("unchecked")
  public <T> T get(String propName, String abbrevPropName, T defVal) {
    String qName = clazz.getName() + "." + propName;
    String val = System.getProperty(qName);

    if (val == null && abbrevPropName != null) {
      val = System.getProperty(abbrevPropName);
    }

    if (val == null) {
      val = defVal + "";
    }

    System.out.println(qName + "=" + val);

    if (val == null)
      return defVal;
    else if (defVal instanceof Integer)
      return (T) new Integer(val);
    else if (defVal instanceof Long)
      return (T) new Long(val);
    else if (defVal instanceof Float)
      return (T) new Float(val);
    else if (defVal instanceof Double)
      return (T) new Double(val);
    else if (defVal instanceof Boolean)
      return (T) new Boolean(val);
    //      if (!(val instanceof defVal))
    //        throw new IllegalArgumentException("Illegal value for flag: " + val);
    return (T) val;
  }
}
