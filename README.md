# Fit Journal
> A journal themed workout logging application.

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)
* [Status](#status)
* [Attribution](#attribution)
* [License](#license)

## General info
The goal of this project is to create an application that contains the sleek appearance and simplicity of a physical fitness journal, yet maintains the usability of a software application. Users can quickly log different types of exercises for the current day, view and modify any past or future logs, and track their body weight and fat. This project was motivated by the desire to dig deeper into Android development as well as to create a meaningful application to support my personal fitness goals.

## Technologies
* Java
* Android Room
* Android Navigation
* Android Lifecycle
* PhilJay MPAndroidChart
* Kizitonwose CalendarView

## Setup
Clone this repository into your local machine using HTTPS
```Shell
$ git clone https://github.com/Ying-Lai/FitJournal.git
```
Import the project to Android Studio. Sync gradle and then run the application.

## Features
#### Log your exercises for the current day:
* Choose from 3 different types of exercises (calisthenics, cardio, and strength).
* Create custom exercises or select from the existing exercise list to log.
* Favorite exercises for quick access.

<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/DiffTypesOfExercises.gif" height="435" width="260">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/CustomBrowse.gif" height="435" width="260">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Favorite.gif" height="435" width="260">

* Search through each list to find specific exercises.
* Add a note for each exercise log to include extra information.
* Edit and remove sessions, logged exercises, or custom exercises through a checked interface and a delete action mode.

<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Search.gif" height="435" width="260">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Note.gif" height="435" width="260">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/EditAndDelete.gif" height="435" width="260">

#### Track your body stats:
* Update body weight and body fat percentage on a day by day basis.
* Visually track changes in both stats over time. 

#### View your workout history through a calendar view:
* Previously logged workout days are marked on the calendar.
* View, edit, add, or remove exercises on any calendar day.

<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/UpdateStats.gif" height="435" width="260">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Calendar.gif" height="435" width="260">

## Status
Project is currently complete. Enhancements might be added in the future.

## Attribution
This application uses Open Source components. You can find the source code of their open source projects along with license information below.

[PhilJay - MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) <br />
Copyright 2019 Philipp Jahoda <br />
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

[kizitonwose - CalendarView](https://github.com/kizitonwose/CalendarView) <br />
Copyright (c) 2019 Kizito Nwose <br />
[MIT License](https://github.com/kizitonwose/CalendarView/blob/master/LICENSE.md)

#### Various Icons from [www.flaticon.com](www.flaticon.com)
<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Smashicons/creativity.png" height="32" width="32">&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Smashicons/garbage.png" height="32" width="32">&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Smashicons/journal.png" height="32" width="32">&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Smashicons/like.png" height="32" width="32">&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Smashicons/workout.png" height="32" width="32">

Icons made by [Smashicons](https://www.flaticon.com/authors/smashicons)

<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Freepik/calisthenics.png" height="32" width="32">&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Freepik/cardio.png" height="32" width="32">&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Freepik/dumbbell.png" height="32" width="32">&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Freepik/strength.png" height="32" width="32">

Icons made by [Freepik](https://www.flaticon.com/authors/freepik)

<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Icongeek26/notes.png" height="32" width="32">&nbsp;&nbsp;&nbsp;

Icon made by [Icongeek26](https://www.flaticon.com/authors/icongeek26)

<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/smalllikeart/hearts.png" height="32" width="32">&nbsp;&nbsp;&nbsp;

Icon made by [smalllikeart](https://www.flaticon.com/authors/smalllikeart)

<img src="https://raw.githubusercontent.com/Ying-Lai/FitJournal/master/images/Vectors%20Market/warning.png" height="32" width="32">&nbsp;&nbsp;&nbsp;

Icon made by [Vectors Market](https://www.flaticon.com/authors/vectors-market)

## License

Copyright 2019 Ying Lai

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

>http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
