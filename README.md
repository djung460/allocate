# Allocate
A straightforward todo app that works by allocating time to each task.
## Features
1. Able to add tasks combined with a timer
2. The goal is then to productively use the allocated time for that task
3. Notification when the timer is done

## Mechanics
* Uses SQL database to store the tasks
* UI is updated via a Handler that runs with the UI thread, acts as a clock
* The TaskHandler class interfaces between the database and the UI
* The notification of completed tasks are done with an AlarmManager

## Background
This was made since this is the way I study and realized there wasn't an app that did this. Currently using it as a platform to learn Android development.

## Screenshots
![Alt text](app_screenshots/device-2016-05-13-160118.png?raw=true "The List of Tasks")
![Alt text](app_screenshots/device-2016-05-13-160022.png?raw=true "Entering a New Task")
![Alt text](app_screenshots/device-2016-05-13-161525.png?raw=true "Viewing Stats")
