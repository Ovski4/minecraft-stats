Minecraft Stats
===============

A plugin for minecraft bukkit servers.  
The purpose of this plugin is to save statistics on players, so it can be used by a website.

## Requirements ##

This plugin was tested on a bukkit 1.5.2. server.  
You will need a mysql database to store the players statistics.

## Installation ##

  * Create a **Yaml** directory in your **plugins** directory
  * Add in this directory a file named **database.yml** with the following text: 
    NOTE SURE CHECK

```yml
MySQL:
  Host: localhost
  Login: database_user_login
  Password: database_user_password
  Database:  database_name
  Port: 3306
```

## Features ##

This plugin save infomation about players such as:
  * the number of placed blocks
  * the number of broken blocks
  * the number of deaths (with a distinction of normal deaths when killed by a npc or a player, stupid deaths in case of accidents)
  * the time played
  * the verbosity : the number of messages wrote in chat
  * the prestige

Everyone on the server can note others players. That's what the prestig is about.

You can enable or disable some stats in the config file (where??)

```yml
TimebetweenSaves: 30
StatsToBeRegistered:
  blockPlace: true
  blockBreak: true
  deaths: true
  timeplayed: true
  verbosity: true
  prestige: true
```

## Commands ##

Displays the current player statistics
  * /stats
  
Displays a player statistics
  * /stats playerName

Give a mark to a player (from -5 to +5)
  * /noter
