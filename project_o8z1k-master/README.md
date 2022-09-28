# Malaysian Harimau Airways (MHA)

## Taking you to more than 30 destinations across the globe.

#### The optimal & simple Airline booking System.


This term in CPSC210, I would like to design an **Airline booking System** for the fictional 
"Malaysian Harimau Airways". It should do mostly everything you would expect while booking a flight ticket. The MHA 
is **1st of it's kind** as their flights are initiated by demand. Which means, user picks a route and if route has a lot 
of demand and the size of the route eventually determines the plane type. The user will receive a booking code once 
their booking is done in which their seat will be reserved for 24 hours. The user will then have to call the MHA 
headquarters to initialize the payment. This allows some flexibility for potential travellers to book and reserve a seat
without paying a single cent for 24 hours to allow travellers to plan their journey comfortably and easier.

This booking system will be **used by all travellers who wish to travel the world** in one of the world's **most 
luxurious airlines** for a *low cost*. I am interested in this project as I have been a flight and travel enthusiast for
as long as I can remember. Before the pandemic, I would travel to at least 5-6 countries every year. As of today, I have
visited 48 countries across the globe at the age of 18 years. I would like to create a new and simple airline booking 
system for a fictional airline of my dreams, as I truly believe that **it is all about the journey and not the 
destination**.


##User Stories

As a user, I **would like to be able** to:
- be able to enter my basic ID information.
- enter an origin and destination
- pick the class (Regular Lux Class, Royal First Class)
- book multiple flights (in one booking). 
- Generate a booking code for payment.
- be able to save my made bookings after finishing adding flights. 
- be able to start my program and load and print my booking with it's saved booking code.

## Phase 4: Task 2
I chose to implement the first option, to make a method in my model package more robust. The method involved 
is located in the model package, in the Booking class called addFlight. I threw an InvalidPlaceException whenever
a 0 sized string is given as an input for the origin or destination. 

## Phase 4: Task 3

If I was given more time, I believe I can improve my design by firstly getting rid of all the REQUIRES clause in
my Booking and Flight class by making it robust. For example, in my booking constructor, there's a REQUIRES
clause that can be eliminated by throwing an exception for each REQUIRES case. I also thought of creating
a routing system, where I can map each flight to a specific route, and therefore have the user select
a flight based on the flights available to them in the HashSet. This would reflect a more realistic airline
booking system rather than a more private "chartered airline" approach. Both of these methods that I have listed
would make my program more professional and realistic. 


