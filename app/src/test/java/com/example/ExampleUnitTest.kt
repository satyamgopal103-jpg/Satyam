package com.example

import com.example.data.model.ComplaintCategory
import com.example.data.model.ComplaintStatus
import com.example.data.repository.CivicRepository
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testComplaintSubmissionWorkflow() {
        val repo = CivicRepository()
        val initialCount = repo.complaints.value.size

        val newComplaint = repo.submitComplaint(
            title = "Damaged Footpath on 8th Cross",
            description = "Pedestrian walkway tiles broken with sharp edges near children's school.",
            category = ComplaintCategory.INFRASTRUCTURE,
            location = "8th Cross, Ward 4"
        )

        assertNotNull(newComplaint.trackingId)
        assertTrue(newComplaint.trackingId.startsWith("CP-2026-"))
        assertEquals(ComplaintStatus.SUBMITTED, newComplaint.status)
        assertEquals(initialCount + 1, repo.complaints.value.size)
        assertEquals(1, newComplaint.upvotes)
        assertTrue(newComplaint.isUpvotedByMe)
    }

    @Test
    fun testComplaintUpvoting() {
        val repo = CivicRepository()
        val firstComplaint = repo.complaints.value.first()
        val prevUpvotes = firstComplaint.upvotes
        val wasUpvoted = firstComplaint.isUpvotedByMe

        repo.toggleUpvoteComplaint(firstComplaint.id)
        val updated = repo.complaints.value.first { it.id == firstComplaint.id }

        if (wasUpvoted) {
            assertEquals(prevUpvotes - 1, updated.upvotes)
            assertFalse(updated.isUpvotedByMe)
        } else {
            assertEquals(prevUpvotes + 1, updated.upvotes)
            assertTrue(updated.isUpvotedByMe)
        }
    }

    @Test
    fun testPollVotingAndPoints() {
        val repo = CivicRepository()
        val poll = repo.polls.value.first { it.selectedOptionId == null }
        val initialVotes = poll.totalVotes
        val initialPoints = repo.currentUser.value.points

        repo.votePoll(poll.id, poll.options.first().id)
        val updatedPoll = repo.polls.value.first { it.id == poll.id }

        assertEquals(initialVotes + 1, updatedPoll.totalVotes)
        assertEquals(poll.options.first().id, updatedPoll.selectedOptionId)
        assertEquals(initialPoints + 10, repo.currentUser.value.points)
    }

    @Test
    fun testVolunteerRegistration() {
        val repo = CivicRepository()
        val opp = repo.volunteerOpportunities.value.first { !it.isRegistered }
        val prevVolHours = repo.currentUser.value.volunteerHours

        repo.toggleVolunteerRegistration(opp.id)
        val updated = repo.volunteerOpportunities.value.first { it.id == opp.id }

        assertTrue(updated.isRegistered)
        assertEquals(prevVolHours + 4, repo.currentUser.value.volunteerHours)
    }

    @Test
    fun testCivicAssistantInquiry() {
        val repo = CivicRepository()
        val initialCount = repo.assistantChat.value.size

        repo.askCivicAssistant("How do I report a pothole?")
        val messages = repo.assistantChat.value

        assertEquals(initialCount + 2, messages.size)
        val assistantAnswer = messages.last()
        assertFalse(assistantAnswer.isUser)
        assertTrue(assistantAnswer.text.contains("pothole") || assistantAnswer.text.contains("tracking ID"))
    }

    @Test
    fun testThemePresetColorSchemeGeneration() {
        val darkOcean = com.example.ui.theme.ThemePreset.BLUE_OCEAN.toColorScheme(isDark = true)
        val lightOcean = com.example.ui.theme.ThemePreset.BLUE_OCEAN.toColorScheme(isDark = false)

        assertNotNull(darkOcean)
        assertNotNull(lightOcean)
        // Light mode should have light background
        assertEquals(androidx.compose.ui.graphics.Color(0xFFF1F5F9), lightOcean.background)
        // Dark mode should have dark background
        assertEquals(com.example.ui.theme.ThemePreset.BLUE_OCEAN.darkBackgroundColor, darkOcean.background)
    }

    @Test
    fun testThemeModesExist() {
        val modes = com.example.ui.theme.ThemeMode.values()
        assertEquals(3, modes.size)
        assertTrue(modes.contains(com.example.ui.theme.ThemeMode.DARK))
        assertTrue(modes.contains(com.example.ui.theme.ThemeMode.LIGHT))
        assertTrue(modes.contains(com.example.ui.theme.ThemeMode.SYSTEM))
    }
}
