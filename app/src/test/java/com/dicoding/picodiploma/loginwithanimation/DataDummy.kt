package com.dicoding.picodiploma.loginwithanimation

import com.dicoding.picodiploma.loginwithanimation.data.remote.pref.ListStoryItem
import kotlin.random.Random

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        return List(101) { index ->
            ListStoryItem(
                photoUrl = "https://picsum.photos/seed/$index/200/300",
                createdAt = java.time.Instant.now().toString(),
                name = "Author ${Random.nextInt(1000)}",
                description = "Unique story description for item $index",
                lat = Random.nextDouble(-90.0, 90.0),
                id = "$index",
                lon = Random.nextDouble(-180.0, 180.0)
            )
        }
    }
}