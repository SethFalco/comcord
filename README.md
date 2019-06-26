# Discord Commandler [![Discord][discord-members]][discord] [![Download][bintray-download]][bintray] [![Documentation][docs-shield]][docs] [![GitLab Pipeline Status][gitlab-build]][gitlab] [![Coverage][gitlab-coverage]][gitlab] 
The [Gradle][gradle]/[Maven][maven] import string can be found at the Download badge above!

## About
Discord Commandler is an extension to Commandelr for integration with Discord.  

## Quick-Start
**Main.java.**
```java
public class Main {
    
    public static void main(String[] args) throws LoginException {
        ContextLoader loader = new ContextLoader(ExampleModule.class);
        DiscordController controller = new DiscordController();
        Commandler commandler = new Commandler(loader, controller);
    
        new JDABuilder("${TOKEN}")
            .addEventListener(controller.getDispatcher())
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
>
> The commands `ping!` and `Say` are accessible via `>ex ping` and `>ex say {text}` respectively. As `ping!` is a static command, it can also be accessed without specifying the module name, like `>ping`.  
>
> Parameters are parsed by Commandler, so they just have to be specified in method heads, not parsed. Parsing it done through parser objects which are defined for each type, JDAC provided a parser for all Discord entities by default but these can be overridden.

## Support
Should any problems occur, come visit us over on [Discord][discord]! We're always around and there are
ample developers that would be willing to help; if it's a problem with the library itself then we'll
make sure to get it sorted.

[discord]: https://discord.gg/hprGMaM "Discord Invite"
[discord-members]: https://discordapp.com/api/guilds/184657525990359041/widget.png "Discord Shield"
[bintray]: https://bintray.com/elypia/Commandler/core/_latestVersion "Bintray Latest Version"
[bintray-download]: https://api.bintray.com/packages/elypia/Commandler/core/images/download.svg "Bintray Download Shield"
[docs]: https://commandler.elypia.com/ "Commandler Documentation"
[docs-shield]: https://img.shields.io/badge/Docs-Commandler-blue.svg "Commandler Documentation Shield"
[gitlab]: https://gitlab.com/Elypia/commandler/commits/master "Repository on GitLab"
[gitlab-build]: https://gitlab.com/Elypia/commandler/badges/master/pipeline.svg "GitLab Build Shield"
[gitlab-coverage]: https://gitlab.com/Elypia/commandler/badges/master/coverage.svg "GitLab Coverage Shield"

[gradle]: https://gradle.org/ "Depend via Gradle"
[maven]: https://maven.apache.org/ "Depend via Maven"

[elypia]: https://elypia.com/ "Elypia Homepage"
[night-config]: https://github.com/TheElectronWill/Night-Config "GitHub Repo for Night-Config"
