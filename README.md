# Comcord

[![](https://img.shields.io/maven-central/v/org.elypia.comcord/core)](https://search.maven.org/search?q=g:org.elypia.comcord) [![](https://gitlab.com/SethFalco/comcord/badges/main/pipeline.svg)](https://gitlab.com/SethFalco/comcord)

## Deprecation Notice

Comcord has not been worked on for years, and pre-dates Discord Slash Commands. As I'm no longer a Discord user, it's very unlikely I'll set time aside to maintain this project.

I would be happy to provide support and endorse a fork of this project, or a new library altogether that bridges Commandler to Discord if someone else was interested in maintaining it. Feel free to get in contact!

## About

Comcord (**Com**mandler for Dis**cord**) was an extension to Commandler for integration with Discord.

### Heads-up!

Comcord is **not** itself a wrapper around the Discord API. It's an integration between Commandler, the command handling framework, and JDA, an API client for Discord.

## Getting Started

### Import

Visit [Comcord on Maven Central](https://search.maven.org/search?q=g:org.elypia.comcord), and follow the instructions for your build system of choice to add Comcord to your project.

### Setup Commandler

It's recommended to start by following the ["Your First Command" section of Commandler](https://gitlab.com/SethFalco/commandler#your-first-command). Once you've done this and your command is working, you can replace the `console` module with Comcord.

### Scaffolding

Create an `application.yml` file in your classpath. Here you can configure your application, including Commandler.

**`main/resources/application.yml`**
```yml
commandler:
  standard-dispatcher:
    prefixes: 
      - "$"
discord:
  bot-token: "{BOT_TOKEN}"
comcord:
  listen-to-bots: false
  listen-to-edit-events: true
```

Next, create a class that produces the instance of the JDA client. This adds JDA to the CDI container, so it can be injected into any class that needs it, including your controllers.

**`main/java/org/example/bot/Main.java`**
```java
public class Main {

    /** Create the Commandler instance and runs all integrations. */
    public static void main(String[] args) {
        Commandler commandler = Commandler.create();
        commandler.run();
    }
    
    @ApplicationScoped
    public static class DiscordBot {
        
        private final JDA jda;
            
        /** Construct your JDA client. */
        @Inject
        public DiscordBot(DiscordConfig discordConfig) throws LoginException {
            String token = discordConfig.getBotToken();
            jda = JDABuilder.create(token, GatewayIntent.GUILD_MESSAGES).build();
        }
    
        /** Add the JDA instance as a singleton to the CDI container. */
        @ApplicationScoped
        @Produces
        public JDA getJda() {
            return jda;
        }
    }
}
```
