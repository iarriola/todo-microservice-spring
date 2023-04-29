# todo-microservice-spring

A simple microservice with Java and Spring Boot

## Resources

* [Java 18 toolchain support](https://github.com/bazelbuild/bazel/issues/14532 "help on github")
* [How to specify Java version with Bazel](https://stackoverflow.com/questions/58567693/how-to-specify-java-version-with-bazel)
* [build - Is there a migration path from Maven to Bazel? - Stack Overflow](https://stackoverflow.com/questions/29342063/is-there-a-migration-path-from-maven-to-bazel)

### Bazel official reosurces

#### Installation

* [Installing / Updating Bazel using Bazelisk](https://bazel.build/install/bazelisk)
* [Installing Bazel](https://bazel.build/install)

#### Resources

* [Bazel Tutorial: Build a Java Project](https://bazel.build/start/java)
* [Java Rules  |  Bazel](https://bazel.build/reference/be/java#java_library)
* [Java and Bazel](https://bazel.build/docs/bazel-and-java)
* [bazeltools/bazel-deps: Generate bazel dependencies for maven artifacts (github.com)](https://github.com/bazeltools/bazel-deps)
* [bazelbuild/rules_jvm_external](https://github.com/bazelbuild/rules_jvm_external/tree/master/examples/spring_boot)
* [jin/awesome-bazel: A curated list of Bazel rules, tooling and resources. (github.com)](https://github.com/jin/awesome-bazel)
* [Maven plugin for migration from Apache Maven to Google Bazel (Deprecated): Used for getting the full dependency tree from pom.xml](https://github.com/OrhanKupusoglu/bazelize-maven-plugin)
  * You have to run `mvn install` to get access to the plugin.
* [Lessons Learned Moving From Maven to Bazel (Bazel Conf Mini-Talk) - Google Slides](https://docs.google.com/presentation/d/1URc6JzE71GWAek2ym9hiOoF1cAg-Jn7-GSD4jRpaY8w/htmlpresent)
* [Migrating from Maven to Bazel](https://bazel.build/migrate/maven)
* [External dependencies overview  |  Bazel](https://bazel.build/external/overview)

### Bazel and Srping

* [OpenAPI, Code Generation, Bazel, and Spring Boot | by Tom Liu | Medium](https://damuliu.medium.com/openapi-code-generation-bazel-and-spring-boot-e7a3603b3289)
* [Bazel rule for building Spring Boot apps as a deployable jar](https://github.com/salesforce/rules_spring)
* [Building a Spring Boot server with Bazel – ncona.com – Learning about computers](https://ncona.com/2021/11/building-a-spring-boot-server-with-bazel)

#### Bazel and Lombok

* [Bazel project with Lombok fails to build on Bazel 4.0.0 rc10 · Issue #12837 · bazelbuild/bazel (github.com)](https://github.com/bazelbuild/bazel/issues/12837)
* [bookingcom/rules_lombok_java_library: Bazel rules that apply delombok over your code so it can be properly passed to java_library (github.com)](https://github.com/bookingcom/rules_lombok_java_library)
* [Lombok Bazel and Java 17 (google.com)](https://groups.google.com/g/bazel-discuss/c/folhCqueNZA)
* [WORKING- Java 9 compiler breaks Project Lombok ](https://groups.google.com/g/bazel-discuss/c/ZRWdqJfYEPw/m/4pYq884kIQAJ)
