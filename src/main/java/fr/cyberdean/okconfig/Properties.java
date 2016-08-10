package fr.cyberdean.okconfig;

import info.jdavid.ok.json.Builder;
import info.jdavid.ok.json.Parser;
import okio.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Properties file manager (like {@link java.util.Properties} but better)
 * @author Dean79000
 */
public class Properties {
  private File mFile;
  private Map<String, Object> mParams;

  /**
   * Constructor
   * @param file File used to store configuration
   * @throws IOException If fail to create file (if not exist)
   */
  public Properties(final File file) throws IOException {
    if (!file.exists()) {
      file.createNewFile();
    }
    mFile = file;
    mParams = new HashMap<>();
  }

  /**
   * Load properties from file (if exist)
   */
  public void load() {
    if (mFile.exists() && mFile.length() > 0) {
      try {
        mParams = Parser.parse(Okio.buffer(Okio.source(mFile)));
      }
      catch (FileNotFoundException ignore) {}
    }
    else {
      mParams = new HashMap<>();
    }
  }

  /**
   * Save properties (erase file or create it)
   * @throws IOException If fail to create file
   */
  public void save() throws IOException {
    if (!mFile.exists()) {
      mFile.createNewFile();
    }
    Builder.build(Okio.buffer(Okio.sink(mFile)), mParams);
  }

  /**
   * Removes all of the properties.
   * Will be empty after this call returns.
   */
  public void clear() {
    mParams.clear();
  }

  /**
   * Get number of parameters of actual properties (in-memory)
   * @return The number of parameters
   */
  public int getSize() {
    return mParams.size();
  }

  /**
   * Returns a {@link Set} of all keys.
   * @return All keys
   * @see Map#keySet()
   */
  public Set<String> keys() {
    return mParams.keySet();
  }

  /**
   * Returns a {@link Set} view of the mappings.
   * @return a set view of the mappings
   * @see Map#entrySet()
   */
  public Set<Map.Entry<String, Object>> entrySet() {
    return mParams.entrySet();
  }

  /**
   * Remove mapping key/value if exist
   * @param key Identifier of property.
   * @return the previous value associated with key, or null if there was no mapping for key.
   * @see Map#remove(Object)
   */
  public Object remove(final String key) {
    return mParams.remove(key);
  }

  /**
   * Get an Object
   * @param key Identifier of property. Must be unique !
   * @param defaultValue Default value if named key not exist
   * @return value or defaultValue if key not exist
   */
  public Object optObject(final String key, final Object defaultValue) {
    return mParams.containsKey(key) ? mParams.get(key) : defaultValue;
  }

  /**
   * Get an String
   * @param key Identifier of property. Must be unique !
   * @param defaultValue Default value if named key not exist
   * @return value or defaultValue if key not exist
   */
  public String optString(final String key, final String defaultValue) {
    final Object obj = optObject(key, defaultValue);
    return (obj instanceof String) ? (String)obj : defaultValue;
  }

  /**
   * Get an boolean
   * @param key Identifier of property. Must be unique !
   * @param defaultValue Default value if named key not exist
   * @return value or defaultValue if key not exist
   */
  public boolean optBoolean(final String key, final boolean defaultValue) {
    final Object obj = optObject(key, defaultValue);
    return (obj instanceof Boolean) ? (Boolean)obj : defaultValue;
  }

  /**
   * Get an Integer
   * @param key Identifier of property. Must be unique !
   * @param defaultValue Default value if named key not exist
   * @return value or defaultValue if key not exist
   */
  public int optInt(final String key, final int defaultValue) {
    if (mParams.containsKey(key)) {
      try {
        return Integer.parseInt(mParams.get(key).toString());
      }
      catch (final NumberFormatException ignore) {}
    }
    return defaultValue;
  }

  /**
   * Get an float
   * @param key Identifier of property. Must be unique !
   * @param defaultValue Default value if named key not exist
   * @return value or defaultValue if key not exist
   */
  public float optFloat(final String key, final float defaultValue) {
    if (mParams.containsKey(key)) {
      try {
        return Float.parseFloat(mParams.get(key).toString());
      }
      catch (final NumberFormatException ignore) {}
    }
    return defaultValue;
  }

  /**
   * Get an double
   * @param key Identifier of property. Must be unique !
   * @param defaultValue Default value if named key not exist
   * @return value or defaultValue if key not exist
   */
  public double optDouble(final String key, final double defaultValue) {
    if (mParams.containsKey(key)) {
      try {
        return Double.parseDouble(mParams.get(key).toString());
      }
      catch (final NumberFormatException ignore) {}
    }
    return defaultValue;
  }

  /**
   * Get an List of Object
   * @param key Identifier of property. Must be unique !
   * @param defaultValue Default value if named key not exist
   * @return value or defaultValue if key not exist
   */
  public List<Object> optList(final String key, final List<Object> defaultValue) {
    final Object obj = optObject(key, defaultValue);
    return (obj instanceof List) ? (List<Object>)obj : defaultValue;
  }

  /**
   *  Get an Map of String, Object
   * @param key Identifier of property. Must be unique !
   * @param defaultValue Default value if named key not exist
   * @return value or defaultValue if key not exist
   */
  public Map<String, Object> optMap(final String key, final Map<String, Object> defaultValue) {
    final Object obj = optObject(key, defaultValue);
    return (obj instanceof Map) ? (Map<String, Object>)obj : defaultValue;
  }

  /**
   * Define new or edit property
   * @param key Identifier of property. Must be unique !
   * @param value Value  Support ONLY : String, Number, Boolean, CharSequence, Map, List
   */
  public void setValue(final String key, final Object value) {
    mParams.put(key, value);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Properties that = (Properties) o;

    if (mFile != null ? !mFile.equals(that.mFile) : that.mFile != null) return false;
    return mParams != null ? mParams.equals(that.mParams) : that.mParams == null;
  }

  @Override
  public int hashCode() {
    int result = mFile != null ? mFile.hashCode() : 0;
    result = 31 * result + (mParams != null ? mParams.hashCode() : 0);
    return result;
  }
}
