# Project 01 Retrospective and Overview

[Video Walkthrough](https://drive.google.com/file/d/10YvfrJso0ogIVMJDDOhZfoXfpxPMn4S6/view?resourcekey)  
[Github Repo](https://github.com/EstrellaOrtiz-F/CST-438-Project1.git)

---

## Overview

This is an app that fetches [Yu-Gi-Oh](https://ygoprodeck.com/api-guide/) card data using a public Yu-Gi-Oh API.

We got styling help for this document from this [guide](https://ygoprodeck.com/api-guide/).


---

## Introduction

* How was communication managed
  * We primarily communicated through Slack and during class time.

* How many stories/issues were initially considered
  * Approximately 13 issues were initially planned, we later added a couple more issues and user stories as we progressed.

* How many stories/issues were completed
  * 10 issues have been completed so far.

---

## Team Retrospective

### Jesus Alfaro-Suarez

- [A link to your pull requests](https://github.com/EstrellaOrtiz-F/CST-438-Project1/pulls?q=is%3Apr+is%3Aclosed+author%3AJesusAlfaroS+assignee%3AJesusAlfaroS)
- [A link to your issues](https://github.com/EstrellaOrtiz-F/CST-438-Project1/issues?q=is%3Aissue%20state%3Aclosed%20assignee%3AJesusAlfaroS)

#### What was your role / which stories did you work on

+ I worked on allowing the user to create an account and storing their credentials as long as they are valid, I also created the settings page allowing the user to change their username & password, this page also allows the user to sign out. 

+ What was the biggest challenge?  
  My biggest challenge was trying to learn kotlin, I kept running into issues and was unsure what was causing them and once I eventually figured it out I had already wasted plenty of time.


+ Why was it a challenge?  
  + How was the challenge addressed?  
Using Kotlin was a challenge for me because I had never used it before, I was so used to Java that I had a hard time transitioning into Kotlin since the way pages were created where completely different. I addressed this challenge by researching how to build an app in Kotlin and also looking at how my teammates were working.

+ Favorite / most interesting part of this project  
My favorite part of this assignment was implementing the creating the sign up, since I was still new to Kotlin it took me a bit but I enjoyed the process of learning.


+ If you could do it over, what would you change?  
If I could do this again I would make sure to implement more features to the settings portion of the application. Due to me learning still I wasn't able to implement every thing I wish I could have.

+ What is the most valuable thing you learned?  
Learning how to use Github and working with a team to create an app. It helped us stay organized by allowing us to assign each-other issues and made sure we reviewed each-others code before merging to the main branch.

---

### Joseph Fedeli

- [[A link to your pull requests]()](https://github.com/EstrellaOrtiz-F/CST-438-Project1/issues?q=state%3Aclosed%20is%3Apr%20author%3A%40me)
- [[A link to your issues]()](https://github.com/EstrellaOrtiz-F/CST-438-Project1/issues?q=is%3Aissue%20state%3Aclosed%20assignee%3AJFedeli)

#### What was your role / which stories did you work on

+ I created Login logic, implemented the API, worked on bug fixing and app patching, created the search + filter, created the card detail screen, and worked on the deck creation portion of profile

+ What was the biggest challenge?
+ I think the biggest challenge for me was bug fixing, I spent way too many hours trying to fix things that were as simple as missing a package declaration
+ Why was it a challenge?
  + How was the challenge addressed?
+ The reason it was so challenging was that many files were often very large so looking over something small like a package declaration or an incorrectly spelled call was easy and would often have me focusing on the logic of something completely unrelated, essentially just eating up my time. The challenge was addressed by hammering away at it until it wasn't a problem anymore I suppose. The debug process went something like: Run the app, look at any errors that may or may not have appeared in the terminal, fix those errors, repeat until the app did what I wanted it to.
+ Favorite / most interesting part of this project
+ My favorite part was actually working on the CardListScreen and other API related files. The YGOAPI is built specifically ot be used for app development so a lot of the calls and functions were pretty easy to implement and I can't even begin to tell you how happy I was seeing all of the cards load first try. They weren't formatted correctly but the images were there.
+ If you could do it over, what would you change?
+ If I could do it over again I'd want to spend more time on the actual look of the app. The app as it is has a simple white and purple theme to it but I think it would've been nicer to make it a darker theme so that the cards popped out more.
+ What is the most valuable thing you learned?
+ What was the biggest challenge?  
+ I think the biggest challenge for me was bug fixing, I spent way too many hours trying to fix things that were as simple as missing a package declaration 
+ Why was it a challenge?  
  + How was the challenge addressed?  
+ The reason it was so challenging was that many files were often very large so looking over something small like a package declaration or an incorrectly spelled call was easy and would often have me focusing on the logic of something completely unrelated, essentially just eating up my time. The challenge was addressed by hammering away at it until it wasn't a problem anymore I suppose. The debug process went something like: Run the app, look at any errors that may or may not have appeared in the terminal, fix those errors, repeat until the app did what I wanted it to.
+ Favorite / most interesting part of this project  
+ My favorite part was actually working on the CardListScreen and other API related files. The YGOAPI is built specifically ot be used for app development so a lot of the calls and functions were pretty easy to implement and I can't even begin to tell you how happy I was seeing all of the cards load first try. They weren't formatted correctly but the images were there.
+ If you could do it over, what would you change?  
+ If I could do it over again I'd want to spend more time on the actual look of the app. The app as it is has a simple white and purple theme to it but I think it would've been nicer to make it a darker theme so that the cards popped out more. 
+ What is the most valuable thing you learned?  
+ I think my teammates will agree that learning how to use github was incredibly valuable. It forced us to stay organized and keep track of each other's work to make sure our app was as fine-tuned as we could make it with the time we had.

---

### Estrella Ortiz-Felix

- [A link to your pull requests](https://github.com/EstrellaOrtiz-F/CST-438-Project1/pulls?q=is%3Apr+is%3Aclosed+assignee%3AEstrellaOrtiz-F)

- [A link to your issues](https://github.com/EstrellaOrtiz-F/CST-438-Project1/issues?q=is%3Aissue%20state%3Aclosed%20assignee%3AEstrellaOrtiz-F)


#### What was your role / which stories did you work on

+ I implemented the app database, developed the database tests,
  created the user profile, wishlist functionality, and the landing page.


+ What was the biggest challenge?

  The biggest challenge I came across was working with the database.

+ Why was it a challenge?
  + When working with the database I often found myself dealing with a lot of errors/bugs. That would cause the app to crash or not properly save the user data.
  + Though with the help of google searching and the android source website I was easily able to identify whhy I was getting certain issues and properly add any code I need for my code to successfully run without issue.

+ Favorite / most interesting part of this project

  My favorite part of the project was working on the user profile and populating it with the user collection and wishlist.

+ If you could do it over, what would you change?

If I could do it all over again I think I'd have focused and worked more on the UI of the application. Making it look nicer and more refined by adding color variety and other cool fetures.


+ What is the most valuable thing you learned?

Utilizing github  was one of the more valubale things I learned. I became more comfortable with using github and it's functions. Learning how to review and leave proper constriuctive critism was another vaubable things I learned as well.

---

### Augustin Morales

- [A link to your pull requests]()
- [A link to your issues]()

#### What was your role / which stories did you work on

+

+ What was the biggest challenge?
+
+ Why was it a challenge?
  + How was the challenge addressed?
+
+ Favorite / most interesting part of this project
+
+ If you could do it over, what would you change?
+
+ What is the most valuable thing you learned?
+

---

## Features

- Browse Yu-Gi-Oh cards using an external API
- View card details and pricing information
- Add cards to a personal collection
- Add cards to a wishlist
- View collection and wishlist in user profile
- Update username and password in settings
- Log out functionality

---

## Conclusion

- How successful was the project?
  -

- What was the largest victory?
  -

- Final assessment of the project
  -
