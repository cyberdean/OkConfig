package fr.cyberdean.okconfig;

import info.jdavid.ok.json.Builder;
import info.jdavid.ok.json.Parser;
import okio.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration file manager (like {@link java.util.Properties} but better)
 * @author Dean79000
 */
public class Configuration {
  private File mFile;
  private Map<String, Object> mParams;

  /**
   * Constructor
   * @param file File used to store configuration
   * @throws IOException If fail to create file (if not exist)
   */
  public Configuration(final File file) throws IOException {
    if (!file.exists()) {
      file.createNewFile();
    }
    mFile = file;
    mParams = new HashMap<>();
  }

  /**
   * Load configuration from file (if exist)
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
   * Save configuration (erase file or create it)
   * @throws IOException If fail to create file
   */
  public void save() throws IOException {
    if (!mFile.exists()) {
      mFile.createNewFile();
    }
    Builder.build(Okio.buffer(Okio.sink(mFile)), mParams);
  }

  /**
   * Get number of parameters of actual config (in-memory)
   * @return The number of parameters
   */
  public int getSize() {
    return mParams.size();
  }

  /**
   * Get an Object from parameters
   * @param key Identifier of parameter. Must be unique !
   * @param defaultValue Default value if named key not exist
   * @return value or defaultValue if key not exist
   */
  public Object optObject(final String key, final Object defaultValue) {
    return mParams.containsKey(key) ? mParams.get(key) : defaultValue;
  }

  /**
   * Get an String from parameters
   * @param key Identifier of parameter. Must be unique !
   * @param defaultValue Default value if named key not exist
   * @return value or defaultValue if key not exist
   */
  public String optString(final String key, final String defaultValue) {
    final Object obj = optObject(key, defaultValue);
    return (obj instanceof String) ? (String)obj : defaultValue;
  }

  /**
   * Get an boolean from parameters
   * @param key Identifier of parameter. Must be unique !
   * @param defaultValue Default value if named key not exist
   * @return value or defaultValue if key not exist
   */
  public boolean optBoolean(final String key, final boolean defaultValue) {
    final Object obj = optObject(key, defaultValue);
    return (obj instanceof Boolean) ? (Boolean)obj : defaultValue;
  }

  /**
   * Get an Integer from parameters
   * @param key Identifier of parameter. Must be unique !
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
   * Get an float from parameters
   * @param key Identifier of parameter. Must be unique !
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
   * Get an double from parameters
   * @param key Identifier of parameter. Must be unique !
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
   * Get an List of Object from parameters
   * @param key Identifier of parameter. Must be unique !
   * @param defaultValue Default value if named key not exist
   * @return value or defaultValue if key not exist
   */
  public List<Object> optList(final String key, final List<Object> defaultValue) {
    final Object obj = optObject(key, defaultValue);
    return (obj instanceof List) ? (List<Object>)obj : defaultValue;
  }

  /**
   *  Get an Map of String, Object from parameters
   * @param key Identifier of parameter. Must be unique !
   * @param defaultValue Default value if named key not exist
   * @return value or defaultValue if key not exist
   */
  public Map<String, Object> optMap(final String key, final Map<String, Object> defaultValue) {
    final Object obj = optObject(key, defaultValue);
    return (obj instanceof Map) ? (Map<String, Object>)obj : defaultValue;
  }

  /**
   * Define new or edit parameter
   * @param key Identifier of parameter. Must be unique !
   * @param value Value
   */
  public void setValue(final String key, final Object value) {
    mParams.put(key, value);
  }
}
