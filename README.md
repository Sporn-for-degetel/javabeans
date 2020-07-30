# Sporniket-Javabeans

> [WARNING] Please read carefully this note before using this project. It contains important facts.

Content

1. What is **Sporniket-Javabeans**, and when to use it ?
2. What should you know before using **Sporniket-Javabeans** ?
3. How to use **Sporniket-Javabeans** ?
4. Known issues
5. Miscellanous

## 1. What is **Sporniket-Javabeans**, and when to use it ?

**Sporniket-Javabeans** is a project to generate Javabeans from a tree of Java POJO structures. The generated tree of Javabeans replicates the POJO tree, supports generics. Additionnally, a fluent builder class is generated for each non abstract generated Javabeans.

**Sporniket-Javabeans** also provides a plugin to perform reverse engineering of an existing class hierarchy of Javabeans for the sake of helping as much as possible to migrate an existing project.

_As of v20.04.01_

**Sporniket-Javabeans** also provides _seeg_ (**S**porniket **E**ntities **E**xtractor and **G**enerator), an application and maven plugin to perform generate a set of entities classes from a database (for now, only PostgreSql is supported), including enums, id classes and a few selectors.

*Before Septembre 2017, it WAS
a project to generate Javabeans from a XML model. Interested people may checkout the v15.04.00 or v15.04.01 and fork from there.*

### TODO list, a.k.a. "The roadmap"

In no particular order :

* can generate boundable and constrainable properties (will use annotation)
* allows true encapsulation of collections and maps (idem)
* the fluent builder api 'done()' method name may be specified.

See also the known issues.

### What's new in v20.04.04

* #53 : [bug][seeg]Hibernate cannot find properties of query method if internal name does not match

### What's new in v20.04.03

* #48 : [seeg] Have a mapper from Entity to IdClass - David SPORN (HEAD -> master, origin/master, origin/HEAD)
* #49 : [bug][seeg] identity auto-incremented and sequence columns not recognized properly - David SPORN
* #50 : [bug][seeg] Generate only enums from the target schema - David SPORN
* #51 : [bug][seeg] Repository class with idclass is not generated - David SPORN
* #52 : [bug][seeg] the id class must implements serializable. - David SPORN

### What's new in v20.04.02

* #45 : [bug][seeg] 'bigserial' type not supported
* #46 : [bug][seeg] type 'serial' should be mapped to integer
* #47 : [seeg] Use primary key columns for entity hashcode and equality

### What's new in v20.04.01

* 42 : Plugin to generate JPA entities and repositories from database
* 44 : [seeg] implements compounded primary keys with @IdClass

### What's new in v20.04.00

* #26 : Generate a generic builder for a generated abstract bean
* #39 : [Bug] Escape string values for annotations parameters
* #41 : [bug] Some annotation need to be generated on the accessors too


### What's new in v19.09.00

* #22 : Copy javadoc comments - class and fields

### What's new in v19.04.00

* #34 : Support arrays fields

### What's new in v19.03.00

* #36 : Does not recognize field name prefix in pojo to expands
* Use junit5 tests, add better coverage.

### What's new in v19.02.00

* Fixes an obvious problem of classname translation when looking for any replacement possible, e.g. when there are the following names to replace : `PrefixClassOfThings`, `PrefixClassOfThingsSpecial`, it tries to replace the longest first.

Fixes to the demo project :

* #34 : Wrong format for additionnal parameters in demo pom
* address critical vulnerability warning in demo project about jackson version

### What's new in v18.10.01

* #33 : Generate a builder that can handle a subclass. In other words, the builder has a default constructor to act as usual, and a constructor accepting an Javabean instance that should have been created.

```java
//SampleGenerics<T,R extends Number> has been generated
static class Parametrized extends SampleGenerics<CharSequence, Double>
{
  public void doSomething()
  {
    System.out.println("hi !");
  }
}

//Sample use of the builder class
public static void main(String[] args)
{
  SampleGenerics<String, Long> sampleGenerics = new SampleGenerics_Builder<String, Long>()//
      // ...etc...
      .done();

  //Create a subclass instance
  Parametrized sampleGenericsSubclass = (Parametrized) new SampleGenerics_Builder<>(new Parametrized())
      // ...etc...
      .done();
  sampleGenericsSubclass.doSomething();
}
```


