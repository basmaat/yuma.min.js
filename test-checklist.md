# GUI-Test Checklist

This is the checklist for performing GUI integration tests. We REALLY need to automate these tests!
The [doh robot] (http://dojotoolkit.org/reference-guide/util/dohrobot.html) seems to be a suitable
candidate, as it captures mouse movement as well. (Critical - but not supported by e.g. Selenium).

# Image Annotation

Repeat tests 4 times: (i) with replies disabled + serverless mode, (ii) with replies disabled + annotation server,
(iii) with replies enabled + serverless mode, (iv) with replies enabled + annotation server.

1. Create an annotation and click CANCEL. Confirm the annotation is removed from the GUI.
2. Create an annotation and click OK. Delete the annotation again, and confirm it works as intended.
3. Create an annotation (and click OK to save it). DO NOT use default bounding box size and location!
   * Edit the annotation: change text AND bounding box size/location -> Click CANCEL and confirm it works as intended
   * Edit the annotation: change text AND bounding box size/location -> Click OK and confirm it works as intended
4. Create a second annotation that covers the first. Make sure the first remains clickable. 
   
# OpenLayers Annotation

Repeat all the steps for image annotation (4 times for different replies/server-mode configurations)