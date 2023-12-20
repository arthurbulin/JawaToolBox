# JawaToolBox

JawaToolBox is a simple Paper/Spigot plugin for Minecraft that isn't dependent on any other plugins. This contains a maintenance mode (a selective allowlist) and a sleep voting system.

## Getting Started

1. Clone the repo:
```
git clone https://github.com/arthurbulin/JawaToolBox
```
2. Either open the project in Netbeans and build with the Maven plugin or build from the CLI. NOTE: I never build from the CLI because I'm lazy.

### Prerequisites

You will need 
    - Maven to build the plugin
    - Java 17
    - Minecraft server running on Paper/Spigot (Paper is prefered and I will probably not support Spigot by the end of 1.20 support)

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

