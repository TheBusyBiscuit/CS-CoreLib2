# CS-CoreLib2
This is an updated (LITE) Version of CS-CoreLib. Instead of being a dependency, it should be shaded instead.

[Javadocs](https://javadoc.jitpack.io/com/github/TheBusyBiscuit/CS-CoreLib2/latest/javadoc/index.html)

## :warning: Deprecated - "end of life" :warning:
`CS-CoreLib2` is currently in its "end of life" stage.
This library has been re-furnished, improved and expanded and now lives on as ["dough"](https://github.com/baked-libs/dough), a new multi-module project which has all of `CS-CoreLib2`'s original features and more!

`CS-CoreLib2` will still be available until **September 2021** but this repository is going to be archived shortly after that and development will cease.

`CS-CoreLib2` has grown far past its original [CS-CoreLib](https://github.com/TheBusyBiscuit/CS-CoreLib) predecessor and beyond the current `0.x.x` versioning scheme.
It's time for our first initial `1.0.0` release and we think it is worthy of a re-branding at this point.
Why "dough" you ask? Well, the `C` in `CS-CoreLib` stands for cookies and you obviously need dough for baking cookies, right? Good, case closed.

## Integrating CS-CoreLib2 into your own Plugin
This part presupposes that you have [Maven](https://maven.apache.org/download.cgi) installed and know how to handle your pom.xml

### 1. Adding the repository
CS-CoreLib2 just sits here on GitHub, so you can use the jitpack.io repository for this.
Add this part into your ```<repositories>``` section.

```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>
```

### 2. Adding the dependency
Now you can add the dependency itself, for this you add the following into your ```<dependencies>``` section.
You can use "master-SNAPSHOT" to always use the latest Version, otherwise have a look on [GitHub Tags](https://github.com/TheBusyBiscuit/CS-CoreLib2/tags) to find all available Version numbers you could use.

```xml
<dependency>
  <groupId>com.github.thebusybiscuit</groupId>
  <artifactId>CS-CoreLib2</artifactId>
  <version>master-SNAPSHOT</version>
</dependency>
```

### 3. Shading
This Step is the most important and also the most easiest to mess up.
Add the following to your ```<builds>``` section:

```xml
<plugins>
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.2.1</version>

    <configuration>
      <relocations>
        <relocation>
          <pattern>io.github.thebusybiscuit.cscorelib2</pattern>
          <shadedPattern>YOUR.PACKAGE.NAME.HERE.cscorelib2</shadedPattern>
        </relocation>
      </relocations>
      <filters>
      <!-- This will become important in Step 4 -->  
      </filters>
    </configuration>

    <executions>
      <execution>
        <phase>package</phase>
        <goals>
          <goal>shade</goal>
        </goals>
      </execution>
    </executions>
  </plugin>
</plugins>
```

### 4. Configuration
It is very important that you changed your ```<shadedPattern>``` tag to a path WITHIN your project.
(e.g.   com.something.project.cscorelib2   )
Now you just need to select the packages you want to include.
You can find a list of all available packages in the wiki or on the bottom of this page.
Once you found the ones you need, add their package as an ```<include>``` tag like this:

```xml
<filters>
  <filter>
    <artifact>com.github.thebusybiscuit:CS-CoreLib2</artifact>
    <includes>
      <include>**/cscorelib2/config/**</include>
      <include>**/cscorelib2/updater/**</include>
    </includes>
  </filter>
</filters>
```

### 5. You are done
Your pom.xml should now look like this:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>me.somebody</groupId>
  <artifactId>pluginname</artifactId>
  <version>1.0</version>

  <repositories>
    <repository>
      <id>spigot-repo</id>
      <url>https://hub.spigotmc.org/nexus/content/groups/public/</url>
    </repository>
    <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </repository>
  </repositories>

  <dependencies>
    <!-- You are probably going to be using Bukkit, Spigot or something like that -->
    <dependency>
      <groupId>org.bukkit</groupId>
      <artifactId>bukkit</artifactId>
      <version>1.14.2-R0.1-SNAPSHOT</version>
      <scope>provided</scope>
    </dependency>
    
    <dependency>
      <groupId>com.github.thebusybiscuit</groupId>
      <artifactId>CS-CoreLib2</artifactId>
      <version>master-SNAPSHOT</version>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>3.2.1</version>

        <configuration>
          <relocations>
            <relocation>
              <pattern>io.github.thebusybiscuit.cscorelib2</pattern>
              <shadedPattern>me.somebody.pluginname.cscorelib2</shadedPattern>
            </relocation>
          </relocations>
          <filters>
            <filter>
              <artifact>com.github.thebusybiscuit:CS-CoreLib2</artifact>
              <includes>
                <include>**/thebusybiscuit/cscorelib2/config/**</include>
                <include>**/thebusybiscuit/cscorelib2/updater/**</include>
              </includes>
            </filter>
          </filters>
        </configuration>

        <executions>
          <execution>
            <phase>package</phase>
            <goals>
              <goal>shade</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>

</project>
```

## Packages
This Library contains the following Packages:
See our [Wiki](https://github.com/TheBusyBiscuit/CS-CoreLib2/wiki/) for more Info on what each Package does.

IMPORTANT: Some Packages also require other packages, check the Wiki for more Info.

```xml
<include>**/thebusybiscuit/cscorelib2/config/**</include>
<include>**/thebusybiscuit/cscorelib2/updater/**</include>
<include>**/thebusybiscuit/cscorelib2/data/**</include>
<include>**/thebusybiscuit/cscorelib2/database/**</include>
<include>**/thebusybiscuit/cscorelib2/reflection/**</include>
<include>**/thebusybiscuit/cscorelib2/protection/**</include>
<include>**/thebusybiscuit/cscorelib2/math/**</include>
<include>**/thebusybiscuit/cscorelib2/inventory/**</include>
<include>**/thebusybiscuit/cscorelib2/item/**</include>
<include>**/thebusybiscuit/cscorelib2/skull/**</include>
```
