package com.shivam.spring_boot_learn.repository

import com.shivam.spring_boot_learn.database.model.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, ObjectId> {
    fun findByEmail(email: String): User?
    fun findByOwnerId(objectId: ObjectId): List<User>
}