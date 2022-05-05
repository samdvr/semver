# Semver

This library provides parsing and comparison capability for [Semantic Versioning](https://semver.org) strings.

## Parsing

```scala
Semver.parse("1.2.3") // => Right(Semver(1, 2, 3))
```

## Comparison

```scala
Semver(2, 2, 3) > Semver(1, 2, 3) // => true
```