## Init Project

Run `npm install` to install npm dependencies.
Run `npm install bower -g` to install bower and `bower install` to install bower dependencies.
Run `npm install grunt-cli -g` to install Grunt.
Run `gem install sass` to install Sass.
Run `gem install compass` to install Compass.

## If gem don't run (Windows)

Go to `http://rubyinstaller.org/downloads/` and download `Ruby.2.1.7 or 2.1.7(x64)`
If `2.1.7` or `2.1.7(x64)` download old version.
Double click `ruby installer` click `add PATH environnement` and finish install.
Source in french : http://www.codesscripts.fr/installer-ruby-sass-et-compass-avec-le-terminal-windows/

## If you've got ERROR:  While executing gem ... (Encoding::UndefinedConversionError)

Run chcp 1252 and run gem commands

## Build & Development

Run `grunt` or `grunt --force` to buil and `grunt serve` to preview.

## Testing

Running `grunt test` will run the unit tests with karma.

## Documentation Yeoman

https://github.com/yeoman/generator-angular#readme