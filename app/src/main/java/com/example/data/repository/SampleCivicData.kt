package com.example.data.repository

import com.example.data.model.*

object SampleCivicData {
    val initialComplaints = listOf(
        Complaint(
            id = "cmp-101",
            trackingId = "CP-2026-8491",
            title = "Severe Potholes on 5th Main Avenue",
            description = "Multiple deep potholes near the crossroad junction causing hazardous traffic slowdowns and bicycle accidents during rain.",
            category = ComplaintCategory.ROADS,
            location = "5th Main Ave, Ward 4, Metro North",
            status = ComplaintStatus.IN_PROGRESS,
            submittedDate = "Nov 02, 2026",
            department = "Public Works & Highways",
            officialResponse = "Road inspection team surveyed site on Nov 4. Asphalt resurfacing crew scheduled for Nov 12.",
            upvotes = 42,
            isUpvotedByMe = true,
            commentsCount = 8,
            timeline = listOf(
                TimelineEvent(ComplaintStatus.SUBMITTED, "Nov 02, 2026", "Complaint lodged by resident", "System"),
                TimelineEvent(ComplaintStatus.UNDER_REVIEW, "Nov 03, 2026", "Assigned to Ward Engineer Mr. Rajesh Verma", "Public Works"),
                TimelineEvent(ComplaintStatus.IN_PROGRESS, "Nov 04, 2026", "On-site survey completed. Materials requisitioned.", "Site Engineer")
            )
        ),
        Complaint(
            id = "cmp-102",
            trackingId = "CP-2026-8492",
            title = "Broken Streetlight at Maple Park Gate",
            description = "Three consecutive pole lights are flickering or non-operational for the past 6 days, leaving pedestrian walkways in total darkness.",
            category = ComplaintCategory.LIGHTING,
            location = "Maple Park West Entrance, Ward 4",
            status = ComplaintStatus.RESOLVED,
            submittedDate = "Oct 28, 2026",
            department = "Municipal Electrical Board",
            officialResponse = "LED drivers and bulbs replaced on Oct 30 by rapid electrical maintenance squad.",
            upvotes = 19,
            isUpvotedByMe = false,
            commentsCount = 3,
            timeline = listOf(
                TimelineEvent(ComplaintStatus.SUBMITTED, "Oct 28, 2026", "Reported by resident", "System"),
                TimelineEvent(ComplaintStatus.UNDER_REVIEW, "Oct 29, 2026", "Electrical crew dispatched", "Municipal Board"),
                TimelineEvent(ComplaintStatus.RESOLVED, "Oct 30, 2026", "New 80W smart LED fixtures installed and tested", "Lighting Division")
            )
        ),
        Complaint(
            id = "cmp-103",
            trackingId = "CP-2026-8493",
            title = "Overflowing Storm Drain on Pine Boulevard",
            description = "Silt blockage in the monsoon drain is causing wastewater overflow onto the sidewalk during morning hours.",
            category = ComplaintCategory.DRAINAGE,
            location = "Corner of Pine Blvd & 9th St",
            status = ComplaintStatus.UNDER_REVIEW,
            submittedDate = "Nov 08, 2026",
            department = "Stormwater & Drainage Dept",
            officialResponse = "Desilting vehicle assigned in routing queue.",
            upvotes = 31,
            isUpvotedByMe = true,
            commentsCount = 5,
            timeline = listOf(
                TimelineEvent(ComplaintStatus.SUBMITTED, "Nov 08, 2026", "Issue reported with geo-tag", "System"),
                TimelineEvent(ComplaintStatus.UNDER_REVIEW, "Nov 09, 2026", "Verification in progress by drainage inspector", "Drainage Dept")
            )
        ),
        Complaint(
            id = "cmp-104",
            trackingId = "CP-2026-8494",
            title = "Irregular Morning Water Supply Pressure",
            description = "Low water pressure in residential blocks 4C to 4F. Supply cutoff occurs after only 25 minutes instead of regular 2 hours.",
            category = ComplaintCategory.WATER,
            location = "Sector 4C Residential Zone",
            status = ComplaintStatus.SUBMITTED,
            submittedDate = "Nov 10, 2026",
            department = "Water Supply & Sewerage Board",
            officialResponse = null,
            upvotes = 14,
            isUpvotedByMe = false,
            commentsCount = 2,
            timeline = listOf(
                TimelineEvent(ComplaintStatus.SUBMITTED, "Nov 10, 2026", "Complaint registered in citizen queue", "System")
            )
        )
    )

