package com.example.data.model

data class User(
    val id: String = "usr-001",
    val name: String = "Citizen Alex Morgan",
    val email: String = "alex.morgan@civicpulse.org",
    val phone: String = "+1 (555) 019-2834",
    val ward: String = "Ward 4 — Central District",
    val constituency: String = "Metro North",
    val points: Int = 340,
    val badges: List<CitizenBadge> = listOf(
        CitizenBadge("Good Citizen", "Active community participant", "🌟"),
        CitizenBadge("Issue Reporter", "Logged 5+ verified civic issues", "📍"),
        CitizenBadge("Community Volunteer", "Dedicated 15+ civic service hours", "🤝"),
        CitizenBadge("Helpful Contributor", "Top upvoted neighbourhood ideas", "💡")
    ),
    val complaintsLogged: Int = 7,
    val complaintsResolved: Int = 5,
    val volunteerHours: Int = 18,
    val eventsAttended: Int = 6,
    val isVerified: Boolean = true,
    val isPublicProfile: Boolean = true
)

data class CitizenBadge(
    val name: String,
    val description: String,
    val iconEmoji: String
)

enum class ComplaintCategory(val displayName: String, val icon: String) {
    ROADS("Roads & Potholes", "🛣️"),
    WATER("Water Supply", "💧"),
    WASTE("Waste Management", "🗑️"),
    DRAINAGE("Drainage & Sewage", "🌊"),
    LIGHTING("Street Lighting", "💡"),
    INFRASTRUCTURE("Public Infrastructure", "🏗️"),
    FACILITIES("Public Facilities", "🏛️"),
    OTHER("Other Civic Issue", "📋")
}

enum class ComplaintStatus(val label: String, val colorHex: Long) {
    SUBMITTED("Submitted", 0xFF38BDF8),
    UNDER_REVIEW("Under Review", 0xFFF59E0B),
    IN_PROGRESS("In Progress", 0xFF6366F1),
    RESOLVED("Resolved", 0xFF10B981),
    REJECTED("Rejected", 0xFFEF4444)
}

data class Complaint(
    val id: String,
    val trackingId: String,
    val title: String,
    val description: String,
    val category: ComplaintCategory,
    val location: String,
    val status: ComplaintStatus,
    val submittedDate: String,
    val department: String,
    val officialResponse: String? = null,
    val upvotes: Int = 0,
    val isUpvotedByMe: Boolean = false,
    val commentsCount: Int = 0,
    val timeline: List<TimelineEvent> = emptyList()
)

data class TimelineEvent(
    val status: ComplaintStatus,
    val date: String,
    val note: String,
    val actor: String
)

data class Representative(
    val id: String,
    val name: String,
    val title: String,
    val constituency: String,
    val party: String,
    val responseRatePercent: Int,
    val projectsCompleted: Int,
    val activeComplaintsHandled: Int,
    val totalGrantUtilized: String,
    val verified: Boolean = true,
    val recentActivities: List<String> = emptyList()
)

data class CivicProject(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val department: String,
    val location: String,
    val budget: String,
    val progressPercent: Int,
    val startDate: String,
    val estimatedCompletion: String,
    val verifiedUpdates: Int,
    val upvotes: Int
)

data class Community(
    val id: String,
    val name: String,
    val description: String,
    val ward: String,
    val memberCount: Int,
    val isJoined: Boolean = false,
    val tags: List<String> = emptyList()
)

data class Poll(
    val id: String,
    val question: String,
    val description: String,
    val isOfficialConsultation: Boolean,
    val totalVotes: Int,
    val options: List<PollOption>,
    val selectedOptionId: String? = null,
    val closesInDays: Int = 4
)

data class PollOption(
    val id: String,
    val text: String,
    val votes: Int
)

data class VolunteerOpportunity(
    val id: String,
    val title: String,
    val organizer: String,
    val date: String,
    val time: String,
    val location: String,
    val requiredVolunteers: Int,
    val registeredCount: Int,
    val pointsAwarded: Int,
    val isRegistered: Boolean = false
)

data class CivicNews(
    val id: String,
    val title: String,
    val summary: String,
    val category: String,
    val source: String,
    val date: String,
    val isOfficialAnnouncement: Boolean = false
)

enum class NotificationFilter(val label: String) {
    ALL("All"),
    MESSAGES("Messages"),
    COMPLAINTS("Complaints"),
    GOVERNMENT("Gov Announcements"),
    EVENTS("Events"),
    PROJECTS("Projects"),
    ALERTS("Urgent Alerts")
}

data class CivicNotification(
    val id: String,
    val title: String,
    val message: String,
    val category: NotificationFilter,
    val timestamp: String,
    val isRead: Boolean = false
)

data class CivicChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: String,
    val suggestedFollowUps: List<String> = emptyList()
)

data class CommentItem(
    val id: String,
    val author: String,
    val text: String,
    val date: String,
    val isOfficial: Boolean = false,
    val likes: Int = 0
)

data class ChatConversation(
    val id: String,
    val title: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int = 0,
    val isOfficialChannel: Boolean = false
)
