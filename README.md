# BetterWebreg
### __Note this is not the full code because I had to delete the private keys since this page is public, please contact me directly for the full code or try out the compiled apks__
This is an android app that uses javascript injection in a webview to get a Rutger's student's schedule from webreg by injecting their login to the page and navigating to their schedule. After it retreives the schedule it then parses it into notifications and reminds the user of every class an hour before the class starts with a notification. The notification includes all sorts of useful information it retrieved from webreg about the class including what bus to take and when that bus will arrive. The app also connects with the nextbus XML API and parses that information to predict when the bus will arrive at each stop as well as asynchronously list all busses and their times for each stop.
