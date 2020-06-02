# Comcord [![Matrix]][matrix-community] [![Discord]][discord-guild] [![Maven Central]][maven-page] [![Docs]][documentation] [![Build]][gitlab] [![Coverage]][gitlab] [![Donate]][elypia-donate]
The [Gradle]/[Maven] import string can be found at the maven-central badge above!

## About
Comcord (**Com**mandler for Dis**cord**) is an extension to Commandler
for integration with Discord.  

This will provide various pre-made implementations to help you get
started as quickly as possible with making a Discord bot.

### Heads Up
Comcord is **not** an implementation of the Discord API. It's an integration project
which just ties Commandler, the command handling framework together with Discord using
[JDA] which is an API client to interact with Discord and develop Discord bots.

## Quick-Start
**application.yml**
```yml
commandler:
  standard-dispatcher:
    prefixes: 
      - "$"
discord:
  bot-token: "It's strongly recommended to set this through environment variables."
comcord:
  listen-to-bots: false
  listen-to-edit-events: true
```

**Main.java**
```java
public class Main {
    
    /**
    * First call Commandler#create() to initialize the CDI
    * (Contexts and Dependency Injection) container. This will 
    * initialize most of the application and validate your classes
    * and configuration.
    * 
    * Then call Commandler#run(); on the instance to create the
    * enabled integrations, this will boot up your application
    * and allow it to start accepting commands.
    */
    public static void main(String[] args) {
        Commandler commandler = Commandler.create();
        commandler.run();
    }
    
    /**
    * Make a producer class which will just construct your JDA instance.
    * You can visit https://github.com/DV8FromTheWorld/JDA for more information
    * on how to get started with JDA.
    */
    @ApplicationScoped
    public static class DiscordBot {
        
        /** The Discord client, this lets us interact with Discords API. */
        private final JDA jda;
            
        /** Configure your JDA instance. */
        @Inject
        public DiscordBot(DiscordConfig discordConfig) throws LoginException {
            String token = discordConfig.getBotToken();
            jda = JDABuilder.create(token, GatewayIntent.GUILD_MESSAGES).build();
        }
    
        /** Add the JDA instance an injectable bean for the CDI container. */
        @ApplicationScoped // Ensures it's only ever constructed once.
        @Produces // Marks this method as producing an injectable bean.
        public JDA getJda() {
            return jda;
        }
    }
}
```

## Support
Should any problems occur, come visit us over on [Discord][discord-guild]!
We're always around and there are ample developers that would be 
willing to help; if it's a problem with the library itself 
then we'll make sure to get it sorted.

[matrix-community]: https://matrix.to/#/+elypia:matrix.org "Matrix Invite"
[discord-guild]: https://discord.com/invite/hprGMaM "Discord Invite"
[maven-page]: https://search.maven.org/search?q=g:org.elypia.comcord "Maven Central"
[documentation]: https://elypia.gitlab.io/comcord "Documentation"
[gitlab]: https://gitlab.com/Elypia/comcord/commits/master "Repository on GitLab"
[elypia-donate]: https://elypia.org/donate "Donate to Elypia"
[Gradle]: https://gradle.org/ "Depend via Gradle"
[Maven]: https://maven.apache.org/ "Depend via Maven"
[JDA]: https://github.com/DV8FromTheWorld/JDA "JDA on GitHub"

[Matrix]: https://img.shields.io/matrix/elypia-general:matrix.org?logo=matrix "Matrix Shield"
[Discord]: https://discord.com/api/guilds/184657525990359041/widget.png "Discord Shield"
[Maven Central]: https://img.shields.io/maven-central/v/org.elypia.comcord/core "Download Shield"
[Docs]: https://img.shields.io/badge/docs-comcord-blue.svg "Documentation Shield"
[Build]: https://gitlab.com/Elypia/comcord/badges/master/pipeline.svg "GitLab Build Shield"
[Coverage]: https://gitlab.com/Elypia/comcord/badges/master/coverage.svg "GitLab Coverage Shield"
[Donate]: https://img.shields.io/badge/donate-elypia-blueviolet "Donate Shield"
