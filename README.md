Animation-edit
==============

What is Animation-edit?
-----------------------
Animation-edit is a tool to create pixel-graphics animations, mainly for games and interactive applications.

Features:
* Create and edit animation sequences using built-in drawing tools or imported images.
* Preview animations.
* Drawing tools can be used to sketch up animations from scratch.
* Integrates well with any external pixel-graphics program (Photoshop, Gimp etc).
* Add meta-data to animations for use in games.
* Save file format that can be integrated directly in a game.
* "Lightboard" feature to draw several frames on top of each other.
* Runs on Windows, Linux, Mac etc.

To install and start:
---------------------
* Download a release from [the release page](https://github.com/jonath0000/animation-edit/releases)
* Unpack zip and start animation-edit.jar

To create a new animation file with some frames:
------------------------------------------------
* Select File->New and create a new file in an empty directory. This directory will be referred to as the "working directory".
* To create a blank new image: Choose Frames->"New frame, create new image". A new png file will be created in the working directory.
* To import an existing png file placed in the working directory: Choose Frames->"New frame, select image in working directory". Input the file name (".png" can be omitted.)

To edit the image of a frame:
-----------------------------
* Use the *Animation frame list* to the left of the window to select the current image.
* Use the drawing tools available to edit the image in the center of the window.
* Alternatively, edit the image in another drawing application, and it will be updated automatically in Animation-edit.

To show severeal frame images overlayed:
----------------------------------------
This feature is useful when sketching animations, it is a virtual "lightboard" or "onion skin view" that shows frames overlayed on top of each other.
* Use keys ALT + 0, 1, 2, 3... to select the number of layers that are shown.

To select the speed of the preview playback:
--------------------------------------------
* Use numeral keys 1, 2, 3, 4 to control the preview speed.
* Key 0 is stop.
* When stopped, use N and B keys to step in the animation.

To edit the duration of a frame:
--------------------------------
* Choose Image->"Edit frame properties". Input the number of tics to stay on the frame. For example, if 60 FPS playback is chosen, 60 tics will stay one second on the frame.

To create non-linear sequences:
-------------------------------
This is useful to create different animations for a game, eg. Walk, Jump, Shoot etc.
* Choose Image->"Edit frame properties". Give the frame a *tag*.
* Now select another frame and again edit the properties, input the previously created tag as *next*.
* Now the preview will use the *next* property to jump to the tag specified.

To copy existing frames:
------------------------
* If you want to copy a frame and share the image file with the old frame, select Frames->"New frame, use currently selected image". Any change to either of the frames image will affect both since they share the image file.
* If you want to copy a frame and also copy the image file, select Frames->"New frame, copy currently selected image".

To adjust the position of a frame:
----------------------------------
* Use the *frame offset* to adjust the position of one frame. 
* Using keys I, J, K, L together with CTRL are convenient for editing the offset. 

To add an event to a frame:
---------------------------
* In the properties for the frame, add a "event" name.
* Additionaly, an event can have an x,y coordinate.
* This data is not used by Animation-edit, but can be used from a game to trigger events, eg. "Shoot", "Attack" etc.
