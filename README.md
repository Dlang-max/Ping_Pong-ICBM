# PING_PONG_ROBOT_ICBM (Intercontinental Ball Machine)

This is the repo for Daniel Lang and Zach Boeck's Senior Capstone. 
+ Link to Robot CAD:https://cad.onshape.com/documents/e40979f8a832da7594da0566/w/5f1ee792b9d612980563d301/e/48b0547c97357f7288388ecd




![image](https://user-images.githubusercontent.com/59770928/224519787-24003ce6-15b4-4854-8212-5fde6178273f.png)


### March 11th, 2023

At the time of writing this, we currently have a CAD model of our robot. 
Code has also been written to move the shoulder and elbow of our robot using human input from a controller.

### April 3rd, 2023

Worked on adding code to control the arm both manually and automatically. I did this by using a motion profile 
provide by WPI for controlling an arm using relative encoders built into the Neo 550s. These encoders will slip
over time so I think we are going to add two limit switches to reset the encoder values of the shoulder and elbow
so we can accurately go to a set position. Tomorrow I'm going to work on our prediction code and integrate it with
our object detection model. I want to get displacement to the ping pong ball in meters. This will allow us to move our 
robot to a set position using a simple conversion. 

### April 4th, 2023

Links in this update will no longer work. Refer to April 5th update

Created two new repositories. One contains code for calibrating a USB camera and the other contains code for pose
estimation of a ping pong ball. This pose estimation will give us an x, y, and z translation from the ping pong ball to
the camera. We will then predict where the ball will break the plane at the back of the ping pong table. Using 
inverse kinematics we will determine what angles we need the joints of our arm to be at. Link to Calibration Repository:
https://github.com/Dlang-max/Capstone_Camera_Calibration Link to first iteration of Distance Detection Repository: 
https://github.com/Dlang-max/Distance_Detection This repository is bound to change. I'm also going to train a new object
detection model for a ping pong ball because the one we are using right now isn't very accurate. 
 
### April 5th, 2023
 
I did a lot of work on vision processing today. I also ran into one major error. CAMERA BLUR. With the original shutter speed of 
the USB camera I'm using, ping pong balls would blur when they came into view of the camera because of how fast they move. To combat
this issue I raised the shutter speed of the camera and now I no longer get a blur when the ball crosses my screen. Then I moved to training
version 1 of the Tensorflow model I will use for our capstone. I used a script to take pictures every ten frames from a USBg camera. I then used 
LabelImg to annotate these images and I then trained a CNN using a colab notebook. When I tested this model it wasn't very good as expected as it's
only using 176 images. My hands are also in the images and because they are the same color as the ping pong ball I'm using my model kept thinking my
hands were ping pong balls. Tomorrow I'm going to train another model using around 750 images. Then I'm going to work on distance calculations and
predictions for the ball. 

+ Link to unblur and picture every ten frames code: https://github.com/Dlang-max/Capstone-Vision-Tests
+ Link to machine learning ping pong model and code: https://github.com/Dlang-max/Capstone-Machine-Learning
+ Link to the Google Colab notebook I use to train my models: https://colab.research.google.com/github/EdjeElectronics/TensorFlow-Lite-Object-Detection-on-Android-and-Raspberry-Pi/blob/master/Train_TFLite2_Object_Detction_Model.ipynb#scrollTo=TI9iCCxoNlAL
+ Link to Youtube video explaining this notebook: https://www.youtube.com/watch?v=XZ7FYAMCc4M&t=846s

### April 6th, 2023

Today I work on figuring out the equations I will use for the inverse kinematic control of the robot arm. It wasn't too bad. I watched a few Youtube videos on it. I think I'm also going to code a graphical demonstration of it using java just so I have a better grasp of it. This depends on time though. Tomorrow I'm going to work on calculating the distance of the ping pong ball from a USB camera.

+ Link to the Inverse Kinematics Youtube video: https://robotacademy.net.au/lesson/inverse-kinematics-for-a-2-joint-robot-arm-using-geometry/

### April 7th, 2023

Today I worked on actually calculating the distance to a ping pong ball using the Microsoft LifeCam 3000's focal length. The results weren't entirely great and tomorrow I'm going to shift gears and switch to using two USB cameras to do triangulation. This will give me the x, y, and z distance of the ping pong ball from both cameras. I also need to train a better model. The one I trained creates bounding boxes that are slightly too big. This discrepancy will screw up distance calculations as it relies on the width of the ping pong ball in pixels. I also wrote a script that calculates the focal length of the Microsoft LifeCam 3000. I also wrote a script that takes a video using OpenCV and creates a .avi file. I wanted to do this because I want to be able to test my code in an efficient way. 


+ #### Object Detection Working

![IMG_4202 (1) (3)](https://user-images.githubusercontent.com/59770928/230704232-0b4a5ee5-178c-4173-88bc-56eeaecfde88.gif)

### April 8th, 2023

I've decided to switch to a triangulation method for determining the x, y, and z distance of the ping pong ball in a coordinate system that we can use. That means we'll be using two Microsoft LifeCam 3000 USB cameras. Tomorrow I'll write the code to run my object detection model on two cameras and do triangulation. I spent the last two hours reviewing code from someone who has already solved the triangulation problem. I think it may also be wise to add a backdrop to the ping pong table so we get less interference with our model. I'm also not sure yet if we should mount the cameras to the ceiling or put them on the opposite side of the ping pong table from our robot. 

+ Link to Triangulation using OpenCv tutorial: https://www.youtube.com/watch?v=sW4CVI51jDY
+ Link to the Gitlab Repository: https://gitlab.com/-/ide/project/duder1966/youtube-projects/edit/master/-/OpenCV

### April 9th, 2023

Worked on triangulation code using example code from the above-linked Youtube video and Gitlab linked in its description. I need an additional Microsoft LifeCam 3000 to do triangulation and I'll work on this tomorrow. Once I get accurate distance calculations I'm going to write code to predict the flight path of the ping pong ball. I'm not sure if I'm going to need this yet but we'll see. I also wrote test code to display two concatenated webcam feeds. The display is of one USB camera and my laptop's integrated webcam 

+ #### Concatenated Webcam Feeds:

![IMG_4204](https://user-images.githubusercontent.com/59770928/230815967-da7e6c4e-5065-483a-a309-c3b9f61fd14c.jpg)

### April 10th, 2023

Ran into a major hardware issue (feature) today. All of the ports on my computer except an SD card reader are USBC ports. To use USB cameras I need to use a dongle. Triangulation requires the use of two cameras. So I thought I could just plug both cameras into one dongle. Turns out you can't. You need two separate dongles. This makes sense because I could use my laptop's integrated camera and a single USB camera without running into a bug, but once I switched to two USB cameras, Opencv couldn't grab frames. This issue was resolved by using two dongles instead of one. Triangulation code now works and it's actually pretty accurate. I think the next step is to actually start programming the robot arm. I want to see if I can just control it with a controller. Then I'll switch to controlling the arm with inverse kinematics. I also have to train new models for the cameras because we are now switching their orientation. I'm not sure yet if I have to train a separate model for both cameras. I'm going to see how just one works and then go from there. I also plan on cading a housing for the USB cameras so their orientation is fixed. 

+ #### Triangulation Code in Action (Ball was on 3in high duct tape and all distance measurements are in meters):

![IMG_4207](https://user-images.githubusercontent.com/59770928/231032449-62124c4f-5a28-4707-ab6e-2cbfe6c480f7.jpg)
![IMG_4210](https://user-images.githubusercontent.com/59770928/231032571-d2cbf4b8-509e-459a-baf3-73bb101e4c2d.jpg)

### April 17th, 2023

Today was the first day that I got to put code on the robot. First I had to update and assign CAN values to the Spark MAXs we are using to control the shoulder and elbow motors. We have basic control over the shoulder and elbow of the arm. We also added a curtain in the background so there would be less interference with our ping pong ball detection model. I also have the arm going to set angles which will be useful when I implement inverse kinematics. The PID control loop that I use for this still needs to be tuned better. I also used a script from my Capstone-Vistion-Test repository that takes images from a camera at a set resolution, shutter speed, brightness, color, and frame rate. I will annotate these images and train another object detection model with them for the stereo cameras. Today was mainly a plug-and-play kind of day. I wrote the PID control loops in the lab but other than that I just played around with the robot, tested code, and tuned PID loops. 

Updating and assigning CAN aalues using REV Hardware Client: https://docs.revrobotics.com/rev-hardware-client/spark-max/updating-spark-max

+ #### ICBM:

![Untitled video - Made with Clipchamp](https://user-images.githubusercontent.com/59770928/233121882-23f90270-079d-4c61-bea4-7933d4046433.gif)

### April 30th, 2023

So I guess I've been slacking a little bit with these updates. The good thing is, I've still been working on code during the past two weeks. I trained a third object detection model (That may or may not have gotten deleted when I was cleaning up my desktop), so I'm going to train another one tomorrow. We also got the slide working and also have the camera mounted on the robot and we are in the beginning stages of having the robot align to the ball based on input from the camera. I also worked on code that uses Google's mediapipe to track my hands so that I can control the robot by tracking the motion of my right and left hands. I'm going to test this code tomorrow.

+ #### Slide:
https://user-images.githubusercontent.com/59770928/235383968-80dac20f-f41d-49b6-a324-65a0608697f1.MOV


+ #### Ball Tracking: 
https://user-images.githubusercontent.com/59770928/235383979-ef55b7e1-602a-4df4-a7e3-08a4767855b7.MOV



+ #### Hand Tracking:
https://user-images.githubusercontent.com/59770928/235383732-7a68d0c4-e7cb-4ea0-ab1b-a50375703311.mp4


