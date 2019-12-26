# JawaToolBox

JawaToolBox is a simple Spigot plugin for Minecraft 1.15.1 that isn't dependent on any other plugins. Currently it only contains a maintenance mode feature that allows a server admins to keep other users from joining unless they are on the maintenance list. Automatic restart functionality, sleep voting, and other things are planned for this plugin. Building is done with Maven and development is done in Apache Netbeans.

## Getting Started

1. Clone the repo:
```
git clone https://github.com/arthurbulin/JawaToolBox
```
2. Either open the project in Netbeans and build with the Maven plugin or build from the CLI. NOTE: I never build from the CLI because I'm lazy.

### Prerequisites

You need Maven to build the plugin. Netbeans is optional but recommended. To use it you need Spigot 1.15.X. It should work under 1.14.X but I haven't tested it.

### Installing

Place in the server's plugins directory. The server will generate all needed config files.

### Usage

Toggle maintenance mode on and off for the current session:
```
/mmtoggle
```
Add an online user to the maintenancelist.txt file using their username. If they are offline you can specify a UUID
```
/mmadd <user name|UUID>
```
Remove an online user to the maintenancelist.txt file using their username. If they are offline you can specify a UUID
```
/mmremove <user name|UUID>
```
See maintenance mode status
```
/mmstatus
```

Maintenance mode can be made to be persistant between restarts by setting the value to true in the config.yml:
```
maintenance-mode: true
```

## Authors

* **Arthur Bulin aka Jawamaster** - (https://github.com/arthurbulin)

## License

This project is licensed under the MIT License. Just don't be a jerk about it.

