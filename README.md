# JDAC [![Discord](https://discordapp.com/api/guilds/184657525990359041/widget.png)](https://discord.gg/hprGMaM) [![Documentation](https://img.shields.io/badge/Docs-Commandler-blue.svg)](https://jdac.elypia.com/) [![GitLab Pipeline Status](https://gitlab.com/Elypia/JDAC/badges/master/pipeline.svg)](https://gitlab.com/Elypia/JDAC/commits/master) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/4756f0a78c104040b93c8df85cd9f9ff)](https://www.codacy.com/app/Elypia/Commandler?utm_source=gitlab.com&amp;utm_medium=referral&amp;utm_content=Elypia/Commandler&amp;utm_campaign=Badge_Grade) [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/4756f0a78c104040b93c8df85cd9f9ff)](https://www.codacy.com/app/Elypia/Commandler?utm_source=gitlab.com&utm_medium=referral&utm_content=Elypia/Commandler&utm_campaign=Badge_Coverage)

## Importing
### [Gradle](https://gradle.org/)
```gradle
implementation 'com.elypia.jdac:{ARTIFACT}:{VERSION}'
```

### [Maven](https://maven.apache.org/)
```xml
<dependency>
  <groupId>com.elypia.jdac</groupId>
  <artifactId>{ARTIFACT}</artifactId>
  <version>{VERSION}</version>
</dependency>
```

## **Artifacts**
| Artifact         | Description                                                      |
|------------------|------------------------------------------------------------------|
| `core`           | Integration of Commandler with JDA for Discord command handling. |
| `logback-logger` | A `logback` appender for logging to a Discord message channel.   |

## About
JDAC is a set of utilities to aide in Discord bot development by providing tools, primiarly command handling through Commandler.  

Commandler is a command handling framework which uses annotations to execute commands as well as parsers and builders
for parameters and message types, an example could be as follows:

## Quick-Start
**Main.java.**
```java
public class Main {
    
    public static void main(String[] args) throws LoginException {
        ModulesContext context = new ModulesContext();
        context.addModules(ExampleModule.class);
    
        JDAC jdac = new JDAC.Builder()
            .setContext(context)
            .setPrefix(">")
            .build();
    
        new JDABuilder("${TOKEN}")
            .addEventListener(new JDACDispatcher(jdac))
            .build();
    }
    
    @Module(id = "Example", aliases = {"example", "ex"}, help = "Trying to show off Commandler!")
    public static class ExampleModule extends JDACHandler {
    
        @Static
        @Command(id = "ping!", aliases = "ping", help = "Check if I'm alive.")
        public String ping() {
            return "pong!";
        }
    
        @Command(id = "Say", aliases = "say", help = "Repeat after you.")
        @Param(id = "text", help = "The text to repeat.")
        public String say(String text) {
            return text;
        }
    }
}
```
> This created our `ExampleModule` class, and then adds it to our `ModulesContext` which is used by all Commandler libraries to manage modules. This alone allows commands to be performed, as well as documentation and website generation for your module.  

> The commands `ping!` and `Say` are accessible via `>ex ping` and `>ex say {text}` respectively. As `ping!` is a static command, it can also be accessed without specifying the module name, like `>ping`.  

> Parameters are parsed by Commandler, so they just have to be specified in method heads, not parsed. Parsing it done through parser objects which are defined for each type, JDAC provided a parser for all Discord entities by default but these can be overridden.

## Support
Should any problems occur, come visit us over on [Discord](https://discord.gg/hprGMaM)! We're always around and there are ample developers that would be willing to help; if it's a problem with the library itself then we'll make sure to get it sorted.

This project is _heavily_ relied on by [Alexis, the Discord bot](https://discordapp.com/oauth2/authorize?client_id=230716794212581376&scope=bot), our Discord bot.
