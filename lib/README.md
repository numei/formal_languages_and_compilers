# JAXB Dependencies

JDK 9 and newer do not include JAXB in the standard library. This project uses JAXB 2.x with `javax.xml.bind`.

The startup scripts first look for jars in this `lib/` directory, and also support the standard Maven cache under `~/.m2/repository`.

Required jars:

- `jaxb-api-2.3.1.jar`
- `jaxb-runtime-2.3.1.jar`
- `jaxb-core-2.3.0.jar`
- `txw2-2.3.0.jar`
- `istack-commons-runtime-3.0.7.jar`
- `stax-ex-1.8.jar`
- `FastInfoset-1.2.15.jar`
- `javax.activation-api-1.2.0.jar`

If these jars are already in `~/.m2/repository`, no extra copy is needed.
