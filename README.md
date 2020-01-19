# jQAssistant JavaScript Plugin

[![GitHub license](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://github.com/softvis-research/jqa-githubissues-plugin/blob/master/LICENSE)
[![Build Status](https://api.travis-ci.com/softvis-research/jqa-javascript-plugin.svg?branch=development)](https://travis-ci.com/softvis-research/jqa-javascript-plugin)
[![codecov](https://codecov.io/gh/softvis-research/jqa-javascript-plugin/branch/development/graph/badge.svg)](https://codecov.io/gh/softvis-research/jqa-javascript-plugin)

This is a JavaScript parser for [jQAssistant](https://jqassistant.org/). 
It enables jQAssistant to scan and to analyze JavaScript file.

## Getting Started

Download the jQAssistant command line tool for your system: [jQAssistant - Get Started](https://jqassistant.org/get-started/).

Next download the latest version from the release tab. Put the `jqassistant-javascript-plugin-*.jar` into the plugins folder of the jQAssistant command
 line tool.
 
Now scan your code and wait for the plugin to finish:

```bash
jqassistant.sh scan -f
```

You can then start a local Neo4j server to start querying the database at [http://localhost:7474](http://localhost:7474):

```bash
jqassistant.sh server
```


## Labels and relations

### Labels

The JavaScript plugin uses the following labels in the resulting graph:

| Label | Description                                                  |
| ----- | ------------------------------------------------------------ |
|```:JavaScript```|Parent label for all nodes related to the JavaScript plugin.|
|```:JavaScriptFile```|A file that contains JavaScript code artifacts, such as classes, functions etc.
|```:Class```|Represents a JavaScript class.| "repo-user/repo-name"|
|```:Function```|Represents a JavaScript function.| "repo-user/repo-name#issue-number" |
|```:Variable```|Represents a JavaScript variable.| "repo-user/repo-name#milestone-id" |
|```:Value```|Represents nodes that can contain values for code artifacts (files, classes, functions, and variables).|
|```:Array```|Nodes that represent JavaScript [arrays](https://developer.mozilla.org/de/docs/Web/JavaScript/Reference/Global_Objects/Array).|
|```:Object```|Nodes that represent JavaScript [objects](https://developer.mozilla.org/de/docs/Web/JavaScript/Reference/Global_Objects/Object).|
|```:Number```|Nodes that represent JavaScript [numbers](https://www.w3schools.com/js/js_numbers.asp).|
|```:Boolean```|Nodes that represent JavaScript [booleans](https://www.w3schools.com/js/js_booleans.asp).|
|```:Undefined```|Nodes that represent JavaScript [undefined](https://developer.mozilla.org/de/docs/Web/JavaScript/Reference/Global_Objects/undefined).|
|```:Null```|Nodes that represent JavaScript [null](https://developer.mozilla.org/de/docs/Web/JavaScript/Reference/Global_Objects/null).|
|```:String```|Nodes that represent JavaScript [null](https://developer.mozilla.org/de/docs/Web/JavaScript/Reference/Global_Objects/String).|

### Relations

#### JavaScriptFile

```java
(:JavaScriptFile)  -[DECLARES]    ->  (:Variable)
(:JavaScriptFile)  -[DECLARES]    ->  (:Class)
(:JavaScriptFile)  -[DECLARES]    ->  (:Function)
```
#### Class
```java
(:Class)  -[DECLARES]    ->  (:Variable)
(:Class)  -[DECLARES]    ->  (:Function)
```
#### Function
```java
(:Function)  -[DECLARES]    ->  (:Variable)
(:Function)  -[INVOKES]    ->  (:Function)
```
#### Variable
```java
(:Variable)  -[HAS]    ->  (:Value)
```
#### Value
A value node can be either an ```array```, ```object```, ```number```, ```boolean```, ```string```, ```undefined``` or ```zero```.

## Known problems

### Weird nodes
The framework automatically creates file nodes, which the plugin does not continue to use, since the listeners create their own file nodes. For directories it does not behave like this. It is a good idea to delete these empty nodes.
### Test coverage
The test coverage is limited to the use of an integration test which exemplarily covers all components (nodes) and edges. Unit tests are missing.
### Static source code analysis is limited
The mapping of JavaScript is limited to the static analysis of source code artifacts. To extract further content, you would have to execute the source code and read the runtime information, e.g. using the runtime engine [Rhino](https://github.com/mozilla/rhino). This was not part of the work. 

### Did you find a bug?
Please have a look at the issue section in GitHub. If you can't find your bug open a ticket with an reproducible example and your error logs.
