package com.devyash.basicsindepthpractice

import android.util.Log
import com.devyash.basicsindepthpractice.Constants.SUPERVISORSCOPETEST
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.supervisorScope
import kotlin.random.Random

class ImageDownloader {

    // Simulated image URLs
    private val imagesUrl = listOf(
        "https://example.com/image1.jpg",
        "https://example.com/image2.jpg",
        "https://example.com/image3.jpg",
        "https://example.com/image4.jpg",
        "https://example.com/image5.jpg"
    )

    // Function to download an image from the given URL
    private suspend fun downloadImage(imageUrl: String): String {

        // Simulating download time
        delay(Random.nextLong(1000, 5000))

        // Simulating occasional download failure
        if (Random.nextBoolean()) {
            throw Exception("Failed to download $imageUrl")
        }
        return "Image Downloaded $imageUrl"
    }

    suspend fun downloadImagesWithCoroutineScope() = coroutineScope {
        try {
            val deferredImages = imagesUrl.map {
                async { downloadImage(it) }
            }
            // Wait for all the images to be downloaded and get results
            deferredImages.awaitAll()
        } catch (e: Exception) {
            Log.d(SUPERVISORSCOPETEST, "Downloading images failed: ${e.message}")
        }
    }

    suspend fun downloadImagesWithSupervisorScope() = supervisorScope {
        val deferredImages = imagesUrl.map {
            async {
                try {
                    downloadImage(it)
                }catch (e: Exception) {
                    // Handle the exception for each image
                    Log.d(SUPERVISORSCOPETEST,"Failed to download $imagesUrl: ${e.message}")
                    "Failed to download $imagesUrl"
                }
            }

        }
        // Wait for all the images to be downloaded and get results
        deferredImages.awaitAll()

    }
}