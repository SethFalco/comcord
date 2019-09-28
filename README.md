# Comcord [![Discord][discord-members]][discord] [![Download][bintray-download]][bintray] [![Documentation][docs-shield]][docs] [![GitLab Pipeline Status][gitlab-build]][gitlab] [![Coverage][gitlab-coverage]][gitlab] 
The [Gradle][gradle]/[Maven][maven] import string can be found at the Download badge above!

## About
Comcord (**Com**mandler for Dis**cord**) is an extension to Commandler for integration with Discord.  

## Quick-Start
**Main.java**
```java
public class Main {
    
    public static void main(String[] args) throws LoginException {
        AnnotationLoader loader = new AnnotationLoader(ExampleModule.class);
        Context context = new ContextLoader(loader).load().build();
        Commandler commandler = new Commandler.Builder(context)
            .build();
        
        DispatcherManager dispatcherManager = commandler.getDispatcherManager();
        dispatcherManager.addDispatcher(new CommandDispatcher(commandler, ">"));
    		
        JDA jda = new JDABuilder("${TOKEN}")
            .addEventListener(controller.getDispatcher())
            .build();

        new DiscordController(dispatcherManager, jda);
    }
}
```

**ExampleModule.java**
```java
@Controller @Aliases({"example", "ex"})
@Help(name = "Example", help = "Trying to show off Commandler!")
public class ExampleModule extends CommandController {

    @Static @Control @Aliases("ping")
    @Help(name = "ping!", help = "Check if I'm alive.")
    public String ping() {
        return "pong!";
    }
    
    @Control @Aliases("say")
    @Help(name = "Say", help = "Repeat after you.")
    public String say(@Param @Help(name = "text", help = "The text to repeat.") String text) {
        return text;
    }
}
```
> This creates the `ExampleModule`, and then adds it to our `Context` which is used
> by all Commandler libraries to manage modules. This alone allows commands to be 
> performed, as well as documentation and website generation for your module.  
>
> The commands `ping!` and `Say` are accessible via `>ex ping` and `>ex say {text}`
> respectively. As `ping!` is a static command, it can also be accessed without specifying
> the module name, like `>ping`.  
>
> Parameters are adapted by Commandler; they just have to be specified in method
> signatures and Commandler can do the rest.

## Support
Should any problems occur, come visit us over on [Discord][discord]! We're always around and there are
ample developers that would be willing to help; if it's a problem with the library itself then we'll
make sure to get it sorted.

[discord]: https://discord.gg/hprGMaM "Discord Invite"
[discord-members]: https://discordapp.com/api/guilds/184657525990359041/widget.png "Discord Shield"
[bintray]: https://bintray.com/elypia/comcord/core/_latestVersion "Bintray Latest Version"
[bintray-download]: https://api.bintray.com/packages/elypia/comcord/core/images/download.svg "Bintray Download Shield"
[docs]: https://commandler.elypia.org/ "Commandler Documentation"
[docs-shield]: https://img.shields.io/badge/Docs-Commandler-blue.svg "Commandler Documentation Shield"
[gitlab]: https://gitlab.com/Elypia/comcord/commits/master "Repository on GitLab"
[gitlab-build]: https://gitlab.com/Elypia/comcord/badges/master/pipeline.svg "GitLab Build Shield"
[gitlab-coverage]: https://gitlab.com/Elypia/comcord/badges/master/coverage.svg "GitLab Coverage Shield"

[gradle]: https://gradle.org/ "Depend via Gradle"
[maven]: https://maven.apache.org/ "Depend via Maven"

[elypia]: https://elypia.org/ "Elypia Homepage"