    val representatives = listOf(
        Representative(
            id = "rep-01",
            name = "Hon. Priya Sharma",
            title = "Member of Legislative Assembly (MLA)",
            constituency = "Metro North Constituency",
            party = "Independent Civic Alliance",
            responseRatePercent = 94,
            projectsCompleted = 18,
            activeComplaintsHandled = 142,
            totalGrantUtilized = "$2.4M (92% of civic budget)",
            verified = true,
            recentActivities = listOf(
                "Inaugurated Ward 4 Community Solar Microgrid project",
                "Conducted public grievance redressal town hall with 250+ citizens",
                "Toured monsoon drainage desilting sites across sector 3 & 4"
            )
        ),
        Representative(
            id = "rep-02",
            name = "Dr. Vikramaditya Rao",
            title = "Municipal Ward Councillor",
            constituency = "Ward 4 — Central",
            party = "Progressive Citizens Forum",
            responseRatePercent = 89,
            projectsCompleted = 12,
            activeComplaintsHandled = 98,
            totalGrantUtilized = "$850K (88% of ward fund)",
            verified = true,
            recentActivities = listOf(
                "Approved sidewalk widening and tree plantation on Main Ave",
                "Introduced automated waste sorting incentive program"
            )
        ),
        Representative(
            id = "rep-03",
            name = "Meera Nambiar",
            title = "District Planning Board Chair",
            constituency = "Metro North & Urban Extension",
            party = "Civic Development Council",
            responseRatePercent = 86,
            projectsCompleted = 24,
            activeComplaintsHandled = 210,
            totalGrantUtilized = "$5.1M (95% grant deployed)",
            verified = true,
            recentActivities = listOf(
                "Completed metro feeder bus route expansion",
                "Launched digital RTI and civic project open dashboard"
            )
        )
    )

    val projects = listOf(
        CivicProject(
            id = "prj-01",
            title = "Green Corridor & Pedestrian Walkway",
            description = "Creation of 3.2km continuous shaded walking trail with solar-powered illumination, smart benches, and indigenous native trees.",
            category = "Urban Infrastructure",
            department = "Urban Planning & Forestry",
            location = "Riverfront to Central Square",
            budget = "$1,250,000",
            progressPercent = 78,
            startDate = "Jan 2026",
            estimatedCompletion = "Dec 2026",
            verifiedUpdates = 14,
            upvotes = 312
        ),
        CivicProject(
            id = "prj-02",
            title = "Smart Stormwater Drainage Overhaul",
            description = "Upgrading underground concrete stormwater canals and rainwater retention wells to prevent monsoon flooding in low-lying sectors.",
            category = "Water & Sanitation",
            department = "Civic Infrastructure Corp",
            location = "Low-lying Sectors 3, 4, and 7",
            budget = "$2,800,000",
            progressPercent = 55,
            startDate = "Mar 2026",
            estimatedCompletion = "Feb 2027",
            verifiedUpdates = 22,
            upvotes = 480
        ),
        CivicProject(
            id = "prj-03",
            title = "Community Solar & Clean Energy Hub",
            description = "Rooftop solar panel installation on 12 public schools and civic library with public battery storage facility.",
            category = "Clean Energy",
            department = "Energy & Sustainability Bureau",
            location = "Central Education Complex",
            budget = "$920,000",
            progressPercent = 90,
            startDate = "Nov 2025",
            estimatedCompletion = "Nov 2026",
            verifiedUpdates = 19,
            upvotes = 560
        )
    )

    val communities = listOf(
        Community(
            id = "com-01",
            name = "Central Ward 4 Residents Alliance",
            description = "Neighbourhood council discussing local security, roads, parks, and youth activities.",
            ward = "Ward 4",
            memberCount = 840,
            isJoined = true,
            tags = listOf("Neighbourhood", "Safety", "Parks")
        ),
        Community(
            id = "com-02",
            name = "Clean Green Metro Volunteers",
            description = "Citizens organizing weekend tree planting, waste cleanup, and eco-initiatives.",
            ward = "Metro North",
            memberCount = 620,
            isJoined = true,
            tags = listOf("Environment", "Volunteering", "Cleanup")
        ),
        Community(
            id = "com-03",
            name = "Safe Cycling & Public Transit Advocates",
            description = "Community focused on cycle track connectivity, pedestrian safety, and public bus frequencies.",
            ward = "Citywide",
            memberCount = 1150,
            isJoined = false,
            tags = listOf("Mobility", "Bikes", "Transit")
        ),
        Community(
            id = "com-04",
            name = "Civic Watchdogs & Transparency Forum",
            description = "Following municipal project budgets, RTI applications, and council votes.",
            ward = "Metro North",
            memberCount = 490,
            isJoined = false,
            tags = listOf("Transparency", "Governance", "Audit")
        )
    )

    val polls = listOf(
        Poll(
            id = "pol-01",
            question = "Should Central Avenue allocate one vehicular lane for dedicated bi-directional cycling?",
            description = "Official Municipal Consultation for the 2027 Active Mobility Plan.",
            isOfficialConsultation = true,
            totalVotes = 1240,
            options = listOf(
                PollOption("opt-1", "Yes, create protected bike lane", 780),
                PollOption("opt-2", "No, keep all lanes for motor traffic", 320),
                PollOption("opt-3", "Implement shared bus & bike corridor", 140)
            ),
            selectedOptionId = "opt-1",
            closesInDays = 5
        ),
        Poll(
            id = "pol-02",
            question = "Which park amenity should be prioritized for Maple Park Phase 2?",
            description = "Ward 4 Participatory Budgeting Community Survey.",
            isOfficialConsultation = false,
            totalVotes = 620,
            options = listOf(
                PollOption("opt-21", "Inclusive children's sensory playground", 290),
                PollOption("opt-22", "Senior citizen wellness gazebo & walking path", 210),
                PollOption("opt-23", "Fenced community dog park", 120)
            ),
            selectedOptionId = null,
            closesInDays = 3
        )
    )

