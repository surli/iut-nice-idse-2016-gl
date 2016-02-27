# UNO Interface Web [IHM]

### Table of contents

- [Contributors](#contributors)
- [Introduction](#introduction)
- [Install](#install)
- [Launch](#launch)
- [Running test](#running-test)
- [TODO](#todo)
- [Documentation](#Documentation)

### Contributors

* Jeremy Froment
* Teva Locandro
* Nicolas Claisse

### Introduction 

##### Arbo structure

```
/webuno
├── /app 
│   ├── /images          
│   ├── /scripts           
│   ├── /styles 
│   ├── /views 
├── /bower_components
├── /dist
├── /test 
├── /WEB-INF
├── bower.json
├── Gruntfile.js
├── package.json
```

### Install

Run this command for install npm dependencies :
```
npm install
```

Run commands to install bower and install bower dependencies :
```
npm install bower -g
bower install
```

Run this command for install Grunt :
```
npm install grunt-cli -g
```

Run this command for install Sass :
```
gem install sass
```

Run this command for install Compass :
```
gem install compass
```


#### If gem don't run (Windows)

Go to `http://rubyinstaller.org/downloads/` and download `Ruby.2.1.7 or 2.1.7(x64)`

If `2.1.7` or `2.1.7(x64)` download old version.

Double click `ruby installer` click `add PATH environnement` and finish install.

Source in french : http://www.codesscripts.fr/installer-ruby-sass-et-compass-avec-le-terminal-windows/


#### Build & Development

Run `grunt` or `grunt --force` for building.

To previews :
```
grunt serve
```

### Testing

Running this command will run the unit tests with karma :
```
grunt test
```

### TODO

* Complete this README
* Create a functional interface
* Users can play UNO

### Documentation

#### Yeoman

https://github.com/yeoman/generator-angular#readme

#### AngularJS

https://docs.angularjs.org/guide/

#### GruntJs 

http://gruntjs.com/getting-started