# README #

Aim of this project is to give a simple way for dealing with dynamic JSON objects in java.

Fluent interface makes easy to create compound objects:
```
JSON json = new JSON()
   .put("key1", "simpleValue")
   .put("key2", 2)
   .put("key3", new JSON().add("this", "is", "an", "array")
   .put("key4", new JSON()
     .put("key41", "value")
     .put("key42", "value")
   );
```
Monadic style makes easy to access sub elements and helps avoiding nullchecks:
```
JSON sub = json.get("key4").get("key41");

JSON noSuchNode = json.get("noSuchKey");
noSuchNode.get("whatever"); //won't throw NullPointerException
System.out.println(noSuchNode==null); //prints false
System.out.println(noSuchNode.isNull()); //prints true

System.out.println(json.get("key3").get(0)); //prints "this"
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
* This project is used in some of my hobby projects at this moment, and it works well.
* Ideas and contributions are welcome.
* Based on Java 7.