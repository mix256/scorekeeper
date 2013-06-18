
------
About:
------
Score Keeper application for Android was created, primarily, for registering scores in the Stockholm Open 2013 Pinball Tournament. (http://stockholmopen.nu)
But it has been done in a way so that it should be easy to "brand" the application to be used in other tournaments (and I guess, other sports).

----------
Libs Used:
----------
ACRA - For catching the crashes (https://github.com/ACRA/acra)
ZBAR - Superb QR / EAN scanner library (http://sourceforge.net/projects/zbar/)

-------------
How To Setup:
-------------
The /res/raw folder contains a connection.properties file. This file holds all the information about the server API and the like.
To read more of how to use it, read the connection_properties.txt file.

If you want to upload the Play Store, make sure to change the package name or create another main activity to start the application with.
Also, create a proper generated keystore and put it in the keystore folder.

See default.properties for building properties.

------
Build:
------
Ant based build scripts are provided.
Run "ant run" to deploy to your Android.
Run "ant" to create release. Change/create the 

--------
Credits:
--------
Mikael Tillander - Codez
Patrik Bodin - Product Manager and runner of stockholmopen.nu (Stockholm Open Pinball Tournaments)
All score keepers at the Stockholm Open 2013 - Superb testers!

--------
Contact:
--------
Mikael Tillander
mix256@widepixelgames.com

