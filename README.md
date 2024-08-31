#### Package dev.h13j
## Class FixedSizeCollection<E>
```text
dev.h13j.FixedSizeCollection<E>
```


### All Implemented Interfaces:

```text
Iterable<E>, Collection<E>
```
---

```java
public class FixedSizeCollection<E> implements Collection<E> {}
```
### Constructor Summary
```java
public FixedSizeCollection(int size);
```

### Method Summary

| Returning Type | Method                                     | Short description                                                                                     |
|----------------|--------------------------------------------|-------------------------------------------------------------------------------------------------------|
| boolean        | add(E e)                                   | add element                                                                                           | 
| boolean        | addAll(@NotNull Collection<? extends E> c) | add collection of elements                                                                            |  
| void           | clear()                                    | clear collection                                                                                      |
| boolean        | contains(Object o)                         | return true, if contain object                                                                        | 
| boolean        | containsAll(@NotNull Collection<?> c)      | return true, if contain any elements from given collection                                            | 
| boolean        | hasNull()                                  | return true, if contain any null-value element                                                        |  
| boolean        | isEmpty()                                  | return true, if empty (size = 0)                                                                      | 
| boolean        | isFull()                                   | return true, if no more space to add elements                                                         |
| Iterator<E>    | iterator()                                 | return iterator for move by elements                                                                  | 
| boolean        | remove(Object o)                           | remove object(s) from collection and return true, if removed                                          |
| boolean        | removeAll(@NotNull Collection<?> c)        | remove element of given collection, if its present and return true, if removed                        |
| boolean        | removeIf(Predicate<? super E> filter)      | remove elements by filer predicate and return true, if removed                                        |
| boolean        | retainAll(@NotNull Collection<?> c)        | remove elements that are in given collection, but missing at original one and return true, if removed |
| int            | size()                                     | return current size of collection                                                                     | 
| Object[]       | toArray()                                  | convert collection to array of Objects                                                                | 
| <T> T[]        | toArray(T @NotNull [] a)                   | convert collection to array of given type T                                                           |  
| String         | toString()                                 | return string from elements with space delimiter                                                      | 

### Methods inherited from class java.lang.Object
```text 
clone, equals, finalize, getClass, hashCode, notify, notifyAll, wait, wait, wait
```

### Methods inherited from interface java.util.Collection
```text
equals, hashCode, parallelStream, spliterator, stream, toArray
```

### Methods inherited from interface java.lang.Iterable
```text
forEach
```

---
Copyright ©️ by Petr [hINT13] Naydin, Irkutsk `2024
