package com.example.data.repository

import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class CivicRepository {
    private val _currentUser = MutableStateFlow(User())
    val currentUser: StateFlow<User> = _currentUser.asStateFlow()

    private val _complaints = MutableStateFlow(SampleCivicData.initialComplaints)
    val complaints: StateFlow<List<Complaint>> = _complaints.asStateFlow()

    private val _representatives = MutableStateFlow(SampleCivicData.representatives)
    val representatives: StateFlow<List<Representative>> = _representatives.asStateFlow()

    private val _projects = MutableStateFlow(SampleCivicData.projects)
    val projects: StateFlow<List<CivicProject>> = _projects.asStateFlow()

    private val _communities = MutableStateFlow(SampleCivicData.communities)
    val communities: StateFlow<List<Community>> = _communities.asStateFlow()

    private val _polls = MutableStateFlow(SampleCivicData.polls)
    val polls: StateFlow<List<Poll>> = _polls.asStateFlow()

    private val _volunteerOpportunities = MutableStateFlow(SampleCivicData.volunteerOpportunities)
    val volunteerOpportunities: StateFlow<List<VolunteerOpportunity>> = _volunteerOpportunities.asStateFlow()

    private val _news = MutableStateFlow(SampleCivicData.news)
    val news: StateFlow<List<CivicNews>> = _news.asStateFlow()

    private val _notifications = MutableStateFlow(SampleCivicData.notifications)
    val notifications: StateFlow<List<CivicNotification>> = _notifications.asStateFlow()

    private val _conversations = MutableStateFlow(SampleCivicData.initialConversations)
    val conversations: StateFlow<List<ChatConversation>> = _conversations.asStateFlow()

    private val _assistantChat = MutableStateFlow(listOf(
        CivicChatMessage(
            id = "msg-0",
            text = "Welcome to Civic Assistant! I can help you understand public services, file or track complaints, navigate municipal departments, find representative contact info, and explain civic terminology.",
            isUser = false,
            timestamp = "Just now",
            suggestedFollowUps = listOf(
                "How do I file a road pothole complaint?",
                "What is the RTI Act process?",
                "Who is my Ward Councillor?",
                "What are participatory budget funds?"
            )
        )
    ))
    val assistantChat: StateFlow<List<CivicChatMessage>> = _assistantChat.asStateFlow()

    fun submitComplaint(
        title: String,
        description: String,
        category: ComplaintCategory,
        location: String,
        evidenceAttached: Boolean = true
    ): Complaint {
        val randSuffix = (1000..9999).random()
        val trackingId = "CP-2026-$randSuffix"
        val currentDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())

        val assignedDept = when (category) {
            ComplaintCategory.ROADS -> "Public Works & Highways"
            ComplaintCategory.WATER -> "Water Supply & Sewerage Board"
            ComplaintCategory.WASTE -> "Municipal Solid Waste Management"
            ComplaintCategory.DRAINAGE -> "Stormwater Drainage Division"
            ComplaintCategory.LIGHTING -> "Municipal Electrical Board"
            ComplaintCategory.INFRASTRUCTURE -> "Urban Infrastructure Corp"
            ComplaintCategory.FACILITIES -> "Public Health & Civic Amenities"
            ComplaintCategory.OTHER -> "General Civic Grievances Cell"
        }

        val newComplaint = Complaint(
            id = "cmp-$randSuffix",
            trackingId = trackingId,
            title = title,
            description = description,
            category = category,
            location = location,
            status = ComplaintStatus.SUBMITTED,
            submittedDate = currentDate,
            department = assignedDept,
            officialResponse = null,
            upvotes = 1,
            isUpvotedByMe = true,
            commentsCount = 0,
            timeline = listOf(
                TimelineEvent(
                    status = ComplaintStatus.SUBMITTED,
                    date = currentDate,
                    note = "Complaint registered with unique tracking ID $trackingId. Auto-routed to $assignedDept.",
                    actor = "Citizen Portal"
                )
            )
        )

        _complaints.value = listOf(newComplaint) + _complaints.value
        _currentUser.value = _currentUser.value.copy(
            complaintsLogged = _currentUser.value.complaintsLogged + 1,
            points = _currentUser.value.points + 25
        )

        // Add a notification
        val notif = CivicNotification(
            id = "notif-$randSuffix",
            title = "Complaint Submitted: $trackingId",
            message = "Your grievance '$title' has been routed to $assignedDept.",
            category = NotificationFilter.COMPLAINTS,
            timestamp = "Just now",
            isRead = false
        )
        _notifications.value = listOf(notif) + _notifications.value

        return newComplaint
    }

    fun toggleUpvoteComplaint(complaintId: String) {
        _complaints.value = _complaints.value.map { item ->
            if (item.id == complaintId) {
                val newUpvoted = !item.isUpvotedByMe
                val diff = if (newUpvoted) 1 else -1
                item.copy(isUpvotedByMe = newUpvoted, upvotes = (item.upvotes + diff).coerceAtLeast(0))
            } else item
        }
    }

    fun updateComplaintStatusByAdmin(complaintId: String, newStatus: ComplaintStatus, officialNote: String) {
        val currentDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
        _complaints.value = _complaints.value.map { item ->
            if (item.id == complaintId) {
                val updatedTimeline = item.timeline + TimelineEvent(
                    status = newStatus,
                    date = currentDate,
                    note = officialNote,
                    actor = "Municipal Administration Officer"
                )
                item.copy(
                    status = newStatus,
                    officialResponse = officialNote,
                    timeline = updatedTimeline
                )
            } else item
        }
    }

    fun votePoll(pollId: String, optionId: String) {
        _polls.value = _polls.value.map { poll ->
            if (poll.id == pollId && poll.selectedOptionId == null) {
                val updatedOptions = poll.options.map { opt ->
                    if (opt.id == optionId) opt.copy(votes = opt.votes + 1) else opt
                }
                poll.copy(
                    selectedOptionId = optionId,
                    totalVotes = poll.totalVotes + 1,
                    options = updatedOptions
                )
            } else poll
        }
        _currentUser.value = _currentUser.value.copy(points = _currentUser.value.points + 10)
    }

    fun toggleCommunityMembership(communityId: String) {
        _communities.value = _communities.value.map { comm ->
            if (comm.id == communityId) {
                val joined = !comm.isJoined
                val countDiff = if (joined) 1 else -1
                comm.copy(isJoined = joined, memberCount = comm.memberCount + countDiff)
            } else comm
        }
    }

    fun toggleVolunteerRegistration(opportunityId: String) {
        _volunteerOpportunities.value = _volunteerOpportunities.value.map { opp ->
            if (opp.id == opportunityId) {
                val registered = !opp.isRegistered
                val diff = if (registered) 1 else -1
                if (registered) {
                    _currentUser.value = _currentUser.value.copy(
                        volunteerHours = _currentUser.value.volunteerHours + 4,
                        points = _currentUser.value.points + opp.pointsAwarded
                    )
                }
                opp.copy(isRegistered = registered, registeredCount = opp.registeredCount + diff)
            } else opp
        }
    }

    fun markAllNotificationsRead() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
    }

    fun askCivicAssistant(userPrompt: String) {
        val timeNow = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val userMsg = CivicChatMessage(
            id = UUID.randomUUID().toString(),
            text = userPrompt,
            isUser = true,
            timestamp = timeNow
        )
        _assistantChat.value = _assistantChat.value + userMsg

        // Generate intelligent contextual response
        val lower = userPrompt.lowercase()
        val responseText = when {
            "pothole" in lower || "road" in lower -> {
                "To report a road or pothole defect in Civic Pulse:\n1. Tap 'Create' or 'Report Issue' on the dashboard.\n2. Select the category 'Roads & Potholes'.\n3. Attach clear photos showing the depth and surrounding landmarks.\n4. Confirm GPS coordinates or input the street name.\n5. Upon submission, a unique tracking ID (e.g. CP-2026-XXXX) is issued. Under the citizen charter, the Public Works rapid patch team surveys within 48 hours."
            }
            "rti" in lower || "right to information" in lower -> {
                "Under the Right to Information (RTI) Act, any citizen can request information from public authorities. Requests must be filed with the designated Public Information Officer (PIO). The statutory response window is 30 days (or 48 hours if life and liberty are concerned). You can monitor digital RTI records in Civic Pulse under the 'Transparency Centre'."
            }
            "mla" in lower || "representative" in lower || "councillor" in lower -> {
                "In your constituency (Metro North), your elected representative is Hon. Priya Sharma (MLA), and your Ward Councillor is Dr. Vikramaditya Rao. You can view their verified project delivery rates, grievance resolution track records, and grant utilization in the 'MLA & Leaders' tab."
            }
            "water" in lower || "sewage" in lower || "drain" in lower -> {
                "Water supply and drainage complaints are handled by the Water Supply & Sewerage Board. If pressure drops or discolored water occurs, file an issue under 'Water Supply'. Scheduled maintenance alerts are automatically pushed to your Notification Centre."
            }
            "budget" in lower || "participatory" in lower -> {
                "Participatory budgeting allows residents to directly propose and vote on how a portion of the municipal ward budget is spent. In Ward 4, the active consultation is currently open in the 'Polls & Surveys' section for park amenities."
            }
            else -> {
                "Civic Pulse connects you directly to municipal governance. You can report civic infrastructure defects, vote on community consultations, join neighborhood volunteer teams, or monitor public works projects. Is there a specific civic service or department process you'd like more details on?"
            }
        }

        val assistantMsg = CivicChatMessage(
            id = UUID.randomUUID().toString(),
            text = responseText,
            isUser = false,
            timestamp = timeNow,
            suggestedFollowUps = listOf(
                "Show active projects in Ward 4",
                "How do I join a volunteer drive?",
                "Track my submitted complaints"
            )
        )
        _assistantChat.value = _assistantChat.value + assistantMsg
    }
}
