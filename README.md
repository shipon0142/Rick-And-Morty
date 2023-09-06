# Rick-And-Morty Android App

## Installation
#### Clone the repository
git clone https://github.com/shipon0142/Rick-And-Morty.git


#### Open the project in Android Studio
File -> Open -> Select the project folder

#### Build and run the project on an emulator or device

## Overview

This Android project is built using the MVVM (Model-View-ViewModel) architectural pattern, which provides a clean and organized way to structure your Android application.

## Code Structure

The project follows a structured package hierarchy to organize its components:

- **app**: Contains the main application code.
  - **data**: Contains data-related classes.
    - **roomdatabse**: Contains Room database-related components.
  - **network**: Network communication and API calls.
  - **repository**: Contains api list and models.
     - **api**: API-related code.
     - **model**: All model class.
  - **utils**: Contains utility classes.
  - **view**: Contains user interface-related classes.
    - **activity**: Defines the activities.
    - **adapter**: Contains GridView adapters.
  - **viewmodel**: Managing and providing data to the UI components.
    
- **res**: Contains resources such as layouts, strings, and drawables.


## Features
 * Splash screen
 * Character List page
 * List pagination
 * Character Details page
 * Run offline
 * Update data when online
 * Pull down refresh
 * Shimmer Loading Effect
 * Unit Test

## Technology used

* MVVM architecture
* Android Databinding
* LiveData
* Retrofit2
* Hilt
* Picasso
* Room Database
* Shimmer Effect

## Permission
* INTERNET
* ACCESS_NETWORK_STATE


## Screenshot
<div style="display: flex; justify-content: space-between;">
<img src="https://raw.githubusercontent.com/shipon0142/Rick-And-Morty/master/app/src/main/assets/screenshot/character_list.jpeg" width="300">
<img src="https://raw.githubusercontent.com/shipon0142/Rick-And-Morty/master/app/src/main/assets/screenshot/character_details.jpeg" width="300">
</div>


