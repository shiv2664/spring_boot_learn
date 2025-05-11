package com.shivam.spring_boot_learn.controller

import com.shivam.spring_boot_learn.database.model.User
import com.shivam.spring_boot_learn.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import kotlin.String

@RestController
@RequestMapping("/users")
class UserController(private val userRepository: UserRepository) {

    data class UserRequest(
        val id: String?,
        val name: String,
        val email: String,
        val ownerId: String
    )

    data class UserResponse(
        val id: String,
        val name: String,
        val email: String,
        val createdAt: Instant
    )


    @GetMapping
    fun getAllUsers(): List<User> = userRepository.findAll()

    @PostMapping
    fun createUser(@RequestBody user: User): User = userRepository.save(user)


    @PostMapping
    fun postUser(@RequestBody user: UserRequest): UserResponse {

        val user=userRepository.save(
            User(
                id = user.id?.let { ObjectId(it) } ?: ObjectId.get(),
                name = user.name,
                email = user.email,
                createdAt = Instant.now(),
                ownerId = ObjectId(user.ownerId),
                ownerIdParent = ObjectId(user.ownerId)
            )
        )

        return user.toUserResponse()
    }

    @GetMapping
    fun getUsersByCurrentUser(
        @RequestParam(required = true) ownerId: String
    ): List<UserResponse> {
        return userRepository.findByOwnerId(ObjectId(ownerId)).map{
            it.toUserResponse()
        }
    }

    private fun User.toUserResponse(): UserController.UserResponse{
        return UserResponse(
            id = id.toString(),
            name = name,
            email = email,
            createdAt = createdAt
        )
    }

}