# Project 1 - *CodePath-InstagramPhotoViewer*

**InstagramPhotoViewer** is an android app that allows a user to check out popular photos from Instagram. The app utilizes Instagram API to display images and basic image information to the user.

Time spent: **4** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current popular photos** from Instagram
* [x] For each photo displayed, user can see the following details:
  * [x] Graphic, Caption, Username
  * [x] Relative timestamp, like count, user profile image

The following **optional** features are implemented:

* [x] User can **pull-to-refresh** popular stream to get the latest popular photos
* [x] Show latest 2 comments for each photo
* [x] Display each user profile image using a RoundedImageViewDisplay each user profile image using a [RoundedImageView](https://github.com/vinc3m1/RoundedImageView)
* [x] Display a nice default placeholder graphic for each image during loading
* [ ] Improved the user interface through styling and coloring

The following **bonus** features are implemented:

* [x] Allow user to view all comments for an image within a separate activity or dialog fragment
* [x] Allow video posts to be played in full-screen using the VideoView
* [x] Apply the popular Butterknife annotation library to reduce view boilerplate

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://s3-eu-west-1.amazonaws.com/chezlui.freelancer/codepath/instagram_showcase.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

It was handy finding a class, DateUtils, that give you the relative time that's been passed since a creation date.
Firstly I implemented RoundedImageView leveraging on the RoundedTransformationBuilder that is packed with the library and can be used in conjunction with Picasso. The problem was that it didn't look quite well since while the avatar was loading the Placeholder appeared like an square image.
I had problems adding views programatically to a LinearLayout because during inflation I was using the rootView as an argument for the next sentence: commentRowView = getLayoutInflater(savedInstanceState).inflate(R.layout.row_comment, null);
Implementing VideoView was something tricky. I tried first to use the same layout and to make GONE or VISIBLE the imageView in the case of a photo, and the videoVie in the case of a video. But the elements that were positioned relative to the imageView got messed up. So I ended using two different layouts, but the problem then was that I couldn't reutilize them in the listView, i.e. each time getView was called I had to inflate a new layout.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [2016] [Jose Luis Martin Romera]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



