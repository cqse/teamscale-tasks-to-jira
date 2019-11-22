# teamscale-tasks-to-jira
Allows to fetch tasks from Teamscale and creates issues for the tasks in Jira

## Installation
Just download the `eu.cqse.qcs.jiratasks.zip` from [the releases](https://github.com/cqse/teamscale-tasks-to-jira/releases)

## Usage
The basic configuration must be placed in a properties file, see [settings.properties](https://github.com/cqse/teamscale-tasks-to-jira/blob/master/eu.cqse.qcs.jiratasks/src/main/resources/settings.properties) as example.

To run, execute the files in bin folder at command line, for example: 

`eu.cqse.qcs.jiratasks.bat settings.properties 4 5 6 17`

This will push Teamscale tasks 4, 5, 6, and 17 to Jira as specified in the settings file. 

## Custom fields
If you need to fill any other custom fields, you can figure out their id by:
1. Browse to some Jira ticket which is similar to the one you want to auto-create
2. Replace `/browse/` with `rest/api/latest/issue/` in the URL
3. Search for the field value in the json.
