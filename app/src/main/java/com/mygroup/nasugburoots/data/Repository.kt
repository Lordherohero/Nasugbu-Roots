package com.mygroup.nasugburoots.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.mygroup.nasugburoots.model.Guide
import com.mygroup.nasugburoots.model.TouristSpot

object Repository {

    private const val TAG = "Repository"
    private var seedStarted = false

    private val db: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    fun seedIfEmpty() {
        if (seedStarted) {
            return
        }
        seedStarted = true

        db.collection("spots").limit(1).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.isEmpty) {
                    val batch = db.batch()
                    sampleSpots.forEach { spot ->
                        batch.set(db.collection("spots").document(spot.placeId.toString()), spot)
                    }
                    sampleGuides.forEach { guide ->
                        batch.set(db.collection("guides").document(guide.guideId.toString()), guide)
                    }
                    batch.commit()
                        .addOnSuccessListener { Log.d(TAG, "Sample spots and guides added") }
                        .addOnFailureListener { Log.e(TAG, "Seeding failed", it) }
                }
            }
            .addOnFailureListener { Log.e(TAG, "Could not check for existing spots", it) }
    }

    fun listenToSpots(onChange: (List<TouristSpot>) -> Unit): ListenerRegistration {
        return db.collection("spots").addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) {
                Log.e(TAG, "Spots listener failed", error)
                return@addSnapshotListener
            }
            try {
                val spots = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(TouristSpot::class.java)
                }
                onChange(spots.sortedBy { it.placeId })
            } catch (e: Exception) {
                Log.e(TAG, "Could not read spots", e)
            }
        }
    }

    fun listenToGuides(onChange: (List<Guide>) -> Unit): ListenerRegistration {
        return db.collection("guides").addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) {
                Log.e(TAG, "Guides listener failed", error)
                return@addSnapshotListener
            }
            try {
                val guides = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Guide::class.java)
                }
                onChange(guides.sortedBy { it.guideId })
            } catch (e: Exception) {
                Log.e(TAG, "Could not read guides", e)
            }
        }
    }
}