### What's new in v18.10.00

* #28, #30 : Support for class and field annotations, can handle typical use of Jackson annotations (`@JsonProperty`, `@JsonTypeInfo` and `@JsonSubTypes`)

### What's new in v17.12.00

Technical matters :

* #20 : Refactor code generation to separate data preparation from data injection into a template

Issues fixed :

* #21 : The generated javabean imports class required by inherited fields
* #23 : Generated classes import native types
* #24 : Boolean/boolean read accessor should be prefixed by 'is' instead of 'get'

### What's new in v17.09.01

Technical matters :

* extending the code coverage using Mockito

Issues fixed :

* #19 : Accessors are not following CamelCase

### What's in v17.09.00
* generate Javabeans with basic getter/setter (not bounded, not constrained).
* fluent builder api, e.g. ```bean = new MyBeautifulBean_Builder.withId(...).withDescription(...).done() ;```
* reverse engineering of existing simple Javabeans hierarchy, to convert existing projects.

### Licence
 **Sporniket-Javabeans** is free software: you can redistribute it and/or modify it under the terms of the
 GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 option) any later version.

 **Sporniket-Javabeans** is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 more details.

 You should have received a copy of the GNU Lesser General Public License along with **Sporniket-Javabeans**.
 If not, see http://www.gnu.org/licenses/ .


## 2. What should you know before using **Sporniket-Javabeans** ?
**Sporniket-Javabeans** relies only on standard jdk 8 and consists of :

* **Sporniket-Javabeans-doclet** : contains ```ExpanderDoclet``` to generate Javabeans, and ```DistillerDoclet``` to reverse-engineer POJOs.
* **seeg-maven-plugin** : 'Sporniket Entities Extractor and Generator' to generate your JPA entities and spring JpaRepositories base repositories

> Do not use **Sporniket-Javabeans** if this project is not suitable for your project

## 3. How to use **Sporniket-Javabeans** ?

### Using 'sporniket-javabeans-doclet'

> See the demo project ```sporniket-javabeans-doclet-sample```. It demonstrate :
>
> * Javabeans and POJOs generation, integrated in the build process of maven.
> * Typical supported java code support for conversion (e.g. generics, annotations).
> * Manual call in a sample program.

### Using 'seeg-maven-plugin'

> This tool require an accessible database with actual tables.
>
> PostgreSQL is the only dbms supported for now

In your maven project, prepare a properties file describing the connection to the reference database, by default it will be `${project.basedir}/seeg-connection.properties`, and containing the following keys : 

* _url_ : jdbc url, e.g. 'jdbc:postgresql://localhost:54320/postgres'.
* _driverClass_ : jdbc url, e.g. 'org.postgresql.Driver'.
* _username_ : name of the database user, should have access to the schema.
* _password_ : password for the database user.

In your pom build section, declare and configure seeg-maven-plugin with the target package to generate (in the source folder) and an optionnal schema ; use the `connectionConfig` parameter to set the path to the properties file if it is not `${project.basedir}/seeg-connection.properties`.

```xml
<plugin>
	<groupId>com.sporniket.javabeans</groupId>
	<artifactId>seeg-maven-plugin</artifactId>
	<version>${version.seeg-maven-plugin}</version>
	<configuration>
		<targetPackage>com.sporniket.lca.entities</targetPackage>
		<schemaName>lca</schemaName>
	</configuration>
</plugin>
```

Then, when ever you need to regenerate your entities, invoke seeg : 

```
mvn seeg:seeg
```


## 4. Known issues
See the [project issues](https://github.com/sporniket/javabeans/issues) page.

## 5. Miscellanous
### Report issues
Use the [project issues](https://github.com/sporniket/javabeans/issues) page.
