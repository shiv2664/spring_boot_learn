package com.shivam.spring_boot_learn.database.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("nodes")
data class User(
    @Id
    val id: ObjectId= ObjectId.get(),
    val name: String,
    val email: String,
    val ownerId: ObjectId,
    val ownerIdParent: ObjectId,
    val createdAt: Instant
)