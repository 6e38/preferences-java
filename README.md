# Preferences
*lite library for java*

Java has a built-in preferences class and I really wanted to use it, but for some reason they don't create the appropriate registry key for Windows thus making it useless. Much sadness.

This implementation creates a folder named `.java-user-prefs` in the user's home folder. For every class it creates a new file in that folder with the name `<class>.json`.

Since this is a lite implementation there is no support of preference groups. The user is left to decide how to organize the preferences.

This library comes with no warranty. Use at your own risk.

You may use and/or modify this code.

## Known Issues
* Depends on a super lite json library

## Usage

`ant compile` builds the project

`ant test` runs some tests on the code

`ant jar` packages the project

## Todo
* Javadoc

