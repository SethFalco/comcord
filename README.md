# Comcord [![Matrix]][matrix-community] [![Discord]][discord-guild] [![Bintray]][bintray-page] [![Docs]][documentation] [![Build]][gitlab] [![Coverage]][gitlab] [![Donate]][elypia-donate]
The [Gradle]/[Maven] import string can be found at the Download badge above!

## About
Comcord (**Com**mandler for Dis**cord**) is an extension to Commandler
for integration with Discord.  

This will provide various pre-made implementations to help you get
started as quickly as possible with making a Discord bot.

## Quick-Start

**application.yml**
```yml
commandler:
  dispatcher:
    - class: "org.elypia.commandler.dispatchers.StandardDispatcher"
      prefix:
        - ">"
comcord:
  bot-token: "I recommend you set this through environment variables instead."
  bot-owner-id: 127578559790186497
  support-guild-id: 184657525990359041
```

**Main.java**
```java
public class Main {
    
    public static void main(String[] args) throws LoginException {
        // Intitialize Commandler and get the injection context
        Commandler commandler = new Commandler();
        InjectorService injector = commandler.getAppContext().getInjector();

        // Get the Discord bot token
        String token = injector.getInstance(DiscordConfig.class).getBotToken();
        
        // Login to Discord
        JDA jda = new JDABuilder(token)
            .addEventListener(controller.getDispatcher())
            .build();

        // Add JDA to your Injection context
        injector.add(jda, JDA.class);
        
        // Run Commandler
        commandler.run();
    }
}
```

## Support
Should any problems occur, come visit us over on Discord!
We're always around and there are ample developers that would be 
willing to help; if it's a problem with the library itself 
then we'll make sure to get it sorted.

[matrix-community]: https://matrix.to/#/+elypia:matrix.org "Matrix Invite"
[discord-guild]: https://discord.com/invite/hprGMaM "Discord Invite"
[bintray-page]: https://bintray.com/elypia/comcord/core/_latestVersion "Bintray Latest Version"
[documentation]: https://elypia.gitlab.io/comcord "Commandler Documentation"
[gitlab]: https://gitlab.com/Elypia/comcord/commits/master "Repository on GitLab"
[elypia-donate]: https://elypia.org/donate "Donate to Elypia"
[Gradle]: https://gradle.org/ "Depend via Gradle"
[Maven]: https://maven.apache.org/ "Depend via Maven"

[Matrix]: https://img.shields.io/matrix/elypia-general:matrix.org?logo=matrix "Matrix Shield"
[Discord]: https://discord.com/api/guilds/184657525990359041/widget.png "Discord Shield"
[Bintray]: https://api.bintray.com/packages/elypia/comcord/core/images/download.svg "Bintray Download Shield"
[Docs]: https://img.shields.io/badge/Docs-Commandler-blue.svg "Commandler Documentation Shield"
[Build]: https://gitlab.com/Elypia/comcord/badges/master/pipeline.svg "GitLab Build Shield"
[Coverage]: https://gitlab.com/Elypia/comcord/badges/master/coverage.svg "GitLab Coverage Shield"
[Donate]: https://img.shields.io/badge/Elypia-Donate-blueviolet "Donate Shield"
