![jcenter](https://img.shields.io/badge/_jcenter_-_1.0-6688ff.png?style=flat) &#x2003; ![jcenter](https://img.shields.io/badge/_Tests_-_13/13-green.png?style=flat)

# OkConfig #

A properties management library, built on top of okio and okjson.

It represent safe method to read or write value to file without write always same code to check if not null, cast to int, ... (unlike java.util.Properties which always return String)

Data are stored under JSON representation. (Easy to use with other program)

## Download ##

JCenter :
```
compile 'fr.cyberdean:okconfig:1.0'
```

## Usage ##

```java
final Properties conf = new Properties(new File("/path/to/my-config-file"));
conf.load(); //not necessary here, but necessary if you have existing file
conf.setValue("enableUI", true); //Set boolean value
conf.setValue("users", Arrays.asList("Bob", "Alice")); //Set List value
conf.save(); //Save to file

final boolean enableUI = conf.optBoolean("enableUI", false); //get value or false if not exist
```

/!\ `setValue` method support only (but sufficient) types : String, Number (int, float, ...), boolean, CharSequence, Map, List (All types supported by okjson)

## Licence ##

Apache 2.0, see LICENCE file.

Simple explanation : https://tldrlegal.com/license/apache-license-2.0-(apache-2.0)