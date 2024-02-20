# README #

## Introduction ##
There are multiple ways for dealing with JSON in Java.
`Jackson`, `Gson` and similar JSON-Object mappers are fine when there's some solid idea about the Domain objects which JSON should be mapped to.
But sometimes, especially when the code is in relation with some REST API in early development phase, domain model may not be really well-defined.
There might be other cases when the domain is explicitly dynamic, some parts of it doesn't even have a static structure.

In this case it's handy to use pure dynamic JSON processing.
`json-simple` is a nice tool for managing JSON in Java, however, sometimes it is cumbersome to use it because of the continuous urge to use casting and variable assignments.

The goal of this project is to give a even *simpler* way for dealing with dynamic JSON objects in java.

## Features ##

Fluent interface makes it easy to create compound objects:
```
JSON json = JSON.object()
   .put("key1", "simpleValue")
   .put("key2", 2)
   .put("key3", JSON.array("this", "is", "an", "array")
   .put("key4", JSON.object()
     .put("key41", "value")
     .put("key42", "value")
   );
```
Monadic style makes it easy to access sub elements and helps avoiding nullchecks:
```
JSON sub = json.get("key4").get("key41");

JSON noSuchNode = json.get("noSuchKey");
noSuchNode.get("whatever"); //won't throw NullPointerException
System.out.println(noSuchNode==null); //prints false
System.out.println(noSuchNode.isNull()); //prints true

System.out.println(json.get("key3").get(1)); //prints "an"
//Of course it's good to know at some point if something is an object or an array.
```

Built-in type checker and converter methods help avoid casts:
```
System.out.println(sub.get("key1").isString()); //prints true
//if we are certain that the attribute is string, this shortcut can be also used
String s = sub.getAsString("key1");

//which is the same as:
String s = sub.get("key1").asString();
```

Iterator support for arrays and for other types:
```
for(JSON arrayElement : json.get("key3")) {
  //you can work with arrayElement
}

//iterators are OK with primitive types:
for(JSON item : json.get("key1")) {
  //note, that json.get("key1") gives a simple string elements, so this will be a one-element iteration
}

//iterators are OK with empty elements:
for(JSON item : json.get("noSuchKey")) {
  //no execution will be here.
}
```

Some more remarks:
* Internal implementation is based on json-simple.
* No 'unchecked' operations.
* Based on Java 7, so it runs on Android.
* Instances of JSON are Serializable, so they can be persisted, cached, be sent into the wire, etc.
* This project is used in some of my hobby projects, for example Traxpace
* Ideas and contributions are welcome.
