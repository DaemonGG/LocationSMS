#
# Author: Xingchi Jin, Andrew ID: xingchij
#

Project Name: LocationMSG

Once the MainActivity starts, it will get th best provider by function getBestProvider(). Only if the GPS is turned on in your device, the selected service provider will be GPS. 

I updated the current location and show them in TextView by install LocationListener. Click the send msg button will also update and show current location.


class: SendMsg:
	Use smsManager to send message.


Usage: Since AVD is not able to use GPS sensor directly, you need to use console command "telnet localhost 5554" and then use "geo fix 37.4144226 -121.9628543 3"