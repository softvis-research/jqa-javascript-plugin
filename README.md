# jQAssistant Java Source Parser Plugin #

[![GitHub license](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://github.com/softvis-research/jqa-javasrc-plugin/blob/master/LICENSE)
[![Build Status](https://travis-ci.com/softvis-research/jqa-javasrc-plugin.svg)](https://travis-ci.com/softvis-research/jqa-javasrc-plugin)

This is a java source parser for [jQAssistant](https://www.jqassistant.org).
It enables jQAssistant to scan and to analyze Java source code files.

## Configuration Parameters for Maven ##

```
<configuration>
	<scanIncludes>
		<scanInclude>
			<path>[PATH TO SOURCE ROOT FOLDER]</path>
			<scope>java:src</scope>
		</scanInclude>
	</scanIncludes>
	<scanProperties>
		<jqassistant.plugin.javasrc.jar.dirname>[PATH TO FOLDER WITH JAR DEPENDENCIES]</jqassistant.plugin.javasrc.jar.dirname>
	</scanProperties>
</configuration>
```
