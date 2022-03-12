![](./img/logo.png "CalPose Logo")
[![](https://jitpack.io/v/sigmadeltasoftware/CalPose.svg)](https://jitpack.io/#sigmadeltasoftware/CalPose)

Calpose is a lightweight, easy-to-use Calendar renderer/widget for Android/Jetpack Compose.

```diff
! NOTE: Given the Alpha-state of compose, it's important that the Compose-version of Calpose matches yours. 
! Preferably, the latest version would always be used in both cases. 
! Please consult the Version changelog below for more info.
```
## Renderer? 
The goal of Calpose is to avoid imposing rules or limitations in regards to the styling of the calendar. We want you to have full control
over the UI aspects using Composable widgets while Calpose deals with the Calendar intrinsics. In other words, Calpose will act as a 
renderer for your widgets based on the Calendar.

## Examples
### Default Widgets
![](./img/calpose.gif "Default Widgets")
[DefaultPreview.kt](https://github.com/sigmadeltasoftware/CalPose/blob/master/app/src/main/java/be/sigmadelta/calpose/DefaultPreview.kt "Default widget example")

### Material
![](./img/calpose_material.gif "Material Widgets with selection")
[MaterialPreview.kt](https://github.com/sigmadeltasoftware/CalPose/blob/master/app/src/main/java/be/sigmadelta/calpose/MaterialPreview.kt "Material widget example")

### GeoCities
![](./img/geocities.gif "Surfing the interw3bz")
[GeoCitiesPreview.kt](https://github.com/sigmadeltasoftware/CalPose/blob/master/app/src/main/java/be/sigmadelta/calpose/GeoCitiesPreview.kt "GeoCities widget example")

## Installation
Add Jitpack to your project build.gradle
```groovy
allprojects {
    repositories {
        ...
        
        // When using build.gradle
        maven { url 'https://jitpack.io' } 
        
        // When using build.gradle.kts
        maven {
            setUrl("https://jitpack.io")
        }
    }
}
```

Add the dependency to your module, see top for `${CURRENT_VERSION}`
```groovy
dependencies {
    // build.gradle
    implementation 'com.github.sigmadeltasoftware:CalPose:${CURRENT_VERSION}'

    // build.gradle.kts
    implementation("com.github.sigmadeltasoftware:CalPose:${CURRENT_VERSION}")
}
```
## Usage

Using the Calpose Calender is pretty much as straightforward as using any Composeable widget:
 
```kotlin
@Composable
fun Calpose(
    month: YearMonth,
    actions: CalposeActions,
    widgets: CalposeWidgets,
    properties: CalposeProperties = CalposeProperties(),
    firstDayOfWeek: DayOfWeek = DayOfWeek.MONDAY
)
```

There are 3 main components which have to be passed: 

#### 1. YearMonth
Using a `YearMonth`, Calpose is able to determine the calendar structure and render accordingly. By manipulating this value (passing a different `YearMonth`), you can choose which
month should currently be displayed.

#### 2. Actions
`CalposeActions` is a data class of actions which can be called upon by Calpose. Please consult the dokka documentation of `CalposeActions` (or our examples below) to get a better idea of which actions are 
beneficial to your application: [CalposeActions.kt](https://github.com/sigmadeltasoftware/CalPose/blob/master/calpose/src/main/java/be/sigmadelta/calpose/model/CalposeActions.kt "CalposeActions.kt")  

#### 3. Widgets
`CalposeWidgets` is the most important component of Calpose, which will contain the definition of the composable widgets that will be rendered by Calpose. This is where you'll mainly define what your calendar
should look like. Consult the dokka documentation of [CalposeWidgets](https://github.com/sigmadeltasoftware/CalPose/blob/master/calpose/src/main/java/be/sigmadelta/calpose/model/CalposeWidgets.kt "CalposeWidgets.kt") 
to get a better idea of which widgets you can pass, and feel free to take a look at the examples below to get a better idea of how these can be used for scenario's where you want to have `clickable` days, etc...

#### Properties
There is also an optional component of `properties` which contains some properties for really fine-tuning your experience: 
[CalposeProperties](https://github.com/sigmadeltasoftware/CalPose/blob/master/calpose/src/main/java/be/sigmadelta/calpose/model/CalposeProperties.kt "CalposeProperties.kt")

#### DayOfWeek
In addition, the day of the week on which a week starts can be selected, so that, for example, weeks that start on Sunday can also be displayed.

## Compose-version Changelog
* V1.1.0: Compose 1.0.0-beta09
* V1.0.5: Compose 1.0.0-beta09
* V1.0.4: Compose 1.0.0-beta01
* V1.0.3: Compose 1.0.0-alpha11
* V1.0.2: Compose 1.0.0-alpha10
* V1.0.1: Compose 1.0.0-alpha09

## License
MIT License

Copyright (c) 2020 Bojan Belic - Sigma Delta Software Solutions

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