    val volunteerOpportunities = listOf(
        VolunteerOpportunity(
            id = "vol-01",
            title = "Weekend Riverfront Tree Plantation Drive",
            organizer = "Metro Forestry Department & Green Alliance",
            date = "Saturday, Nov 15, 2026",
            time = "08:00 AM – 11:30 AM",
            location = "Riverfront Promenade Gate 2",
            requiredVolunteers = 50,
            registeredCount = 38,
            pointsAwarded = 50,
            isRegistered = true
        ),
        VolunteerOpportunity(
            id = "vol-02",
            title = "Community Digital Literacy Workshop for Seniors",
            organizer = "Central Civic Library",
            date = "Wednesday, Nov 19, 2026",
            time = "02:00 PM – 04:30 PM",
            location = "Ward 4 Civic Media Centre",
            requiredVolunteers = 15,
            registeredCount = 9,
            pointsAwarded = 40,
            isRegistered = false
        ),
        VolunteerOpportunity(
            id = "vol-03",
            title = "Monsoon Drain Pre-Inspection & Litter Cleanup",
            organizer = "Ward 4 Residents Alliance",
            date = "Sunday, Nov 23, 2026",
            time = "07:30 AM – 10:00 AM",
            location = "North Canal Walk",
            requiredVolunteers = 30,
            registeredCount = 18,
            pointsAwarded = 45,
            isRegistered = false
        )
    )

    val news = listOf(
        CivicNews(
            id = "nw-01",
            title = "Municipal Corporation Approves $15M Annual Civic Maintenance Grant",
            summary = "Special focus directed to stormwater drainage modernization, automated pothole repair vehicles, and LED street lighting expansion.",
            category = "Government Announcements",
            source = "City Gazette",
            date = "Nov 10, 2026",
            isOfficialAnnouncement = true
        ),
        CivicNews(
            id = "nw-02",
            title = "New Express Metro Feeder Bus Routes to Start from Nov 20",
            summary = "Twelve new electric feeder shuttles will connect residential sectors directly to North Metro interchange at 8-minute intervals.",
            category = "Infrastructure Updates",
            source = "Transit Authority",
            date = "Nov 09, 2026",
            isOfficialAnnouncement = true
        ),
        CivicNews(
            id = "nw-03",
            title = "Ward 4 Resident Grievance Camp Scheduled for This Saturday",
            summary = "Meet department heads from Water, Roads, and Electricity for instant on-spot complaint escalation and tracking ID generation.",
            category = "Community Events",
            source = "Ward 4 Office",
            date = "Nov 08, 2026",
            isOfficialAnnouncement = false
        )
    )

    val notifications = listOf(
        CivicNotification(
            id = "notif-01",
            title = "Complaint Status Updated: In Progress",
            message = "Your issue 'Severe Potholes on 5th Main Avenue' (CP-2026-8491) was assigned to the road repair crew.",
            category = NotificationFilter.COMPLAINTS,
            timestamp = "2 hours ago",
            isRead = false
        ),
        CivicNotification(
            id = "notif-02",
            title = "Public Consultation Live",
            message = "Cast your vote on the 'Main Street Active Cycling Corridor' proposal before Nov 16.",
            category = NotificationFilter.GOVERNMENT,
            timestamp = "5 hours ago",
            isRead = false
        ),
        CivicNotification(
            id = "notif-03",
            title = "Volunteer Registration Confirmed",
            message = "You are confirmed for 'Weekend Riverfront Tree Plantation Drive' on Saturday at 8:00 AM.",
            category = NotificationFilter.EVENTS,
            timestamp = "1 day ago",
            isRead = true
        ),
        CivicNotification(
            id = "notif-04",
            title = "Urgent: Water Supply Scheduled Maintenance",
            message = "Sector 4 water mains will undergo flushing on Thursday 1:00 PM – 4:00 PM. Please store adequate water.",
            category = NotificationFilter.ALERTS,
            timestamp = "1 day ago",
            isRead = true
        )
    )

    val initialConversations = listOf(
        ChatConversation(
            id = "chat-01",
            title = "Ward 4 Grievance Officer",
            lastMessage = "Your site photos have been forwarded to the road works inspector.",
            time = "10:35 AM",
            unreadCount = 1,
            isOfficialChannel = true
        ),
        ChatConversation(
            id = "chat-02",
            title = "Central Ward 4 Community Group",
            lastMessage = "Ananya: Don't forget the park cleanup this Saturday morning!",
            time = "09:12 AM",
            unreadCount = 3,
            isOfficialChannel = false
        ),
        ChatConversation(
            id = "chat-03",
            title = "Public Works Department Helpline",
            lastMessage = "Complaint CP-2026-8492 marked resolved with contractor verification.",
            time = "Yesterday",
            unreadCount = 0,
            isOfficialChannel = true
        )
    )
}
