package fr.cyberdean.okconfig;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PropertiesTest {
  private Properties config;
  private final File f = new File("config.test");

  @Before
  public void setup() throws IOException {
    if (f.exists()) {
      if (!f.delete()) throw  new RuntimeException("Cannot delete test file");
    }
    config = new Properties(f);
  }


  @Test
  public void testLoadSave() throws IOException {
    assertEquals(0, config.getSize());
    config.setValue("k", 1);
    config.save();

    assertTrue(f.exists());
    config.load();
    assertEquals(1, config.getSize());
    assertTrue(f.delete());
    config.load();
    assertEquals(0, config.getSize());

    assertEquals(0, config.getSize());
    config.setValue("k", 1);
    config.save();
  }

  /* limitation of okjson
 @Test
  public void testObject() throws IOException {
    assertEquals(0, config.getSize());
    config.load();
    assertEquals(0, config.getSize());

    final Byte[] bytes = new Byte[] {0, 1};
    config.setValue("obj", bytes);
    config.save();
    config.load();
    assertEquals(bytes, config.optObject("obj", null));
  }

  @Test
  public void testSet() throws IOException {
    assertEquals(0, config.getSize());
    config.load();
    assertEquals(0, config.getSize());

    final Set<String> bytes = new HashSet<>(Arrays.asList("1", "test 2", "3"));
    config.setValue("obj", bytes);
    config.save();
    config.load();
    assertEquals(bytes, config.optObject("obj", null));
  }*/


  @Test
  public void testString() throws IOException {
    assertEquals(0, config.getSize());
    config.load();
    assertEquals(0, config.getSize());

    config.setValue("str", "default string");
    config.save();
    config.load();
    assertEquals("default string", config.optString("str", null));
  }

  @Test
  public void testBoolean() throws IOException {
    assertEquals(0, config.getSize());
    config.load();
    assertEquals(0, config.getSize());

    config.setValue("boolean", true);
    config.save();
    config.load();
    assertEquals(true, config.optBoolean("boolean", false));

    config.setValue("boolean", false);
    config.save();
    config.load();
    assertEquals(false, config.optBoolean("boolean", true));
    assertEquals(true, config.optBoolean("notExist", true));
    assertEquals(false, config.optBoolean("notExist", false));
  }


  @Test
  public void testInt() throws IOException {
    assertEquals(0, config.getSize());
    config.load();
    assertEquals(0, config.getSize());

    config.setValue("int", 42);
    config.setValue("str", "notValid");
    config.save();
    config.load();
    assertEquals(42, config.optInt("int", 0));
    assertEquals(1, config.optInt("notExist", 1));
    assertEquals(2, config.optInt("str", 2));
  }

  @Test
  public void testFloat() throws IOException {
    assertEquals(0, config.getSize());
    config.load();
    assertEquals(0, config.getSize());

    config.setValue("float", 17.2f);
    config.setValue("str", "notValid");
    config.save();
    config.load();
    assertEquals(17.2f, config.optFloat("float", 0f), 0);
    assertEquals(1.2f, config.optFloat("notExist", 1.2f), 0);
    assertEquals(2, config.optFloat("str", 2), 0);
  }

  @Test
  public void testDouble() throws IOException {
    assertEquals(0, config.getSize());
    config.load();
    assertEquals(0, config.getSize());

    config.setValue("double", 138.123456);
    config.setValue("str", "notValid");
    config.save();
    config.load();
    assertEquals(138.123456, config.optDouble("double", 0), 0);
    assertEquals(1.2, config.optDouble("notExist", 1.2), 0);
    assertEquals(2, config.optDouble("str", 2), 0);
  }

  @Test
  public void testList() throws IOException {
    assertEquals(0, config.getSize());
    config.load();
    assertEquals(0, config.getSize());

    final List<String> list = Arrays.asList("String 1", "String 2", "String 3");
    config.setValue("list", list);
    config.save();
    config.load();
    assertEquals(list, config.optList("list", null));
    assertNull(config.optList("notExist", null));
  }

  @Test
  public void testMap() throws IOException {
    assertEquals(0, config.getSize());
    config.load();
    assertEquals(0, config.getSize());

    final Map<String, Object> map = new HashMap<>();
    map.put("String 1", "value 1");
    map.put("Key 2", 42);
    map.put("Key 3", Arrays.asList("String 1", "String 2", "String 3"));

    config.setValue("map", map);
    config.save();
    config.load();
    assertEquals(map, config.optMap("map", null));
    assertNull(config.optMap("notExist", null));
  }

  @Test
  public void testEquals() throws IOException {
    assertEquals(0, config.getSize());
    assertTrue(config.equals(config));
    assertFalse(config.equals(null));
    assertFalse(config.equals(""));

    final Properties prop = new Properties(f);
    assertTrue(prop.equals(config));
    assertEquals(config.hashCode(), prop.hashCode());

    config.setValue("akey", true);
    assertFalse(prop.equals(config));
    assertNotEquals(config.hashCode(), prop.hashCode());

    prop.setValue("akey", false);
    assertFalse(prop.equals(config));
    assertNotEquals(config.hashCode(), prop.hashCode());

    prop.setValue("akey", true);
    assertTrue(prop.equals(config));
    assertEquals(config.hashCode(), prop.hashCode());
  }

  @Test
  public void testRemove() throws IOException {
    assertEquals(0, config.getSize());
    config.load();
    assertEquals(0, config.getSize());

    assertNull(config.remove("notExist"));
    config.setValue("akey", true);
    assertEquals(1, config.getSize());

    config.save();
    config.load();
    assertEquals(1, config.getSize());

    assertEquals(true, config.remove("akey"));
    assertEquals(0, config.getSize());

    config.save();
    config.load();
    assertEquals(0, config.getSize());
  }

  @Test
  public void testClear() throws IOException {
    assertEquals(0, config.getSize());
    config.load();
    assertEquals(0, config.getSize());

    config.setValue("akey", true);
    assertEquals(1, config.getSize());

    config.save();
    config.load();
    assertEquals(1, config.getSize());

    config.clear();
    assertEquals(0, config.getSize());

    config.save();
    config.load();
    assertEquals(0, config.getSize());
  }

  @Test
  public void testKeys() throws IOException {
    assertEquals(0, config.getSize());

    config.setValue("akey", true);
    config.setValue("key 2", 42);
    config.setValue("Key 3", "oui");
    assertEquals(3, config.getSize());

    assertEquals(3, config.keys().size());
    assertTrue(config.keys().contains("akey"));
    assertTrue(config.keys().contains("key 2"));
    assertTrue(config.keys().contains("Key 3"));
    assertFalse(config.keys().contains("NotExist"));
  }

  @Test
  public void testEntrySet() throws IOException {
    assertEquals(0, config.getSize());

    config.setValue("akey", true);
    config.setValue("key 2", 42);
    config.setValue("Key 3", "oui");
    assertEquals(3, config.getSize());

    assertEquals(3, config.entrySet().size());

    for (final Map.Entry<String, Object> entry : config.entrySet()) {
      if (entry.getKey().equals("akey")) {
        assertEquals(true, entry.getValue());
      }
      else if (entry.getKey().equals("key 2")) {
        assertEquals(42, entry.getValue());
      }
      else if (entry.getKey().equals("Key 3")) {
        assertEquals("oui", entry.getValue());
      }
      else {
        fail("Unknown key : " + entry.getKey());
      }
    }

  }
}
